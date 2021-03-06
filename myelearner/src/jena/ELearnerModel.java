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
	
	//io opertaions
	boolean writeToFile(File file);
	
	//query operations
	boolean containELearner(String eid);
	boolean containEConcept(String cid);
	boolean containEResource(String rid);
	ArrayList<EConcept> getAllEConcepts();
	ArrayList<EConcept> getSonConcepts(EConcept concept);
	ArrayList<EConcept> getMemberConcept(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EResource> getResourcesByKey(ELearner elearner, String keyword);
	ArrayList<EPortfolio> getPortfolios(ELearner elearner);
	ArrayList<EResource> getEResourcesByEConcepts(EConcept concept);
	ArrayList<EResource> getAllEResources();
	EConcept getEConcept(String cid);
	ELearner getELearner(String eid);
	EResource getEResource(String rid);
	
	EPerformance getEPerformance(ELearner elearner, EConcept concept);
	ArrayList<EPerformance> getEPerformances(ELearner elearner);
	boolean updatePerformance(EPerformance performance);
	/***************************************************************************
	 * there are 13 rules now
	 * is_recommend_of_c_ (0--8)-->ELearner VS EConcept
	 * is_recommend_of_L_ (0 1)--> ELearner VS ELearner
	 * is_recommend_of_r_ (0 3) -->ELearner VS Resource
	 * TO BE Test
	 ****************************************************************************/
	ArrayList<EConcept> getRecommendEConcepts(ELearner elearner,int rule);
	ArrayList<EResource> getRecommendEResources(ELearner elearner,int rule);
	ArrayList<ELearner> getRecommendELearners(ELearner elearner,int rule);
}
