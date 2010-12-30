package jena.interfaces;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModelUpdateOperationInterface {
	//non-user operations
	boolean updateEConcept(EConcept concept);
	boolean updateEPerfomance(EPerformance performance);
	boolean updateEPortfolio(EPortfolio portfolio);
	
	//user operations
	boolean updateELearner(ELearner elearner);
	boolean updateEResource(EResource resource);
	boolean updateEInterest(EInterest interest);
	
}
