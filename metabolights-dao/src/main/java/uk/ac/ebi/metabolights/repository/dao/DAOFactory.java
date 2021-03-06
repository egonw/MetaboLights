/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-28
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.metabolights.repository.dao.hibernate.AccessionDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.dao.hibernate.HibernateUtil;
import uk.ac.ebi.metabolights.repository.dao.hibernate.UserDAO;

import javax.sql.DataSource;

/**
 * User: conesa
 * Date: 28/01/15
 * Time: 10:55
 */
public class DAOFactory {

	private static DAOFactory instance;
	private static String isaTabRootConfigurationFolder;
	private static String studiesFolder;
	private static final Logger logger = LoggerFactory.getLogger(DAOFactory.class);
	public static String defaultPrefix = "MTBLS";

	public static DAOFactory initialize (String isaTabRootConfigurationFolder, String studiesFolder, Configuration configuration) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, studiesFolder, defaultPrefix);

		HibernateUtil.initialize(configuration);

		return getInstance();

	}

	public static DAOFactory initialize (String isaTabRootConfigurationFolder, String studiesFolder, String JNDIDataSource) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, studiesFolder, defaultPrefix);

		HibernateUtil.initialize(JNDIDataSource);

		return getInstance();

	}

	public static DAOFactory initializeWithDataSource(String isaTabRootConfigurationFolder, String studiesFolder, DataSource dataSource, String defaultPrefix) throws DAOException {

		initializeFields(isaTabRootConfigurationFolder, studiesFolder, defaultPrefix);

		HibernateUtil.initialize(dataSource);

		return getInstance();

	}

	private static void initializeFields(String isaTabRootConfigurationFolder, String studiesFolder, String defaultPrefix) {
		if (isInitialized()) {
			logger.warn("DAOFactory is already initialized..this shouldn't happen unless you've found a good use case for it. Be careful!");
		}

		DAOFactory.isaTabRootConfigurationFolder = isaTabRootConfigurationFolder;
		DAOFactory.studiesFolder = studiesFolder;
		DAOFactory.defaultPrefix = defaultPrefix;

	}


	public static DAOFactory getInstance() throws DAOException {

		if (!isInitialized()) {
			throw new DAOException("Configure first the DAOFactory before requesting an instance");
		}

		if (instance == null) {
			instance = new DAOFactory();

		}

		return instance;
	}
	private DAOFactory(){}

	private static boolean isInitialized(){
		return isaTabRootConfigurationFolder != null;
	}
	public static String getIsaTabRootConfigurationFolder() {
		return isaTabRootConfigurationFolder;
	}
	public static String getStudiesFolder() {
		return studiesFolder;
	}

	/**
	 * Default prefix to use in case of creating the table from scratch
	 * @return String with the default prefix
	 */
	public static String getDefaultPrefix (){
		return defaultPrefix;
	}

	public static void setDefaultPrefix(String newDefaultPrefix){
		defaultPrefix = newDefaultPrefix;
	}

	public StudyDAO getStudyDAO(){
		StudyDAO newStudyDAO = new StudyDAO(isaTabRootConfigurationFolder, studiesFolder);
		return newStudyDAO;
	}
	public UserDAO getUserDAO() {
		return new UserDAO();
	}

	public AccessionDAO getAccessionDAO() {
		return new AccessionDAO();
	}
}
