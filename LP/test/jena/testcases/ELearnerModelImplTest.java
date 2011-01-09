package jena.testcases;

import exception.jena.IndividualNotExistException;
import java.io.File;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import util.StringExchanger;

import jena.impl.ELearnerModelImpl;
import junit.framework.TestCase;
import ontology.EInterest;

public class ELearnerModelImplTest extends TestCase {

    public void updateELearner(ELearner elearner) throws IndividualNotExistException {
        emi.getELearner("el002");
    }

    public void addEInterest(EInterest interest) throws IndividualNotExistException {
    }

    public void updateEInterest(EInterest interest) throws IndividualNotExistException {
    }

    public void removeEInterest(EInterest interest) throws IndividualNotExistException {
    }

    public void removeEInterest(ELearner el, EConcept con) throws IndividualNotExistException {
    }

    public void setUp() {
        File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
        emi = new ELearnerModelImpl(file);
    }
    private ELearnerModelImpl emi;
}
