package jena.testcases;

import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.resources.ISCB_Resource;
import junit.framework.TestCase;

public class ELearnerModelPropertyTest extends TestCase{
	public void testAddPropertyIsResourceOfC(){
		
	}
	public static void main(String [] args){
		ELearnerModelImpl emi = new ELearnerModelImpl();
		ISCB_Resource resource = emi.getEResource("rid00004");
		EConcept concept = emi.getRootConcept();
		emi.addPropertyIsResourceOfC(resource, concept);
		
		System.out.println(emi.getEResourcesByEConcept(concept));
	}
	public void setUp(){
		emi  = new ELearnerModelImpl();
	}
	private ELearnerModelImpl emi;
}
