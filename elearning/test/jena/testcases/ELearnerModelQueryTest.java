package jena.testcases;

import java.util.ArrayList;

import jena.impl.ELearnerModelImplOne;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import junit.framework.TestCase;

public class ELearnerModelQueryTest extends TestCase{
	public void testGetConcept(){
		EConcept con = emi.getEConcept("CMP.CF9_001_C");
		assertTrue(con.getName().equals("c"));
	}
	public void testGetELearner(){
		ELearner c = emi.getELearner("el001");
		assertTrue(c.getName().equals("Ma Sheng"));
	}
	public void testGetEResource(){
		EResource res = emi.getEResource("rid00001");
		assertTrue(res.getName().equals("semantic web primer"));
	}
	public void testGetAllConcepts(){
		ArrayList<EConcept> c = emi.getAllEConcepts();
		assertTrue(c.size()==36);
	}
	
	public void testGetInterestConcepts(){
		ArrayList<EConcept> c = emi.getInterestConcepts(el);
		assertTrue(c.size()==5);
	}
	public void testGetPerformances(){
		ArrayList<EPerformance> c = emi.getEPerformances(el);
		assertTrue(c.size()==3);
	}
	public void testContainELearner(){
		assertTrue(emi.containELearner("el001"));
	}
	public void testContainEConcept(){
		assertTrue(emi.containEConcept("Software_Engineer"));
	}
	public void testContainEConcept2(){
		assertFalse(emi.containEConcept("noConcept"));
	}
	public void testContainEResource(){
		assertTrue(emi.containEResource("rid00001"));
	}
	public void testGetSonConcepts(){
		ArrayList<EConcept> c = emi.getSonConcepts(rootConcept);
		assertTrue(c.size()==11);
	}
	public void testGetPortfolios(){
		ArrayList<EPortfolio> c = emi.getEPortfolios(el);
		assertTrue(c.size()==2);
	}
	public void testGetPortfolio(){
		EResource res = emi.getEResource("rid00001");
		EPortfolio ep = emi.getEPortfolio(el, res);
		assertTrue(ep.getValue()==1);
	}
	
	public void testGetConceptResources(){
		ArrayList<EResource> c = emi.getEResourcesByEConcept(rootConcept);
	}
	
	public void testGetAllResources(){
		ArrayList<EResource> c = emi.getAllEResources();
		assertTrue(c.size()==6);
	}
	public void setUp(){
		emi  = new ELearnerModelImplOne();
		rootConcept = new EConcept("Software_Engineer");
		el = new ELearner("el001");
	}
	private ELearnerModelImplOne emi;
	private EConcept rootConcept;
	private ELearner el;
}
