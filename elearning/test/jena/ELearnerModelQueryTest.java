package jena;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.InfModel;

import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.EResource;
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
	public void testGetEResource(){
		EResource res = emi.getResource("rid00001");
		assertTrue(res.getName().equals("semantic web primer"));
	}
	public void testGetAllConcepts(){
		ArrayList<EConcept> c = emi.getAllConcepts();
		assertTrue(c.size()==31);
	}
	public void testGetMemberConcepts(){
		EConcept concept = new EConcept("root-se");
		ArrayList<EConcept> c = emi.getMemberConcept(concept);
		assertTrue(c.size()==30);
	}
	public void testGetELearnerConcepts(){
		ELearner el = new ELearner("el001");
		ArrayList<EConcept> c = emi.getInterestConcepts(el);
		assertTrue(c.size()==4);
	}
	public void testContainELearner(){
		assertTrue(emi.containELearner("el001"));
	}
	public void testContainEConcept(){
		assertTrue(emi.containEConcept("root-se"));
	}
	public void testContainEResource(){
		assertTrue(emi.containEResource("rid00001"));
	}
	public void setUp(){
		emi  = new ELearnerModelImpl();
	}

	private ELearnerModelImpl emi;
}
