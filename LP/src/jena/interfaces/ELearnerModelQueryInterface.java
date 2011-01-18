package jena.interfaces;

import com.hp.hpl.jena.ontology.Individual;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

/**********************************************
 * The Query operations for the model
 * 
 * @author william
 *
 */
public interface ELearnerModelQueryInterface {

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

    /***************************************************************************
     * Basic Methods for fetching the ontology by given id.
     * @param id
     * @return
     */
    EConcept getEConcept(String cid);

    ELearner getELearner(String eid);

    ISCB_Resource getEResource(String rid);

    EPerformance getEPerformance(String pid);

    EPortfolio getEPortfolio(String pid);

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
