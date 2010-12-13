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
	ArrayList<EConcept> getSonConcepts(EConcept concept);
	ArrayList<EConcept> getMemberConcept(EConcept concept);
	ArrayList<EConcept> getInterestConcepts(ELearner elearner);
	ArrayList<EConcept> getPerfomanceConcepts(ELearner elearner);
	ArrayList<EResource> getResourcesByKey(ELearner elearner, String keyword);
	EConcept getConcept(String cid);
	ELearner getLearner(String eid);
	EResource getResource(String rid);
	
	/***************************************************************************
	 * there are 13 rules now
	 * rule_c_0 to rule_c_8 -->ELearner VS EConcept
	 * rule_L_0 rule_L_1 --> ELearner VS ELearner
	 * rule_r_0 to rule_r_3 -->ELearner VS Resource
	 * TO BE Test
	 ****************************************************************************/
	ArrayList<EConcept> getRecommendConcepts(ELearner elearner,int rule);
	ArrayList<EResource> getRecommendResources(ELearner elearner,int rule);
	ArrayList<ELearner> getRecommendELearner(ELearner elearner,int rule);
}
