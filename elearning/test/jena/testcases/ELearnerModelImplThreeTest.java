package jena.testcases;

import java.io.File;

import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.EResource;

import jena.impl.ELearnerModelImplThree;
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
	public void setUp(){
		File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
		emi = new ELearnerModelImplThree(file);
	}
	private ELearnerModelImplThree emi;
	
}
