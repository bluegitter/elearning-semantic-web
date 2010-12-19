package jena;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;
import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;

public class ELearnerModelTest extends TestCase{
	
	public void testAddEConcept(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept con = new EConcept();
		con.setCid("testAddConcept");
		con.setName("testName");
		emi.addEConcept(con);
		EConcept newC = emi.getEConcept(con.getCid());
		assertTrue(newC.getCid().equals(con.getCid()));
	}
	public void testAddElearner()throws IOException{
		ELearnerModelImpl emi = new ELearnerModelImpl();
		ELearner el = new ELearner();
		el.setId("eltest");
		el.setName("testName");
		el.setAddress("testAddress");
		emi.addELearner(el);
		ELearner newE = emi.getELearner(el.getId());
		assertTrue(newE.getId().equals(el.getId()));
	}
	public void testAddPerformance(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept concept = new EConcept("testPreCnp");
		ELearner elearner = new ELearner("el002");
		EPerformance performance = new EPerformance();
		performance.setConcept(concept);
		performance.setElearner(elearner);
		String newId ="newIDFORADD";
		performance.setId(newId);
		float newValue = 2;
		performance.setValue(newValue);
		
		emi.addEPerfomance(performance);
		
		EPerformance perf = emi.getEPerformance(elearner, concept);
		
		assertTrue(perf.getId().equals(newId));
		assertTrue(newValue==perf.getValue());
	}
	public void testAddPortfolio(){
		EResource r = emi.getEResource("rid00003");
		EPortfolio p = new EPortfolio("new_portfolio",el,r,0);
		int size =  emi.getEPortfolios(el).size();
		emi.addEPortfolio(p);
		EPortfolio newP = emi.getEPortfolio(el,r);
		//ArrayList<EPortfolio> c = emi.getEPortfolios(el);
		assertTrue(p.getId().equals(newP.getId()));
	}
	public void testGetAllPortfolios(){
		
	}
	public void setUp(){
		emi  = new ELearnerModelImpl();
		rootConcept = new EConcept("Software_Engineer");
		el = new ELearner("el001");
	}
	private ELearnerModelImpl emi;
	private EConcept rootConcept;
	private ELearner el;
}
