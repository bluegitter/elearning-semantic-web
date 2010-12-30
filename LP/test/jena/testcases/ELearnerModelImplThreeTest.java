package jena.testcases;

import java.io.File;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.StringExchanger;

import jena.impl.ELearnerModelImpl;
import junit.framework.TestCase;

public class ELearnerModelImplThreeTest extends TestCase {
	public void testGetEConcept(){
		EConcept concept = emi.getEConcept("CMP");
		assertTrue(concept.getName().equals("计算基础"));
	}
	public void testGetEResource(){
		EResource resource = emi.getEResource("rid000001");
		assertTrue(resource.getDifficulty().equals("easy"));
		assertTrue(resource.getFileLocation().equals("74\\page\\chap01\\010101.asp"));
		assertTrue(resource.getName().equals("数据结构绪论"));
	}
	public void testGetELearner(){
		ELearner elearner = emi.getELearner("el001");
		assertTrue(elearner.getName().equals("Ma Sheng"));
	}
	public void testGetAllEResources(){
		ArrayList<EResource>resources = emi.getAllEResources();
		assertTrue(resources.size()==639);
	}
	public void testGetEPortfolios(){
		ELearner el = emi.getELearner("el001");
		ArrayList<EPortfolio> ports = emi.getEPortfolios(el);
		assertTrue(ports.size()==2);
	}
	public void testGetEPerformance(){
		ELearner el = emi.getELearner("el001");
		EConcept con = emi.getEConcept("cid1");
		EPerformance perform = emi.getEPerformance(el, con);
		assertTrue(perform.getId().equals("E_Performance_1_2"));
		assertTrue(perform.getDatetime().equals(StringExchanger.parseStringToDate("2010-12-19T14:34:47")));
		assertTrue(perform.getValue()==1.0);
	}
	public void testGetRecommendEConcepts(){
		
	}
	public void testGetRecommendELearners(){
		
	}
	public void testGetRecommendEResources(){
		
	}
	
	public void setUp(){
		File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
		emi = new ELearnerModelImpl(file);
	}
	private ELearnerModelImpl emi;
	
}
