package jena.interfaces;

import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModelUpdateOperationInterface {
	//non-user operations
	boolean updateEConcept(EConcept concept) throws IndividualNotExistException;
	boolean updateEPerfomance(EPerformance performance)throws IndividualNotExistException;
	boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException;
	
	//user operations
	boolean updateELearner(ELearner elearner) throws IndividualNotExistException;
	boolean updateEResource(EResource resource) throws IndividualNotExistException;
	
}
