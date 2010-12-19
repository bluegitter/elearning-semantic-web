package jena;

import java.io.File;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModel {
	//non-user operations
	boolean addEConcept(EConcept concept);
	boolean addEPerfomance(EPerformance performance);
	boolean addEPortfolio(EPortfolio portfolio);
	
	//user operations
	boolean addELearner(ELearner elearner);
	boolean addEResource(EResource resource);
	boolean addEInterest(EInterest interest);
	
	//add opertions for properties
	boolean addPropertyIsSonOf(EConcept fatherConcept,EConcept sonConcept);
	//io opertaions
	boolean writeToFile(File file);
	
	//query operations
	boolean containELearner(String eid);
	boolean containEConcept(String cid);
	boolean containEResource(String rid);
	ArrayList<EConcept> getAllEConcepts();
	ArrayList<EConcept> getSonConcepts(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EPortfolio> getEPortfolios(ELearner elearner);
	ArrayList<EResource> getEResourcesByEConcept(EConcept concept);
	ArrayList<EResource> getAllEResources();
	EConcept getEConcept(String cid);
	ELearner getELearner(String eid);
	EResource getEResource(String rid);
	EPortfolio getEPortfolio(ELearner elearner,EResource resource);
	EPerformance getEPerformance(ELearner elearner, EConcept concept);
	ArrayList<EPerformance> getEPerformances(ELearner elearner);
	boolean updatePerformance(EPerformance performance);
	
	
}
