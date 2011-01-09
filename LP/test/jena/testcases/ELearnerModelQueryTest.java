package jena.testcases;

import java.util.ArrayList;

import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;
import junit.framework.TestCase;
import util.StringExchanger;

public class ELearnerModelQueryTest extends TestCase {

    public void testGetConcept() {
        EConcept con = emi.getEConcept("CMP.CF9_001_C");
        assertTrue(con.getName().equals("c"));
    }

    public void testGetELearner() {
        ELearner c = emi.getELearner("el001");
        assertTrue(c.getName().equals("Ma Sheng"));
    }

    public void testGetEResource() {
        ISCB_Resource res = emi.getEResource("rid00001");
        assertTrue(res.getName().equals("A_Semantic_Web_Primer 2nd"));
    }

    public void testGetEResourceTwo() {
        ISCB_Resource resource = emi.getEResource("rid000001");
        assertTrue(resource.getDifficulty().equals("easy"));
        assertTrue(resource.getFileLocation().equals("74\\page\\chap01\\010101.asp"));
        assertTrue(resource.getName().equals("数据结构绪论"));
    }

    public void testGetAllConcepts() {
        ArrayList<EConcept> c = emi.getAllEConcepts();
        assertTrue(c.size() == 598);
    }

    public void testGetAllEResources() {
        ArrayList<ISCB_Resource> resources = emi.getAllEResources();
        assertTrue(resources.size() == 639);
    }

    public void testGetInterestConcepts() {
        ArrayList<EConcept> c = emi.getInterestConcepts(el);
        assertTrue(c.size() == 5);
    }

    public void testContainELearner() {
        assertTrue(emi.containELearner("el001"));
    }

    public void testContainEConcept() {
        assertTrue(emi.containEConcept("Computer_Science"));
    }

    public void testContainEConcept2() {
        assertFalse(emi.containEConcept("noConcept"));
    }

    public void testContainEResource() {
        assertTrue(emi.containEResource("rid00001"));
    }

    public void testGetSonConcepts() {
        ArrayList<EConcept> c = emi.getSonConcepts(rootConcept);
        assertTrue(c.size() == 11);
    }

    public void testGetPortfolios() {
        ArrayList<EPortfolio> c = emi.getEPortfolios(el);
        assertTrue(c.size() == 2);
    }

    public void testGetPortfolio() {
        ISCB_Resource res = emi.getEResource("rid00001");
        EPortfolio ep = emi.getEPortfolio(el, res);
        assertTrue(ep.getValue() == 1);
    }

    public void testGetEPortfolio() {
        ELearner el = emi.getELearner("el001");
        ISCB_Resource resource = emi.getEResource("rid000622");
        EPortfolio port = emi.getEPortfolio(el, resource);
        assertTrue(port.getId().equals("E_Portfolio_el001-1"));
        assertTrue(port.getDatetime().equals(StringExchanger.parseStringToDate("2010-12-19T22:31:40")));
        assertTrue(port.getValue() == 1.0);
    }

    public void testGetEPerformance() {
        ELearner el = emi.getELearner("el001");
        EConcept con = emi.getEConcept("cid1");
        EPerformance perform = emi.getEPerformance(el, con);
        assertTrue(perform.getId().equals("E_Performance_1_2"));
        assertTrue(perform.getDatetime().equals(StringExchanger.parseStringToDate("2010-12-19T14:34:47")));
        assertTrue(perform.getValue() == 1.0);
    }

    public void testGetPerformances() {
        ArrayList<EPerformance> c = emi.getEPerformances(el);
        assertTrue(c.size() == 8);
    }

    public void testGetConceptResources() {
        ArrayList<ISCB_Resource> c = emi.getEResourcesByEConcept(rootConcept);
    }

    public void testGetAllResources() {
        ArrayList<ISCB_Resource> c = emi.getAllEResources();
        assertTrue(c.size() == 6);
    }

    public void setUp() {
        emi = new ELearnerModelImpl();
        rootConcept = new EConcept("Computer_Science");
        el = new ELearner("el001");
    }
    private ELearnerModelImpl emi;
    private EConcept rootConcept;
    private ELearner el;
}
