package jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import db.OwlOperator;

public class ELearnerModelImpl implements ELearnerModel{
	private InfModel model;
	public ELearnerModelImpl(){
		// Open the bloggers RDF graph from the filesystem
		InputStream in;
		try {
			in = new FileInputStream(new File(Constant.OWLFile));
			// Create an empty in-memory model and populate it from the graph
			Model model = ModelFactory.createMemModelMaker().createModel(null);
			model.read(in,Constant.NS); // null base URI, since model URIs are absolute
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ELearnerModelImpl(InfModel model){
		this.model = model;
	}

	public boolean addELearner(ELearner elearner){
		Resource el = model.createResource(Constant.NS+elearner.getId(),model.getResource(Constant.NS+"E_Learner"));
		el.addProperty(model.getProperty(Constant.NS+"name"), elearner.getName());
		return true;
	}
	public boolean addResource(EResource resource){
		Resource re = model.createResource(Constant.NS+resource.getId(),model.getResource(Constant.NS+"E_Resource"));
		re.addProperty(model.getProperty(Constant.NS+"name"), resource.getName());
		re.addProperty(model.getProperty(Constant.NS+"difficulty"), resource.getDifficulty());
		return true;
	}
	
	@Override
	public boolean addConcept(EConcept concept) {
		// TODO Auto-generated method stub
		Resource con = model.createResource(Constant.NS+concept.getId(),model.getResource(Constant.NS+"E_Concept"));
		con.addProperty(model.getProperty(Constant.NS+"name"), concept.getName());
		return true;
	}
	
	@Override
	public boolean addInterest(EInterest interest) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addPerfomance(EPerformance performance) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean writeToFile(File file) {
		try {
			OwlOperator.updateOwlFile(model, file);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean hasELearner(ELearner elearner) {
		Resource r = model.getResource(Constant.NS+"FUCK");
		boolean b = r.isURIResource();
		boolean b2 = r.isResource();
		boolean b3 = r.isLiteral();
		System.out.println(b+"\t"+b2+"\t"+b3);
		return true;
	}
	@Override
	public ArrayList<EConcept> getAllConcepts() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getConcepts(ELearner elearner) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getRecommandConcepts(ELearner elearner) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getRecommandResources(ELearner elearner) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EResource> getResourcesByKey(ELearner elearner,
			String keyword) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
