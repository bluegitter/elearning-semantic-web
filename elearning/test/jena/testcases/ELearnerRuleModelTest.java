package jena.testcases;

import java.util.ArrayList;

import jena.impl.ELearnerModelImpl;

import junit.framework.TestCase;

import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.EResource;

public class ELearnerRuleModelTest extends TestCase{
	public void testGetMemberConcepts(){
		ArrayList<EConcept> c = emi.getMemberConcept(rootConcept);
		assertTrue(c.size()==30);
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
	public void setUp(){
		emi  = new ELearnerModelImpl();
		rootConcept = new EConcept("Software_Engineer");
		el = new ELearner("el001");
	}
	private ELearnerModelImpl emi;
	private EConcept rootConcept;
	private ELearner el;
}
