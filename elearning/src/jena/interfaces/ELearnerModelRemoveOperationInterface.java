package jena.interfaces;

import ontology.EConcept;
import ontology.EInterest;
import ontology.people.ELearner;
import exception.jena.IndividualNotExistException;

public interface ELearnerModelRemoveOperationInterface {
	
	boolean removeEInterest(EInterest interest)throws IndividualNotExistException;
	boolean removeEInterest(ELearner el,EConcept con)throws IndividualNotExistException;
	
}
