package jena;

import java.io.InputStream;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class JenaRdfsRulesReasoner {
	static String NS="http://www.owl-ontologies.com/e-learning.owl#";
	static String OWLFile ="D:\\EclipseWorkspace\\elearning\\protege\\elearning.owl";
	static String RulesFile="D:\\EclipseWorkspace\\elearning\\src\\jena\\elearning.rules";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = getOWLModel();
		List<Rule> rules = Rule.rulesFromURL(RulesFile);
		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,model);
		Resource configuration = om.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		InfModel inf = ModelFactory.createInfModel(reasoner, om);
		
		Resource el001 = model.getResource(NS+"el001");
		Resource mysql = model.getResource(NS+"MySQL");
		inferenceRelation(el001,mysql,inf);
	}
	public static void inferenceRelation (Resource a, Resource b,InfModel inf){
		StmtIterator stmtIter = inf.listStatements(a, null, b);
		if (!stmtIter.hasNext()) {
			System.out.println("there is no relation between " + a.getLocalName() + " and " + b.getLocalName());
			System.out.println("\n-------------------\n");
		}
		while (stmtIter.hasNext()) {
			Statement s = stmtIter.nextStatement();
			System.out.println("Relation between " + a.getLocalName() + " and " + b.getLocalName() + " is :");
			System.out.println(a.getLocalName() + " " + s.getPredicate().getLocalName() + " " + b.getLocalName());
			System.out.println(s);
			System.out.println("\n-------------------\n");
		}
	}
	public static Model getOWLModel(){
		Model model = ModelFactory.createDefaultModel();
		InputStream in = FileManager.get().open(OWLFile);
		if(in ==null){
			throw new IllegalArgumentException("File: " + OWLFile + " not found");
		}
		model.read(in,NS);
		return model;
	}

}
