package jena;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

import util.Constant;

public class MultipleFileReaderTest {
/**
 * 
 */
	public static String dataFile = "D:\\EclipseWorkspace\\elearning\\test\\jena\\data.owl";
	public static String ontologyFile ="D:\\EclipseWorkspace\\elearning\\test\\jena\\ontology.owl";
	public static void main(String [] args) throws IOException{
		InputStream inData = new FileInputStream(new File(dataFile));
		InputStream inOnto = new FileInputStream(new File(ontologyFile));
		
		InfModel model = OwlFactory.getGenericRuleReasonerModel();
		model.read(inData,"http://localhost:8080/myelearner/webapp/data.owl"); // null base URI, since model URIs are absolute
		
		// Create a new query
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://localhost:8080/myelearner/webapp/ontology.owl#> " +
			"SELECT ?org ?uri " +
			"WHERE {" +
			"      ?org rdf:type base:E_Organizations . " +
		//	"      ?org rdf:ID ?uri . "+
			"      }";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		ResultSetFormatter.out(System.out, results, query);
		
		// Important - free up resources used running the query
		qe.close();
		
		
		inData.close();
		inOnto.close();
	}
}
