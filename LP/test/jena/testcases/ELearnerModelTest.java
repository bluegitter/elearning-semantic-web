package jena.testcases;

import java.io.IOException;
import java.util.Date;
import jena.impl.ELearnerModelImpl;
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
	public void testAddEResource(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EResource res = new EResource();
		res.setRid("res_ID");
		res.setName("res_Name");
		res.setFileLocation("res_FileLocation");
		res.setDifficulty("res_difficult");
		emi.addEResource(res);
		EResource newRes = emi.getEResource(res.getRid());
		assertTrue(newRes.getRid().equals(res.getRid()));
	}
	public void testAddPerformance(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept concept = new EConcept("testPreCnp");
		ELearner elearner = new ELearner("el002");
		EPerformance performance =getNewPerformance(concept,elearner);
		emi.addEPerfomance(performance);
		EPerformance perf = emi.getEPerformance(elearner, concept);
		
		assertTrue(perf.getId().equals(performance.getId()));
		assertTrue(performance.getValue()==perf.getValue());
		assertTrue(perf.getDatetime().toString().equals(performance.getDatetime().toString()));
	}
	public void testGetAllAfterAddPerformance(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept concept = new EConcept("testPreCnp");
		ELearner elearner = new ELearner("el002");
		EPerformance performance = getNewPerformance(concept,elearner);
		int size = emi.getEPerformances(elearner).size();
		emi.addEPerfomance(performance);
		int size2 = emi.getEPerformances(elearner).size();
		
		assertTrue(size==size2-1);
	}
	public void testAddPortfolio(){
		EResource r = emi.getEResource("rid00003");
		EPortfolio p = new EPortfolio("new_portfolio",el,r,0,new Date(System.currentTimeMillis()));
		emi.addEPortfolio(p);
		EPortfolio newP = emi.getEPortfolio(el,r);
		assertTrue(p.getId().equals(newP.getId()));
	}
	public void testGetAllAfterAddPortfolio(){
		EResource r = emi.getEResource("rid00003");
		EPortfolio p = new EPortfolio("new_portfolio",el,r,0,new Date(System.currentTimeMillis()));
		int size =  emi.getEPortfolios(el).size();
		emi.addEPortfolio(p);
		int size2 =  emi.getEPortfolios(el).size();
		assertTrue(size == size2-1);
	}
	public EPerformance getNewPerformance(EConcept concept,ELearner elearner){
		EPerformance performance= new EPerformance();
		performance.setConcept(concept);
		performance.setElearner(elearner);
		String newId ="newIDFORADD";
		float newValue = 2;
		Date date = new Date(System.currentTimeMillis());
		performance.setId(newId);
		performance.setValue(newValue);
		performance.setDatetime(date);
		return performance;
	}
	public void setUp(){
		emi  = new ELearnerModelImpl();
		el = new ELearner("el001");
	}
	private ELearnerModelImpl emi;
	private ELearner el;
}
