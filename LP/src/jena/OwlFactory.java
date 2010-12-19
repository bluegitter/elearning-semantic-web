package jena;

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
	public static Model getDefaultOWLModel(String fileURL){
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(fileURL);
		if(in ==null){
			throw new IllegalArgumentException("File: " + Constant.OWLFile + " not found");
		}
		model.read(in,Constant.NS);
		return model;
	}
	public static OntModel getOntModel(){
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(Constant.OWLFile);
		if(in ==null){
			throw new IllegalArgumentException("File: " + Constant.OWLFile + " not found");
		}
		model.read(in,Constant.NS);
		Resource configuration = model.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		return model;
	}
	public static OntModel getOntModel(String fileURL){
		OntModel model = ModelFactory.createOntologyModel();
		InputStream in = FileManager.get().open(fileURL);
		if(in ==null){
			throw new IllegalArgumentException("File: " + Constant.OWLFile + " not found");
		}
		model.read(in,Constant.NS);
		Resource configuration = model.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		return model;
	}
	/**************************************************************
	 * Get a inf model with some kind of reasoner
	 * @return kind of reason
	 *************************************************************/
	public static Reasoner getGenericRuleReasoner(){
		List<Rule> rules = Rule.rulesFromURL(Constant.RulesFile);
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		return reasoner;
	}
	public static Reasoner getPelletReasonerModel(){
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		return reasoner;
	}
	public static InfModel getInfoModel(Reasoner reasoner,Model model){
		OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,model);
		InfModel infModel = ModelFactory.createInfModel(reasoner, ontModel);
		return infModel;
	}
	
	
}

