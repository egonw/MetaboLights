/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/2/14 2:38 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.species.core.tools;

import org.junit.Test;
import uk.ac.ebi.metabolights.species.model.Taxon;
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 13/11/2013
 * Time: 11:27
 */
public class GrouperTest {
	@Test
	public void testGetGroupFromTaxon() throws Exception {

		Grouper grouper = new Grouper();

		Taxon result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNotNull("Group for NEWT root element should be itelf", result);


		// Set a group for animals...
		Taxon animals = TaxonConverter.stringToTaxon("NEWT:33208");

		grouper.getTaxonGroups().add(animals);



		// Try human
		Taxon human = TaxonConverter.stringToTaxon("NEWT:9606");

		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animals.getId() , result.getId());


		// Try human (NCBI version)
		human = TaxonConverter.stringToTaxon("NCBI:9606");

		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NCBI:9606 should be Animals NEWT:33208", animals.getId() , result.getId());




		// Lets try now with a green plants...it should return the root node
		Taxon greenPlants = TaxonConverter.stringToTaxon("NEWT:33090");

		result =  grouper.getGroupFromTaxon(greenPlants);

		assertEquals("Group for green plants should be root NEWT:1", "NEWT:1" , result.getId());


		// Add now the green plants as a group...
		grouper.getTaxonGroups().add(greenPlants);

		// Lets try now with a green plants...it should return itself
		result = grouper.getGroupFromTaxon(greenPlants);

		assertEquals("Group for green plants should be itself NEWT:33090", greenPlants.getId() , result.getId());


		// Try a plant...Arabidopsis thaliana (thale cress)
		result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:3702"));

		assertEquals("Group for arabisopsis should be Green plants", greenPlants.getId() , result.getId());

		// Human should still return animals
		result = grouper.getGroupFromTaxon(human);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animals.getId() , result.getId());




	}

	@Test
	public void testGetGroupFromTaxon2() throws Exception {

		Grouper grouper = new Grouper();

		IParentSearcher woRMSSearcher = new WoRMSPArentSearcher();
		grouper.getParentSearchers().add(woRMSSearcher);

		Taxon result = grouper.getGroupFromTaxon(TaxonConverter.stringToTaxon("NEWT:1"));

		assertNotNull("Group for NEWT root element should be itelf", result);

		// Set a group for animals... (WoRMS and NCBI)
		Taxon animalsNEWT = TaxonConverter.stringToTaxon("NEWT:33208");
		Taxon animalsWoRMS = TaxonConverter.stringToTaxon("WORMS:2");

		grouper.getTaxonGroups().add(animalsNEWT);
		grouper.getTaxonGroups().add(animalsWoRMS);

		// Try human
		Taxon humanNEWT = TaxonConverter.stringToTaxon("NEWT:9606");
		Taxon pontinus = new Taxon(WoRMSPArentSearcher.WoRMS_PREFIX + ":" + 127240, "Pontinus kuhlii (Bowdich, 1825)", "","");

		result = grouper.getGroupFromTaxon(humanNEWT);

		assertEquals("Group for NEWT:9606 should be Animals NEWT:33208", animalsNEWT.getId() , result.getId());


		result = grouper.getGroupFromTaxon(pontinus);

		assertEquals("Group for WORMS:127240 should be Animals WORMS:2", animalsWoRMS.getId() , result.getId());


	}

	@Test
	public void testGetGroupFromGlobalNames() throws Exception {

		Grouper grouper = new Grouper();
		grouper.setGlobalNamesEnabled(true);

		// Set a group for fishes...
		Taxon fish = TaxonConverter.stringToTaxon("NCBI:117570", "Teleostomi");

		grouper.getTaxonGroups().add(fish);

		// Try pontinus kuhlii
		Taxon pontinus = TaxonConverter.stringToTaxon("NONE:123", "Pontinus kuhlii");


		Taxon result = grouper.getGroupFromTaxon(pontinus);

		assertEquals("Group for NONE:123 should be fish NCBI:117570", fish.getId() , result.getId());


	}


}
