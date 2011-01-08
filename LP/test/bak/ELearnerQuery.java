package bak;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jena.OwlFactory;

import ontology.EConcept;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import util.Constant;
/******************************************************************
 * The Query is based on the SPARQL Query
 * @author william
 *
 */
public class ELearnerQuery {
	
	/*************************************************************
	 * Get All the concepts Names
	 * @return
	 * @throws IOException
	 ********************************************************/
	public static ArrayList<String> getAllConceptName()throws IOException{
		// Open the bloggers RDF graph from the filesystem
		InputStream in = new FileInputStream(new File(Constant.OWLFile));
		// Create an empty in-memory model and populate it from the graph
		Model model = ModelFactory.createMemModelMaker().createModel(null);
		model.read(in,null); // null base URI, since model URIs are absolute
		in.close();
		// Create a new query
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      }";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		ArrayList<String> concepts = new ArrayList<String>();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			//trim() eliminates the blank space behind the uri, which make the getResource() method working.
			String uri = qs.get("?concept").toString().trim();
			String name = model.getResource(uri).getLocalName();
			//System.out.println(name);
			concepts.add(name);
		}
		qe.close();
		return concepts;
	}
	/**********************************************************************
	 * Get all concepts who are the part of the given concept.
	 * @param conceptName
	 * @throws IOException
	 *************************************************************************/
	public static void getPartOfConcept(String conceptName) throws IOException{
		InputStream in = new FileInputStream(new File(Constant.OWLFile));
		Model model = ModelFactory.createMemModelMaker().createModel(null);
		model.read(in,null); // null base URI, since model URIs are absolute
		in.close();
		// Create a new query
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:is_part_of base:Database ."+
			"      }";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		
		qe.close();
	}

	/*******************************************************************
	 * Pay attention to the context of the uid, which should be in the type
	 * of "el001"@en
	 * @param uid
	 * @return
	 */
	public static ArrayList<String> getInterestConcepts(String uid){
		ArrayList<String> concepts = new ArrayList<String>();
		InfModel model = OwlFactory.getInfoModel(OwlFactory.getGenericRuleReasoner(), OwlFactory.getDefaultOWLModel());
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?concept ?elearner " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:is_concept_of ?interest . "+
			"      ?interest rdf:type base:E_Interest . "+
			"      ?interest base:inverse_of_has_interest ?elearner . "+
			"      ?elearner base:id  \""+uid+"\"@en . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		
		qe.close();
		return concepts;
	}
	
	public static int getPerformanceCount(String learner) throws IOException{
		InputStream in = new FileInputStream(new File(Constant.OWLFile));
		Model model = ModelFactory.createMemModelMaker().createModel(null);
		model.read(in,null); // null base URI, since model URIs are absolute
		in.close();

		// Create a new query
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
			"SELECT  ?concept ?performance ?value " +
			"WHERE {" +			
			"      ?performance base:inverse_of_has_performance base:"+learner+" . "+
			"      ?concept base:is_concept_of ?performance . "+
			"      ?performance base:value ?value . "+
		//	"      FILTER xsd:float(?value) > 0.5^^xsd:float . "+	
			"      FILTER (?value < 0.5) "+		
			"      }"+
			"      ORDER BY ASC(?VALUE) "+
			"      LIMIT 1 ";
        
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		
		ArrayList<String> performances = new ArrayList<String>();
        while(results.hasNext()){
			QuerySolution qs = results.next();
			//trim() eliminates the blank space behind the uri, which make the getResource() method working.
			//String uri = qs.get("?performance").toString().trim();
			System.out.println(qs.toString());
			
			//String name = model.getResource(uri).getLocalName();
			//concepts.add(name);
		}
        int count = results.getRowNumber();
		qe.close();
		System.out.println(count);
		return count;
	}
	
	
	public static void test() throws IOException{
		// Open the bloggers RDF graph from the filesystem
		InputStream in = new FileInputStream(new File(Constant.OWLFile));

		// Create an empty in-memory model and populate it from the graph
		InfModel model = OwlFactory.getInfoModel(OwlFactory.getGenericRuleReasoner(), OwlFactory.getDefaultOWLModel());
		// Create a new query
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:is_part_of base:Software_Engineer . "+
			"      }";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		 
		// Output query results	
		ResultSetFormatter.out(System.out, results, query);
		
		// Important - free up resources used running the query
		qe.close();
	}
	
}

