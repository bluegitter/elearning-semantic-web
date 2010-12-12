package jena;

import java.io.IOException;

import ontology.EConcept;
import ontology.people.ELearner;
import junit.framework.TestCase;

public class ELearnerModelQueryTest extends TestCase{
	public void testGetConcept(){
		EConcept con = emi.getConcept("cmp.cf9.001");
		assertTrue(con.getName().equals("c"));
	}
	public void testGetELearner(){
		
		ELearner c = emi.getLearner("el001");
		assertTrue(c.getName().equals("Ma Sheng"));
	}
	public void setUp(){
		emi  = new ELearnerModelImpl();
	}
	private ELearnerModelImpl emi;
}
