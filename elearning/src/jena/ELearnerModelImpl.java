package jena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
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
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import db.OwlOperation;

public class ELearnerModelImpl implements ELearnerModel{
	private InfModel model;
	public ELearnerModelImpl(){
		model = OwlFactory.getGenericRuleReasonerModel();
	}
	public ELearnerModelImpl(InfModel model){
		this.model = model;
	}
	public ELearnerModelImpl(String fileURL,String ruleURL){
		model = OwlFactory.getGenericRuleReasonerModel(fileURL,ruleURL);
	}
	
	public InfModel getModel() {
		return model;
	}
	public void setModel(InfModel model) {
		this.model = model;
	}
	@Override
	public ArrayList<EResource> getResourcesByKey(ELearner elearner,
			String keyword) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean writeToFile(File file) {
		try {
			OwlOperation.updateOwlFile(model, file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean addELearner(ELearner elearner){
		Resource el = model.createResource(Constant.NS+elearner.getId(),model.getResource(Constant.NS+"E_Learner"));
		el.addProperty(model.getProperty(Constant.NS+"name"), elearner.getName());
		return true;
	}
	public boolean addResource(EResource resource){
		Resource re = model.createResource(Constant.NS+resource.getRid(),model.getResource(Constant.NS+"E_Resource"));
		re.addProperty(model.getProperty(Constant.NS+"name"), resource.getName());
		re.addProperty(model.getProperty(Constant.NS+"difficulty"), resource.getDifficulty());
		return true;
	}
	@Override
	public boolean addPortfolio(EPortfolio portfolio) {
		ELearner el = portfolio.getElearner();
		EResource res = portfolio.getResource();
		if(!containELearner(el.getId())){
			return false;
		}
		if(!containEResource(res.getRid())){
			return false;
		}
		Resource in = model.createResource(Constant.NS+portfolio.getId(),model.getResource(Constant.NS+"E_Interest"));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_has_portfolio"),model.getResource(Constant.NS+el.getId()));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_is_resource_of_P"),model.getResource(Constant.NS+res.getRid()));
		in.addProperty(model.getProperty(Constant.NS+"value"),"-1");
		return true;
	}
	@Override
	public boolean addConcept(EConcept concept) {
		Resource con = model.createResource(Constant.NS+concept.getCid(),model.getResource(Constant.NS+"E_Concept"));
		con.addProperty(model.getProperty(Constant.NS+"name"), concept.getName());
		return true;
	}
	
	@Override
	public boolean addInterest(EInterest interest) {
		ELearner el = interest.getEl();
		EConcept con = interest.getCon();
		if(!containELearner(el.getId())){
			return false;
		}
		if(!containEConcept(con.getCid())){
			return false;
		}
		Resource in = model.createResource(Constant.NS+interest.getId(),model.getResource(Constant.NS+"E_Interest"));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_has_interest"),model.getResource(Constant.NS+el.getId()));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_is_concept_of_I"),model.getResource(Constant.NS+con.getCid()));
		in.addProperty(model.getProperty(Constant.NS+"value"),"-1");
		return true;
	}
	@Override
	public boolean addPerfomance(EPerformance performance) {
		ELearner el = performance.getElearner();
		EConcept con = performance.getConcept();
		if(!containELearner(el.getId())){
			return false;
		}
		if(!containEConcept(con.getCid())){
			return false;
		}
		Resource in = model.createResource(Constant.NS+performance.getId(),model.getResource(Constant.NS+"E_Performance"));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_has_performance"),model.getResource(Constant.NS+el.getId()));
		in.addProperty(model.getProperty(Constant.NS+"inverse_of_is_concept_of_P"),model.getResource(Constant.NS+con.getCid()));
		in.addProperty(model.getProperty(Constant.NS+"value"),String.valueOf(performance.getValue()));
		return true;
	}
	
	@Override
	public boolean containELearner(String eid) {
		Resource r = model.getResource(Constant.NS+"FUCK");
		boolean b = r.isURIResource();
		boolean b2 = r.isResource();
		boolean b3 = r.isLiteral();
		System.out.println(b+"\t"+b2+"\t"+b3);
		return true;
	}
	
	@Override
	public ArrayList<EConcept> getAllConcepts() {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	@Override
	public ArrayList<EConcept> getMemberConcept(EConcept concept) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      ?concept base:is_part_of ?fatherConcept . "+
			"      ?fatherConcept rdf:type base:E_Concept . "+
			"      ?fatherConcept base:id " +StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	@Override
	public ArrayList<EConcept> getInterestConcepts(ELearner elearner) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?concept base:is_concept_of_I ?interest . "+
			"      ?elearner base:has_interest ?interest . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			concepts.add(con);
			//System.out.println(con);
		}
		qe.close();
		return concepts;
	}
	
	@Override
	public ArrayList<EConcept> getRecommendConcepts(ELearner elearner,int i) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String rule = "is_recommend_of_c_"+i;
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?concept base:"+rule+" ?elearner . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			concepts.add(con);
			//System.out.println(con);
		}
		qe.close();
		return concepts;
	}
	@Override
	public ArrayList<ELearner> getRecommendELearner(ELearner elearner, int rule) {
		ArrayList<ELearner> elearners = new ArrayList<ELearner>();
		String i = "is_recommend_of_L_"+rule+"";
		System.out.println(i);
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?el ?id ?name ?grade ?email ?address " +
			"WHERE {" +
			"      ?el rdf:type base:E_Learner . " +
			"      ?el base:id ?id . " +
			"      ?el base:name ?name . "+
		 	"      ?el base:grade ?grade . "+
			"      ?el base:email ?email . "+
			"      ?el base:address ?address . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?el base:"+i+" ?elearner . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			ELearner el = new ELearner();
			String id = qs.get("?id").toString().trim();
			String name = qs.get("?name").toString().trim();
			String grade = qs.get("?grade").toString().trim();
			String email = qs.get("?email").toString().trim();
			String address = qs.get("?address").toString().trim();
			el.setId(StringExchanger.getCommonString(id));
			el.setName(StringExchanger.getCommonString(name));
			el.setGrade(StringExchanger.getCommonString(grade));
			el.setEmail(StringExchanger.getCommonString(email));
			el.setAddress(StringExchanger.getCommonString(address));
			elearners.add(el);
		}
		qe.close();
		return elearners;
	}
	@Override
	public ArrayList<EResource> getRecommendResources(ELearner elearner,
			int rule) {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String i = "is_recommend_of_r_"+rule;
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?id ?name ?difficulty ?fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:id ?id . " +
			"      ?resource base:name ?name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?resource base:"+i+" ?elearner . "+
			"      ?resource base:difficulty ?difficulty . "+
			"      ?resource base:fileLocation ?fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			String difficulty = qs.get("?difficulty").toString().trim();
			String fileLocation = qs.get("?fileLocation").toString().trim();
			EResource res = new EResource();
			res.setRid(StringExchanger.getCommonString(id));
			res.setName(StringExchanger.getCommonString(name));
			res.setDifficulty(StringExchanger.getCommonString(difficulty));
			res.setFileLocation(StringExchanger.getCommonString(fileLocation));
			System.out.println(res);resources.add(res);
		}
		qe.close();
		return resources;
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

	//get a basic ELearner by the given elearner id
	public ELearner getLearner(String eid) {
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
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?resource ?name ?difficulty ?fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
		    "      ?resource base:id "+StringExchanger.getSparqlString(rid)+" . "+
			"      ?resource base:name ?name . "+
		 	"      ?resource base:difficulty ?difficulty . "+
			"      ?resource base:fileLocation ?fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		EResource res = new EResource(rid);
		if(results.hasNext()){
			QuerySolution qs = results.next();
			String name = qs.get("?name").toString().trim();
			String difficulty = qs.get("?difficulty").toString().trim();
			String fileLocation = qs.get("?fileLocation").toString().trim();
			res.setName(StringExchanger.getCommonString(name));
			res.setDifficulty(StringExchanger.getCommonString(difficulty));
			res.setFileLocation(StringExchanger.getCommonString(fileLocation));
			System.out.println(res);
		}
		qe.close();
		return res;
	}
	//return the root concept which is already set.
	public EConcept getRootConcept(){
		EConcept rootConcept = new EConcept();
		rootConcept.setCid("Software_Engineer");
		rootConcept.setName("software engineering");
		return rootConcept;
	}
	@Override
	public boolean containEConcept(String cid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			if(StringExchanger.getCommonString(id).equals(cid)){
				return true;
			}
		}
		qe.close();
		return false;
	}
	@Override
	public boolean containEResource(String rid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?id " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:id ?id . " +
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			if(StringExchanger.getCommonString(id).equals(rid)){
				return true;
			}
		}
		qe.close();
		return false;
	}
	@Override
	public ArrayList<EPerformance> getPerfomanceByConcepts(ELearner elearner) {
		ArrayList<EPerformance> ps = new ArrayList<EPerformance>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?perform ?id ?name ?performance ?pid ?pvalue " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?performance base:inverse_of_is_concept_of_P ?concept . "+
			"      ?elearner base:has_performance ?performance . "+
			"      ?performance base:id ?pid . "+
			"      ?performance base:value ?pvalue . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			
			EPerformance per = new EPerformance();
			per.setElearner(elearner);
			per.setConcept(con);
			per.setId(StringExchanger.getCommonString(qs.get("?pid").toString()));
			String value = StringExchanger.getCommonString(qs.get("?pvalue").toString());
			per.setValue(Float.valueOf(value));
			ps.add(per);
		}
		qe.close();
		return ps;
	}
	@Override
	public ArrayList<EConcept> getSonConcepts(EConcept concept) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?id ?name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id ?id . " +
			"      ?concept base:name ?name . "+
			"      ?concept base:is_son_of ?fatherConcept . "+
			"      ?fatherConcept rdf:type base:E_Concept . "+
			"      ?fatherConcept base:id " +StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			EConcept con = new EConcept();
			con.setCid(StringExchanger.getCommonString(id));
			con.setName(StringExchanger.getCommonString(name));
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	public static void main(String [] args)throws Exception{
		//E_Performance_1
		ELearnerModelImpl emi = new ELearnerModelImpl();
		EConcept concept = new EConcept("testPreCnp");
		ELearner elearner = new ELearner("el002");
		EPerformance performance = new EPerformance();
		performance.setConcept(concept);
		performance.setElearner(elearner);
		performance.setId("jklfdsj");
		performance.setValue(2);
		emi.addPerfomance(performance);
		EPerformance perf = emi.getPerformance(elearner, concept);
		System.out.println(perf.getValue());
		
		
		/*
		System.out.println(perf.getValue());
		
		EPerformance up = new EPerformance();
		up.setId(perf.getId());
		up.setConcept(concept);
		up.setElearner(elearner);
		up.setValue(5);
		emi.updatePerformance(up);
		EPerformance newP = emi.getPerformance(elearner, concept);
		System.out.println(newP.getValue());
*/
	}
	@Override
	public ArrayList<EResource> getPortfolioResources(ELearner elearner) {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?res ?id ?name ?difficulty ?fileLocation " +
			"WHERE {" +
			"      ?res rdf:type base:E_Resource . " +
			"      ?res base:id ?id . " +
			"      ?res base:name ?name . "+
		 	"      ?res base:difficulty ?difficulty . "+
			"      ?res base:fileLocation ?fileLocation . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?portfolio base:inverse_of_is_resource_of_P ?res . "+
			"      ?elearner base:has_portfolio ?portfolio . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			String difficulty = qs.get("?difficulty").toString().trim();
			String fileLocation = qs.get("?fileLocation").toString().trim();
			
			EResource res = new EResource();
			res.setRid(StringExchanger.getCommonString(id));
			res.setName(StringExchanger.getCommonString(name));
			res.setDifficulty(StringExchanger.getCommonString(difficulty));
			res.setFileLocation(StringExchanger.getCommonString(fileLocation));
			resources.add(res);
	//		System.out.println(res);
		}
		qe.close();
		return resources;
	}
	
	@Override
	public ArrayList<EResource> getConceptResources(EConcept concept) {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?id ?name ?difficulty ?fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:id ?id . " +
			"      ?resource base:name ?name . "+
			"      ?concept base:id " +StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      ?resource base:is_resource_of_C ?concept . "+
			"      ?resource base:difficulty ?difficulty . "+
			"      ?resource base:fileLocation ?fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?id").toString();
			String name = qs.get("?name").toString();
			String difficulty = qs.get("?difficulty").toString().trim();
			String fileLocation = qs.get("?fileLocation").toString().trim();
			EResource res = new EResource();
			res.setRid(StringExchanger.getCommonString(id));
			res.setName(StringExchanger.getCommonString(name));
			res.setDifficulty(StringExchanger.getCommonString(difficulty));
			res.setFileLocation(StringExchanger.getCommonString(fileLocation));
			System.out.println(res);resources.add(res);
		}
		qe.close();
		return resources;
	}
	@Override
	public ArrayList<EResource> getAllResources() {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?id ?name ?difficulty ?fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
		    "      ?resource base:id ?id . "+
			"      ?resource base:name ?name . "+
		 	"      ?resource base:difficulty ?difficulty . "+
			"      ?resource base:fileLocation ?fileLocation . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EResource res = new EResource();
			String id =qs.get("?id").toString().trim();
			String name = qs.get("?name").toString().trim();
			String difficulty = qs.get("?difficulty").toString().trim();
			String fileLocation = qs.get("?fileLocation").toString().trim();
			res.setRid(StringExchanger.getCommonString(id));
			res.setName(StringExchanger.getCommonString(name));
			res.setDifficulty(StringExchanger.getCommonString(difficulty));
			res.setFileLocation(StringExchanger.getCommonString(fileLocation));
			System.out.println(res);
			resources.add(res);
		}
		qe.close();
		return resources;
	}
	@Override
	public EPerformance getPerformance(ELearner elearner, EConcept concept) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?perform ?id ?value " +
			"WHERE {" +
			"      ?perform rdf:type base:E_Performance . " +
			"      ?perform base:value ?value . "+
		 	"      ?perform base:inverse_of_has_performance ?elearner . "+
			"      ?perform base:inverse_of_is_concept_of_P ?concept . "+
			"      ?concept base:id "+StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      ?elearner base:id "+StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		//ResultSetFormatter.out(System.out, results, query);
		
		EPerformance performance = null;
		while(results.hasNext()){
			performance = new EPerformance();
			QuerySolution qs = results.next();
			String uri =qs.get("?perform").toString().trim();
			String id = model.getResource(uri).getLocalName();
			String value = qs.get("?value").toString().trim();
			performance.setId(id);
			float v = Float.parseFloat(value);
			performance.setValue(v);
		}
		qe.close();
		return performance;
	}
	@Override
	public boolean updatePerformance(EPerformance performance) {
		// TODO Auto-generated method stub
		Resource r = model.getResource(Constant.NS+performance.getId());
		r.removeAll(model.getProperty(Constant.NS+"value"));
		r.addProperty(model.getProperty(Constant.NS+"value"), String.valueOf(performance.getValue()));
		return true;
	}
}
