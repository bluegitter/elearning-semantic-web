package jena.interfaces;

import java.io.File;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModelAddOperationInterface {
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
	boolean addPropertyIsResourceOfC(EResource resource,EConcept concept);
	
	
	
}
