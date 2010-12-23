package jena.interfaces;

import java.util.ArrayList;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModelQueryInterface {
	//query operations
	boolean containELearner(String eid);
	boolean containEConcept(String cid);
	boolean containEResource(String rid);
	ArrayList<EConcept> getAllEConcepts();
	ArrayList<EConcept> getSonConcepts(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EPortfolio> getEPortfolios(ELearner elearner);
	ArrayList<EResource> getEResourcesByEConcept(EConcept concept);
	ArrayList<EResource> getEResourcesByInterestEConcepts(ELearner elearner,EConcept concept);
	ArrayList<EResource> getAllEResources();
	EConcept getEConcept(String cid);
	ELearner getELearner(String eid);
	EResource getEResource(String rid);
	EPortfolio getEPortfolio(ELearner elearner,EResource resource);
	EPerformance getEPerformance(ELearner elearner, EConcept concept);
	ArrayList<EPerformance> getEPerformances(ELearner elearner);
	
}
