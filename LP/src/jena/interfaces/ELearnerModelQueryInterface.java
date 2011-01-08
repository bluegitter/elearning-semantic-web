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

    ArrayList<EConcept> getSonConcepts(EConcept concept);

    ArrayList<EConcept> getInterestConcepts(ELearner elearner);

    ArrayList<EConcept> getUnInterestConcepts(ELearner elearner);

    ArrayList<EPortfolio> getEPortfolios(ELearner elearner);

    ArrayList<ISCB_Resource> getEResourcesByEConcept(EConcept concept);

    ArrayList<ISCB_Resource> getEResourcesByInterestEConcepts(ELearner elearner);

    ArrayList<ISCB_Resource> getAllEResources();

    EConcept getEConcept(String cid);

    ELearner getELearner(String eid);

    ISCB_Resource getEResource(String rid);

    EInterest getEInterest(ELearner elearner, EConcept concept);

    EPortfolio getEPortfolio(ELearner elearner, ISCB_Resource resource);

    EPerformance getEPerformance(ELearner elearner, EConcept concept);

    EPerformance getEPerformance(String pid);

    EPortfolio getEPortfolio(String pid);

    ArrayList<EPerformance> getEPerformances(ELearner elearner);
}
