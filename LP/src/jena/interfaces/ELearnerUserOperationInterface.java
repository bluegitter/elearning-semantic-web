package jena.interfaces;

import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerUserOperationInterface {
	boolean updateELearner(ELearner elearner) throws IndividualNotExistException;
	boolean addEInterest(EInterest interest)throws IndividualNotExistException;
	
	boolean removeEInterest(EInterest interest)throws IndividualNotExistException;
	boolean removeEInterest(ELearner el,EConcept con)throws IndividualNotExistException;
	
	
}
