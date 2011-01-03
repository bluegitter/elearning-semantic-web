package jena.interfaces;

import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
/**********************************************
 * The Query operations for the model
 * 
 * @author william
 *
 */
public interface ELearnerModelQueryInterface {
	ArrayList<EConcept> getAllEConcepts();
	ArrayList<EConcept> getSonConcepts(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EPortfolio> getEPortfolios(ELearner elearner);
	ArrayList<EResource> getEResourcesByEConcept(EConcept concept);
	ArrayList<EResource> getEResourcesByInterestEConcepts(ELearner elearner);
	ArrayList<EResource> getAllEResources();
	EConcept getEConcept(String cid);
	ELearner getELearner(String eid);
	EResource getEResource(String rid);
	EInterest getEInterest(ELearner elearner,EConcept concept);
	EPortfolio getEPortfolio(ELearner elearner,EResource resource);
	EPerformance getEPerformance(ELearner elearner, EConcept concept);
	ArrayList<EPerformance> getEPerformances(ELearner elearner);
	
}
