package jena;

import java.io.InputStream;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.HasValueRestriction;
import com.hp.hpl.jena.ontology.IntersectionClass;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;

public class JenaReadModelFromOWLFile {
	static final String inputFileName  = "D:\\EclipseWorkspace\\elearning\\protege\\elearning.owl";
	public static String NS = "http://www.owl-ontologies.com/e-learning.owl#";
	
    public static void main (String args[]) {
        // create an empty model
    	OntModel model = ModelFactory.createOntologyModel();
        

        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }
        
        // read the RDF/XML file
        model.read(in, "UTF-8");
        
        OntClass recommendConcept = model.createClass(NS + "RecommandConcept");
        
        HasValueRestriction hvr1 = model.createHasValueRestriction(null, model.getProperty(NS+"is_concept_of"), 
        		model.getIndividual(NS+"E_Interest_1"));
        RDFNode[] rda = {hvr1, model.getOntClass(NS+ "E_Concept")};
        IntersectionClass ic = model.createIntersectionClass(null, model.createList(rda));
        
        recommendConcept.setEquivalentClass(ic);
        // Get the Pellet reasoner;
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		// Create the inference model with Pellet reasoner; 
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		
		Resource infrc = infModel.getResource(NS + "RecommandConcept");

        // write it to standard out
        model.write(System.out);            
    }
}
