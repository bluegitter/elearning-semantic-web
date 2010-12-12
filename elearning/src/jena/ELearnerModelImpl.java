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
import util.StringExchanger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import db.OwlOperation;

public class ELearnerModelImpl implements ELearnerModel{
	private InfModel model;
	public ELearnerModelImpl(){
		model = OwlFactory.getGenericRuleReasonerModel();
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
		Resource con = model.createResource(Constant.NS+concept.getCid(),model.getResource(Constant.NS+"E_Concept"));
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
			OwlOperation.updateOwlFile(model, file);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean containELearner(ELearner elearner) {
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
	
	//get a basic Concept by the concept id
	public EConcept getConcept(String cid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?concept ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id "+StringExchanger.getSparqlString(cid)+" . "+
			"      ?concept base:name ?name . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		// Output query results	
		EConcept con = new EConcept(cid);
		if(results.hasNext()){
			QuerySolution qs = results.next();
			String name = qs.get("?name").toString().trim();
			con.setName(StringExchanger.getCommonString(name));
		}
		qe.close();
		return con;
	}

	@Override
	public ELearner getLearner(String eid) {
		// TODO Auto-generated method stub
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?elearner ?name ?grade ?email ?address " +
			"WHERE {" +
			"      ?elearner rdf:type base:E_Learner . " +
		    "      ?elearner base:id "+StringExchanger.getSparqlString(eid)+" . "+
			"      ?elearner base:name ?name . "+
		 	"      ?elearner base:grade ?grade . "+
			"      ?elearner base:email ?email . "+
			"      ?elearner base:address ?address . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		// Output query results	
		ELearner el = new ELearner(eid);
		if(results.hasNext()){
			QuerySolution qs = results.next();
			String name = qs.get("?name").toString().trim();
			String grade = qs.get("?grade").toString().trim();
			String email = qs.get("?email").toString().trim();
			String address = qs.get("?address").toString().trim();
			el.setName(StringExchanger.getCommonString(name));
			el.setGrade(StringExchanger.getCommonString(grade));
			el.setEmail(StringExchanger.getCommonString(email));
			el.setAddress(StringExchanger.getCommonString(address));
		}
		qe.close();
		return el;
	}
	@Override
	public EResource getResource(String rid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getPartMember(EConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
