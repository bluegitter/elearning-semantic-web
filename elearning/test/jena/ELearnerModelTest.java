package jena;

import java.io.File;
import java.io.IOException;

import ontology.people.ELearner;
import ontology.resources.EResource;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;

import db.OwlOperator;

public class ELearnerModelTest {
	
	public static void main(String [] args) throws IOException{
		testHas();
	}
	public static void testHas(){
		InfModel model = OwlFactory.getGenericRuleReasonerModel();
		ELearnerModelImpl mo = new ELearnerModelImpl(model);
		ELearner el = new ELearner();
		mo.hasELearner(el);
	}
	public static void testAddElearner()throws IOException{
		File file = new File("D:\\EclipseWorkspace\\elearning\\src\\db\\ms.owl");
		InfModel model = OwlFactory.getGenericRuleReasonerModel();
		ELearnerModelImpl mo = new ELearnerModelImpl(model);
		ELearner el = new ELearner();
		el.setId("eltest");
		el.setName("testName");
		mo.addELearner( el);
		OwlOperator.updateOwlFile(model,file);
	}
	public static void testAddResrouce() throws IOException{
		File file = new File("D:\\EclipseWorkspace\\elearning\\src\\db\\ms.owl");
		InfModel model = OwlFactory.getGenericRuleReasonerModel();
		ELearnerModelImpl mo = new ELearnerModelImpl(model);
		EResource res = new EResource();
		res.setId("testResourse");
		res.setName("testResourceName");
		res.setDifficulty("35");
		mo.addResource(res);
		mo.writeToFile(file);
	}
}
