package jena.interfaces;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.Resource;
import exception.jena.IndividualExistException;
import exception.jena.IndividualNotExistException;
import java.util.ArrayList;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

public interface ELearnerModelOperationInterface {

    /***************************************************************************
     * ADD OPERATION INTERFACE
     ***************************************************************************/
    boolean addEConcept(EConcept concept) throws IndividualExistException;

    boolean addEPerfomance(EPerformance performance) throws IndividualExistException;

    boolean addEPortfolio(EPortfolio portfolio) throws IndividualExistException;

    boolean addELearner(ELearner elearner) throws IndividualExistException;

    boolean addEResource(ISCB_Resource resource) throws IndividualExistException;

    boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept) throws IndividualNotExistException;

    boolean addPropertyIsResourceOfC(ISCB_Resource resource, EConcept concept) throws IndividualNotExistException;

    /***************************************************************************
     * UPDATE OPERATION INTERFACE
     ***************************************************************************/
    boolean updateEConcept(EConcept concept) throws IndividualNotExistException;

    boolean updateEPerfomance(EPerformance performance) throws IndividualNotExistException;

    boolean updateEPortfolio(EPortfolio portfolio) throws IndividualNotExistException;

    boolean updateEResource(ISCB_Resource resource) throws IndividualNotExistException;

    /*******************************************************************************************
     * REMOVE OPERATION INTERFACE
     *******************************************************************************************/
     boolean removeEConcept(String cid);


    Individual getFileFormat(String postfix);

    String getPostFix(Resource fileFormat);

    /*************************************************************************************
     * QUERY INTERFACE
     ****************************************************************************/
    EConcept getEConcept(String cid);

    ELearner getELearner(String eid);

    ISCB_Resource getEResource(String rid);

    EPerformance getEPerformance(String pid);

    EPortfolio getEPortfolio(String pid);

    ArrayList<EConcept> getAllEConcepts();

    /***************************************************************************
     * return a list of concepts which are the son of the given concept
     * @param concept
     * @return a list with concepts or an empty list
     ****************************************************************************/
    ArrayList<EConcept> getSonConcepts(EConcept concept);

    ArrayList<EConcept> getMemberConcepts(EConcept concept);

    ArrayList<EConcept> getInterestConcepts(ELearner elearner);

    ArrayList<EConcept> getUnInterestConcepts(ELearner elearner);

    ArrayList<EConcept> getEConcepts(ISCB_Resource resource);

    /***************************************************************************
     * return a list of EPortfolios which are the portfolios of the given elearner
     * @param elearner
     * @return a list with EPortfolios or an empty list
     ****************************************************************************/
    ArrayList<EPortfolio> getEPortfolios(ELearner elearner);

    /***************************************************************************
     * return a list of EPerformances which are the performances of the given elearner
     * @param elearner
     * @return a list with EPerformances or an empty list
     ****************************************************************************/
    ArrayList<EPerformance> getEPerformances(ELearner elearner);

    ArrayList<ISCB_Resource> getEResourcesByEConcept(EConcept concept);

    ArrayList<ISCB_Resource> getEResourcesByInterestEConcepts(ELearner elearner);

    ArrayList<ISCB_Resource> getLearntEResources(ELearner elearner, EConcept concept);

    ArrayList<ISCB_Resource> getEResourcesByName(String name);

    ArrayList<EInterest> getEInterests(ELearner elearner);

    ArrayList<ISCB_Resource> getEResourcesByTypes(String applicationType, String fileFormat, String mediaType);

    ArrayList<ISCB_Resource> getEResourcesByAppType(String applicationType);

    ArrayList<ISCB_Resource> getEResourcesByMeidaType(String mediaType);

    ArrayList<ISCB_Resource> getEResourcesByFileFormat(String fileFormat);

    ArrayList<ISCB_Resource> getAllEResources();

    EInterest getEInterest(ELearner elearner, EConcept concept);

    /**************************************************************************
     *
     * @param elearner
     * @param resource
     * @return the portfolio of the elearner concerning the resource
     *          null if such portfolio does not exist in the model
     **************************************************************************/
    EPortfolio getEPortfolio(ELearner elearner, ISCB_Resource resource);

    /************************************************************************
     * @param elearner
     * @param concept
     * @return the performance of the eleaner concerning the concept
     *          null if such performance does not exist in the model
     ***********************************************************************/
    EPerformance getEPerformance(ELearner elearner, EConcept concept);

    Individual getFileFormatIndividualByFullName(String name);
}
