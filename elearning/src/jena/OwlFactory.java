package jena;

import java.io.InputStream;

import util.Constant;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class OwlFactory {
	public static Model getOWLModel(){
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(Constant.OWLFile);
		if(in ==null){
			throw new IllegalArgumentException("File: " + Constant.OWLFile + " not found");
		}
		model.read(in,Constant.NS);
		return model;
	}
	public static OntModel getOntOWLModel(){
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(Constant.OWLFile);
		if(in ==null){
			throw new IllegalArgumentException("File: " + Constant.OWLFile + " not found");
		}
		model.read(in,Constant.NS);
		return model;
	}
}

