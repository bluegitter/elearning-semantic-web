package jena;

import java.util.ArrayList;
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
	public void testGetMemberConcepts(){
		ArrayList<EConcept> c = emi.getMemberConcept(rootConcept);
		assertTrue(c.size()==30);
	}
	public void testGetInterestConcepts(){
		ArrayList<EConcept> c = emi.getInterestConcepts(el);
		assertTrue(c.size()==5);
	}
	public void testAllPerformanceByELearner(){
		ArrayList<EPerformance> c = emi.getPerformances(el);
		assertTrue(c.size()==3);
	}
	public void testAddPerformance(){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept concept = new EConcept("testPreCnp");
		ELearner elearner = new ELearner("el001");
		EPerformance performance = new EPerformance();
		performance.setConcept(concept);
		performance.setElearner(elearner);
		String newId ="newIDFORADD";
		performance.setId(newId);
		float newValue = 2;
		performance.setValue(newValue);
		emi.addEPerfomance(performance);
		
		EPerformance perf = emi.getPerformance(elearner, concept);
		assertTrue(perf.getId().equals(newId));
		assertTrue(newValue==perf.getValue());
	}
	public void testContainELearner(){
		assertTrue(emi.containELearner("el001"));
	}
	public void testContainEConcept(){
		assertTrue(emi.containEConcept("Software_Engineer"));
	}
	public void testContainEResource(){
		assertTrue(emi.containEResource("rid00001"));
	}
	public void testGetSonConcepts(){
		ArrayList<EConcept> c = emi.getSonConcepts(rootConcept);
		assertTrue(c.size()==11);
	}
	public void testGetPortfolioResources(){
		ArrayList<EResource> c = emi.getPortfolioResources(el);
		assertTrue(c.size()==2);
	}
	public void testGetRecommendELearner(){
		ArrayList<ELearner> c = emi.getRecommendELearners(el, 0);
		assertTrue(c.size()==1);
	}
	public void testGetRecommendResource(){
		ArrayList<EResource> c = emi.getRecommendEResources(el, 0);
		assertTrue(c.size()==2);
	}
	public void testGetRecommendEConcept(){
		ArrayList<EConcept> c = emi.getRecommendEConcepts(el, 0);
		assertTrue(c.size()==5);
	}
	public void testGetConceptResources(){
		ArrayList<EResource> c = emi.getConceptResources(rootConcept);
		System.out.println("size:"+c.size());
	}
	public void testAddPortfolio(){
		EResource r = emi.getEResource("rid00003");
		EPortfolio p = new EPortfolio("new portfolio",el,r,0);
		int size =  emi.getPortfolioResources(el).size();
		emi.addEPortfolio(p);
		ArrayList<EResource> c = emi.getPortfolioResources(el);
		assertTrue(c.size() == (size+1));
	}
	public void testGetAllResources(){
		ArrayList<EResource> c = emi.getAllEResources();
		assertTrue(c.size()==4);
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
