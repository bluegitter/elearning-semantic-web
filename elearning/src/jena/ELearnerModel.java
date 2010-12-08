package jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import db.OwlOperator;

public class ELearnerModel {
	private Model model;
	public ELearnerModel(){
		// Open the bloggers RDF graph from the filesystem
		InputStream in;
		try {
			in = new FileInputStream(new File(Constant.OWLFile));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createMemModelMaker().createModel(null);
			model.read(in,null); // null base URI, since model URIs are absolute
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ELearnerModel(Model model){
		this.model = model;
	}

	public void addELearner(ELearner elearner){
		Resource el = model.createResource(Constant.NS+elearner.getId(),model.getResource(Constant.NS+"E_Learner"));
		el.addProperty(model.getProperty(Constant.NS+"name"), elearner.getName());
	}
	public void addResource(EResource resource){
		Resource re = model.createResource(Constant.NS+resource.getId(),model.getResource(Constant.NS+"E_Resource"));
		re.addProperty(model.getProperty(Constant.NS+"name"), resource.getName());
		re.addProperty(model.getProperty(Constant.NS+"difficulty"), resource.getDifficulty());
	}
	
	
	public static void main(String [] args) throws IOException{
		File file = new File("D:\\EclipseWorkspace\\elearning\\src\\db\\ms.owl");
		Model model = OwlFactory.getGenericRuleReasonerModel();
		ELearnerModel mo = new ELearnerModel(model);
		ELearner el = new ELearner();
		el.setId("eltest");
		el.setName("testName");
		mo.addELearner( el);
		OwlOperator.updateOwlFile(model,file);
	}


	
}
