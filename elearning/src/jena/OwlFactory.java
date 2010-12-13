package jena;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import util.Constant;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class OwlFactory {
	public static Model getDefaultOWLModel(){
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
	/**************************************************************
	 * Get a inf model with some kind of reasoner
	 * @return kind of reason
	 *************************************************************/
	public static InfModel getGenericRuleReasonerModel(){
		List<Rule> rules = Rule.rulesFromURL(Constant.RulesFile);
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,OwlFactory.getDefaultOWLModel());
		Resource configuration = model.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		return infModel;
	}
	public static InfModel getGenericRuleReasonerModel(String fileURL){
		List<Rule> rules = Rule.rulesFromURL(fileURL);
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,OwlFactory.getDefaultOWLModel());
		Resource configuration = model.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		return infModel;
	}
	public static InfModel getPelletReasonerModel(){
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,OwlFactory.getDefaultOWLModel());
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		return infModel;
	}
}

