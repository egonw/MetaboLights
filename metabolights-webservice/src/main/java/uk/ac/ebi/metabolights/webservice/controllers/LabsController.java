/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Oct-29
 * Modified by:   venkata
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;



/**
 * Created by venkata on 29/10/2015.
 */

@Controller
@RequestMapping("labs")
public class LabsController extends BasicController{
    protected static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> authenticateUser(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        JSONParser parser = new JSONParser();
        JSONObject loginData = null;

        try {
            loginData = (JSONObject) parser.parse(data);
        } catch (ParseException e) {

        }

        String email = (String) loginData.get("email");
        String secret = (String) loginData.get("secret");

        RestResponse<String> restResponse = new RestResponse<>();
        UserServiceImpl usi = null;

        try {
            usi = new UserServiceImpl();
        } catch (DAOException e) {
            response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return restResponse;
        }

        User user = usi.authenticateUser(email,secret);

        if (user != null){
            JwtClaims claims = new JwtClaims();
            claims.setSubject(email);
            claims.setIssuer("Metabolights");
            claims.setAudience("Metabolights Labs");
            claims.setClaim("Name",user.getFullName());

            String token = user.getApiToken();
            Key key = null;
            try {
                key = new HmacKey(token.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                response.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
                return restResponse;
            }

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
            jws.setKey(key);
            jws.setDoKeyValidation(false); // relaxes the key length requirement

            String jwt = null;
            try {
                jwt = jws.getCompactSerialization();
            } catch (JoseException e) {
                response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
                return restResponse;
            }

            restResponse.setContent(jwt);
            response.setHeader("user", email);
            response.setHeader("jwt", jwt);
            return restResponse;
        }else{
            restResponse.setContent("invalid");
            response.setStatus(Response.Status.FORBIDDEN.getStatusCode());
            return restResponse;
        }

    }

    @RequestMapping(value = "validateuser", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<String> validateJWTToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JoseException, DAOException {

        RestResponse<String> restResponse = new RestResponse<String>();

        String jwt = request.getHeader("jwt");
        String email = request.getHeader("user");

        try {
            UserServiceImpl usi = new UserServiceImpl();
            User user = usi.getUserById(email);
            String tokenSecret = user.getApiToken();
            Key key = new HmacKey(tokenSecret.getBytes("UTF-8"));

            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireSubject()
                    .setExpectedIssuer("Metabolights")
                    .setExpectedAudience("Metabolights Labs")
                    .setVerificationKey(key)
                    .setRelaxVerificationKeyValidation() // relaxes key length requirement
                    .build();
            try {
                JwtClaims processedClaims = jwtConsumer.processToClaims(jwt);
                response.setHeader("jwt", jwt);
                restResponse.setContent("valid");
            } catch (InvalidJwtException e) {
                restResponse.setContent("invalid");
                response.setStatus(403);
            }

        }catch (Exception e){
            restResponse.setContent("invalid");
            response.setStatus(403);
        }

        return restResponse;
    }
}
