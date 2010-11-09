package family;

import java.io.InputStream;
import java.util.List;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.*;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class FamilyReasoner {
	static String NS ="http://www.semanticweb.org/ontologies/2010/0/family.owl#";
	static String OwlFileLocation ="D:\\EclipseWorkspace\\jenatest\\src\\family\\family.owl";
	static String RulesFileLocation ="D:\\EclipseWorkspace\\jenatest\\src\\family\\family.rules";
	public void InferenceRelation(Resource a, Resource b) {
		Model model = ModelFactory.createDefaultModel();
   
		InputStream in = FileManager.get().open( OwlFileLocation );
		if (in == null) {
			throw new IllegalArgumentException( "File: " + OwlFileLocation + " not found");
		}
		// read the RDF/XML file
		model.read(in, NS);
  
		List rules = Rule.rulesFromURL(RulesFileLocation);

		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.setOWLTranslation(true);
		reasoner.setDerivationLogging(true);
		reasoner.setTransitiveClosureCaching(true);
		OntModel om = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,model);
		Resource configuration = om.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");

		InfModel inf = ModelFactory.createInfModel(reasoner, om);
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

	public static void main(String[] args) {
		FamilyReasoner f = new FamilyReasoner();
		Model m = FileManager.get().loadModel("D:\\EclipseWorkspace\\jenatest\\src\\family\\family.owl");

		Resource Jim = m.getResource(NS + "Jim");
		Resource John = m.getResource(NS + "John");
		Resource Lucy = m.getResource(NS + "Lucy");
		Resource Kate = m.getResource(NS + "Kate");
		Resource Sam = m.getResource(NS + "Sam");
		Resource James = m.createResource(NS + "James");
		f.InferenceRelation(Jim, John);
		f.InferenceRelation(John, Jim);
		f.InferenceRelation(John, Sam);
		f.InferenceRelation(Lucy, John);
		f.InferenceRelation(Kate, Sam);
		f.InferenceRelation(Sam, Kate);
		f.InferenceRelation(James, John);
	}
}

