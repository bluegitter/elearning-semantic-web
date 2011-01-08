package jena.interfaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;
import exception.jena.IndividualNotExistException;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

public interface ELearnerModelOperationInterface {

    /*****************************************************
     * ADD
     *****************************************************/
    boolean addEConcept(EConcept concept) throws IndividualNotExistException;

    boolean addEPerfomance(EPerformance performance);

    boolean addEPortfolio(EPortfolio portfolio);

    boolean addELearner(ELearner elearner) throws IndividualNotExistException;

    boolean addEResource(ISCB_Resource resource) throws IndividualNotExistException;

    boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept);

    boolean addPropertyIsResourceOfC(ISCB_Resource resource, EConcept concept);

    /*****************************************************
     * UPDATE
     *****************************************************/
    boolean updateEConcept(EConcept concept) throws IndividualNotExistException;

    boolean updateEPerfomance(EPerformance performance) throws IndividualNotExistException;

    boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException;

    boolean updateEResource(ISCB_Resource resource) throws IndividualNotExistException;

    public Individual getFileFormat(String postfix);

    public String getPostFix(Resource fileFormat);
}
