package jena.testcases;

import jena.impl.ELearnerModelImplOne;
import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.EResource;
import junit.framework.TestCase;

public class ELearnerModelPropertyTest extends TestCase{
	public void testAddPropertyIsResourceOfC(){
		
	}
	public static void main(String [] args){
		ELearnerModelImplOne emi = new ELearnerModelImplOne();
		EResource resource = emi.getEResource("rid00004");
		EConcept concept = emi.getRootConcept();
		emi.addPropertyIsResourceOfC(resource, concept);
		
		System.out.println(emi.getEResourcesByEConcept(concept));
	}
	public void setUp(){
		emi  = new ELearnerModelImplOne();
	}
	private ELearnerModelImplOne emi;
}
