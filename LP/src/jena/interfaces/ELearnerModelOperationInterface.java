package jena.interfaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;
import exception.jena.IndividualExistException;
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
    boolean addEConcept(EConcept concept) throws IndividualExistException;

    boolean addEPerfomance(EPerformance performance) throws IndividualExistException;

    boolean addEPortfolio(EPortfolio portfolio) throws IndividualExistException;

    boolean addELearner(ELearner elearner) throws IndividualExistException;

    boolean addEResource(ISCB_Resource resource) throws IndividualExistException;

    boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept) throws IndividualNotExistException;

    boolean addPropertyIsResourceOfC(ISCB_Resource resource, EConcept concept) throws IndividualNotExistException;

    /*****************************************************
     * UPDATE
     *****************************************************/
    boolean updateEConcept(EConcept concept) throws IndividualNotExistException;

    boolean updateEPerfomance(EPerformance performance) throws IndividualNotExistException;

    boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException;

    boolean updateEResource(ISCB_Resource resource) throws IndividualNotExistException;

    Individual getFileFormat(String postfix);

    String getPostFix(Resource fileFormat);

    /***************************************************************************
     * Basic Methods for fetching the ontology by given id.
     * @param id
     * @return
     ****************************************************************************/
    EConcept getEConcept(String cid);

    ELearner getELearner(String eid);

    ISCB_Resource getEResource(String rid);

    EPerformance getEPerformance(String pid);

    EPortfolio getEPortfolio(String pid);
}
