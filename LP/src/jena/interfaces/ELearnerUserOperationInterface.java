package jena.interfaces;

import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.people.ELearner;

public interface ELearnerUserOperationInterface {

    boolean updateELearner(ELearner elearner) throws IndividualNotExistException;

    boolean addEInterest(EInterest interest) throws IndividualExistException;

    boolean updateEInterest(EInterest interest) throws IndividualNotExistException;

    boolean removeEInterest(EInterest interest) throws IndividualNotExistException;

    boolean removeEInterest(ELearner el, EConcept con) throws IndividualNotExistException;
}
