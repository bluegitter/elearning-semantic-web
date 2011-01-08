package jena.interfaces;

import java.io.File;
import java.util.ArrayList;

import exception.jena.IndividualNotExistException;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public interface ELearnerModelOperationInterface {
	/*****************************************************
	 * ADD 
	 *****************************************************/
	boolean addEConcept(EConcept concept) throws IndividualNotExistException;
	boolean addEPerfomance(EPerformance performance);
	boolean addEPortfolio(EPortfolio portfolio);
	boolean addELearner(ELearner elearner) throws IndividualNotExistException;
	boolean addEResource(EResource resource)throws IndividualNotExistException;

	boolean addPropertyIsSonOf(EConcept fatherConcept,EConcept sonConcept);
	boolean addPropertyIsResourceOfC(EResource resource,EConcept concept);
		
	/*****************************************************
	 * UPDATE 
	 *****************************************************/
	boolean updateEConcept(EConcept concept) throws IndividualNotExistException;
	boolean updateEPerfomance(EPerformance performance)throws IndividualNotExistException;
	boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException;
	boolean updateEResource(EResource resource) throws IndividualNotExistException;
	
}
