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
	boolean hasELearner(ELearner elearner);
	ArrayList<EConcept> getAllConcepts();
	ArrayList<EConcept> getConcepts(ELearner elearner);
	ArrayList<EConcept> getRecommandConcepts(ELearner elearner);
	ArrayList<EConcept> getRecommandResources(ELearner elearner);
	ArrayList<EResource> getResourcesByKey(ELearner elearner, String keyword);
	
}
