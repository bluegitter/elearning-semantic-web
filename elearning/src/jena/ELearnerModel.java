package jena;

import java.io.File;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModel {
	//non-user operations
	boolean addConcept(EConcept concept);
	boolean addPerfomance(EPerformance performance);
	
	//user operations
	boolean addELearner(ELearner elearner);
	boolean addResource(EResource resource);
	boolean addInterest(EInterest interest);
	
	//io opertaions
	boolean writeToFile(File file);
	
	//query operations
	boolean containELearner(String eid);
	boolean containEConcept(String cid);
	boolean containEResource(String rid);
	ArrayList<EConcept> getAllConcepts();
	ArrayList<EConcept> getMemberConcept(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EConcept> getPerfomanceConcepts(ELearner elearner);
	ArrayList<EResource> getResourcesByKey(ELearner elearner, String keyword);
	EConcept getConcept(String cid);
	ELearner getLearner(String eid);
	EResource getResource(String rid);
	
	/***************************************************************************
	 * there are 13 rules now
	 * rule1 to rule8 -->ELearner VS EConcept
	 * rule9 rule10 --> ELearner VS ELearner
	 * rule11 to rule 13 -->ELearner VS Resource
	 * TO BE Test
	 ****************************************************************************/
	ArrayList<EConcept> getRecommendConcepts(ELearner elearner,int rule);
	ArrayList<EResource> getRecommendResources(ELearner elearner,int rule);
	ArrayList<ELearner> getRecommendELearner(ELearner elearner,int rule);
}
