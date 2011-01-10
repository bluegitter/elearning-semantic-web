package jena.interfaces;

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

    ArrayList<ISCB_Resource> getAllEResources();

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
}
