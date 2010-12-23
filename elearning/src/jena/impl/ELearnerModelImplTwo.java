package jena.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jena.OwlFactory;
import jena.interfaces.ELearnerModelOperationInterface;
import jena.interfaces.ELearnerModelQueryInterface;
import jena.interfaces.ELearnerRuleModel;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import util.QuerySolutionParserTwo;
import util.StringExchanger;
import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Resource;

import db.OwlOperation;

public class ELearnerModelImplTwo {
	private InfModel model;
	public ELearnerModelImplTwo(){
		model = OwlFactory.getInfoModel(OwlFactory.getGenericRuleReasoner(), OwlFactory.getOntModel());
	}
	public ELearnerModelImplTwo(OntModel model){
		this.model = model;
	}
	public ELearnerModelImplTwo(String fileURL){
		model = OwlFactory.getOntModel(fileURL);
	}
	
	public InfModel getModel() {
		return model;
	}
	public void setModel(OntModel model) {
		this.model = model;
	}
	//return the root concept which is already set.
	public EConcept getRootConcept(){
		EConcept rootConcept = new EConcept();
		rootConcept.setCid("Software_Engineer");
		rootConcept.setName("software engineering");
		return rootConcept;
	}
	
	/*******************************************************************************************************
	 * Add new Data Operations 
	 *******************************************************************************************************/
	public boolean addELearner(ELearner elearner){
		Resource el = model.createResource(Constant.NS+elearner.getId(),model.getResource(Constant.NS+"E_Learner"));
		model.add(el,model.getProperty(Constant.NS+"id"), elearner.getId());
		model.add(el,model.getProperty(Constant.NS+"name"), elearner.getName(),new XSDDatatype("string"));
		model.add(el,model.getProperty(Constant.NS+"grade"), elearner.getGrade(),new XSDDatatype("string"));
		model.add(el,model.getProperty(Constant.NS+"address"), elearner.getAddress(),new XSDDatatype("string"));
		model.add(el,model.getProperty(Constant.NS+"email"), elearner.getEmail(),new XSDDatatype("string"));
		return true;
	}
	public boolean addEResource(EResource resource){
		Resource re = model.createResource(Constant.NS+resource.getRid(),model.getResource(Constant.NS+"E_Resource"));
		model.add(re,model.getProperty(Constant.NS+"id"), resource.getRid());
		model.add(re,model.getProperty(Constant.NS+"name"), resource.getName(),new XSDDatatype("string"));
		model.add(re,model.getProperty(Constant.NS+"fileLocation"), resource.getFileLocation(),new XSDDatatype("string"));
		model.add(re,model.getProperty(Constant.NS+"difficulty"), resource.getDifficulty(),new XSDDatatype("string"));
		return true;
	}
	public boolean addEPortfolio(EPortfolio portfolio) {
		ELearner el = portfolio.getElearner();
		EResource res = portfolio.getEResource();
		if(!containELearner(el.getId())){
			return false;
		}
		if(!containEResource(res.getRid())){
			return false;
		}
		Resource port = model.createResource(Constant.NS+portfolio.getId(),model.getResource(Constant.NS+"E_Portfolio"));
		model.add(port,model.getProperty(Constant.NS+"inverse_of_has_portfolio"),model.getResource(Constant.NS+el.getId()));
		model.add(port,model.getProperty(Constant.NS+"inverse_of_is_resource_of_P"),model.getResource(Constant.NS+res.getRid()));
		model.add(port,model.getProperty(Constant.NS+"value"),(String.valueOf(portfolio.getValue())),new XSDDatatype("string"));
		return true;
	}
	public boolean addEConcept(EConcept concept) {
		Resource con = model.createResource(Constant.NS+concept.getCid(),model.getResource(Constant.NS+"E_Concept"));
		model.add(con, model.getProperty(Constant.NS+"id"), concept.getCid());
		model.add(con, model.getProperty(Constant.NS+"name"), concept.getName(),new XSDDatatype("string"));
		return true;
	}
	public boolean addPropertyIsSonOf(EConcept fatherConcept,EConcept sonConcept){
		Resource son = model.getResource(Constant.NS+sonConcept.getCid());
		Resource father = model.getResource(Constant.NS+fatherConcept.getCid());
		model.add(son, model.getProperty(Constant.NS+"is_son_of"), father);
		return true;
	}
	
	 
	public boolean addEInterest(EInterest interest) {
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
		in.addProperty(model.getProperty(Constant.NS+"value"),String.valueOf(interest.getValue()),new XSDDatatype("string"));
		return true;
	}
	 
	public boolean addEPerfomance(EPerformance performance) {
		ELearner el = performance.getElearner();
		EConcept con = performance.getConcept();
		if(!containELearner(el.getId())){
			return false;
		}
		if(!containEConcept(con.getCid())){
			return false;
		}
		Resource in = model.createResource(Constant.NS+performance.getId(),model.getResource(Constant.NS+"E_Performance"));
		model.add(in,model.getProperty(Constant.NS+"inverse_of_has_performance"),model.getResource(Constant.NS+el.getId()));
		model.add(in,model.getProperty(Constant.NS+"inverse_of_is_concept_of_P"),model.getResource(Constant.NS+con.getCid()));
		model.add(in,model.getProperty(Constant.NS+"value"),(String.valueOf(performance.getValue())),new XSDDatatype("string"));
		return true;
	}
	
	 
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
	
	 
	public boolean containELearner(String eid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?elearner ?el_id " +
			"WHERE {" +
			"      ?elearner rdf:type base:E_Learner . " +
			"      ?elearner base:id ?el_id . " +
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?el_id").toString().trim();
			if(StringExchanger.getCommonString(id).equals(eid)){
				return true;
			}
		}
		qe.close();
		return false;
	}
	
	 
	public boolean containEResource(String rid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?r_id " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:id ?r_id . " +
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			String id = qs.get("?r_id").toString();
			if(StringExchanger.getCommonString(id).equals(rid)){
				return true;
			}
		}
		qe.close();
		return false;
	}
	
	 
	public ArrayList<EConcept> getAllEConcepts() {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?c_name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	 
	public ArrayList<EConcept> getMemberConcept(EConcept concept) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept  ?c_name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
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
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	 
	public ArrayList<EConcept> getInterestConcepts(ELearner elearner) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?c_name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
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
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	
	 
	public ArrayList <EConcept> getRecommendEConcepts(ELearner elearner,int i) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String rule = "is_recommend_of_c_"+i;
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?c_name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?concept base:"+rule+" ?elearner . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	 
	public ArrayList<ELearner> getRecommendELearners(ELearner elearner, int rule) {
		ArrayList<ELearner> elearners = new ArrayList<ELearner>();
		String i = "is_recommend_of_L_"+rule+"";
		//this rule is used to recommend elearner to rec_el
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?elearner ?el_name ?el_grade ?el_email ?el_address " +
			"WHERE {" +
			"      ?elearner rdf:type base:E_Learner . " +
			"      ?elearner base:name ?el_name . "+
		 	"      ?elearner base:grade ?el_grade . "+
			"      ?elearner base:email ?el_email . "+
			"      ?elearner base:address ?el_address . "+
			"      ?rec_el base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?elearner base:"+i+" ?rec_el . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			ELearner el = QuerySolutionParserTwo.getELearner(qs, model);
			elearners.add(el);
		}
		qe.close();
		return elearners;
	}
	 
	public ArrayList<EResource> getRecommendEResources(ELearner elearner,
			int rule) {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String i = "is_recommend_of_r_"+rule;
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?r_name ?r_difficulty ?r_fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:name ?r_name . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?resource base:"+i+" ?elearner . "+
			"      ?resource base:difficulty ?r_difficulty . "+
			"      ?resource base:fileLocation ?r_fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EResource res = QuerySolutionParserTwo.getEResource(qs, model);
			resources.add(res);
		}
		qe.close();
		return resources;
	}
	
	//get a basic Concept by the concept id
	public EConcept getEConcept(String cid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?concept ?c_name ?id " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:id "+StringExchanger.getSparqlString(cid)+" . "+
			"      ?concept base:name ?c_name . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		System.out.println(StringExchanger.getSparqlString(cid)+"---------id");
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
	
		//	ResultSetFormatter.out(System.out, results, query);
		// Output query results	
		EConcept con = null;
		if(results.hasNext()){
			QuerySolution qs = results.next();
			con = QuerySolutionParserTwo.getEConcept(qs, model);
		}
		qe.close();
		return con;
	}

	//get a basic ELearner by the given elearner id
	public ELearner getELearner(String eid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?elearner ?el_name ?el_grade ?el_email ?el_address " +
			"WHERE {" +
			"      ?elearner rdf:type base:E_Learner . " +
		    "      ?elearner base:id "+StringExchanger.getSparqlString(eid)+" . "+
			"      ?elearner base:name ?el_name . "+
		 	"      ?elearner base:grade ?el_grade . "+
			"      ?elearner base:email ?el_email . "+
			"      ?elearner base:address ?el_address . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		// Output query results	
		ELearner el = null;
		if(results.hasNext()){
			QuerySolution qs = results.next();
			el = QuerySolutionParserTwo.getELearner(qs, model);
		}
		qe.close();
		return el;
	}

	 
	public EResource getEResource(String rid) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
			"SELECT ?resource ?r_name ?r_difficulty ?r_fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
		    "      ?resource base:id "+StringExchanger.getSparqlString(rid)+" . "+
			"      ?resource base:name ?r_name . "+
		 	"      ?resource base:difficulty ?r_difficulty . "+
			"      ?resource base:fileLocation ?r_fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		// Output query results	
		EResource res = null;
		if(results.hasNext()){
			QuerySolution qs = results.next();
			res = QuerySolutionParserTwo.getEResource(qs, model);
		}
		qe.close();
		return res;
	}
	
	 
	public ArrayList<EPerformance> getEPerformances(ELearner elearner) {
		ArrayList<EPerformance> ps = new ArrayList<EPerformance>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?c_name ?performance ?performance_value " +
			"WHERE {" +
			"      ?performance rdf:type base:E_Performance . " +
			"      ?performance base:inverse_of_is_concept_of_P ?concept . "+
			"      ?performance base:inverse_of_has_performance ?elearner . "+
			"      ?performance base:value ?performance_value . "+
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
			"      ?elearner base:id "+StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			EPerformance per = new EPerformance();
			String pid = QuerySolutionParserTwo.getIdByURI(qs, model, "?performance");
			String valueString = StringExchanger.getCommonString(qs.get("?performance_value").toString().trim());
			float value = Float.valueOf(valueString);
			per.setId(pid);
			per.setElearner(elearner);
			per.setConcept(con);
			per.setValue(value);
			ps.add(per);
		}
		qe.close();
		return ps;
	}
	 
	public ArrayList<EConcept> getSonConcepts(EConcept concept) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?concept ?c_name " +
			"WHERE {" +
			"      ?concept rdf:type base:E_Concept . " +
			"      ?concept base:name ?c_name . "+
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
			EConcept con = QuerySolutionParserTwo.getEConcept(qs, model);
			concepts.add(con);
		}
		qe.close();
		return concepts;
	}
	public static void main(String [] args)throws Exception{
		ELearnerModelImpl emi = new ELearnerModelImpl();
		emi.getEResource("rid00001");
	}
	 
	public ArrayList<EPortfolio> getEPortfolios(ELearner elearner) {
		ArrayList<EPortfolio> portfolios = new ArrayList<EPortfolio>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?r_name ?r_difficulty ?r_fileLocation ?portfolio ?portfolio_value " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:name ?r_name . "+
		 	"      ?resource base:difficulty ?r_difficulty . "+
			"      ?resource base:fileLocation ?r_fileLocation . "+
			"      ?elearner base:id " +StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      ?portfolio base:inverse_of_is_resource_of_P ?resource . "+
			"      ?elearner base:has_portfolio ?portfolio . "+
			"      ?portfolio base:value ?portfolio_value . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EResource resource = QuerySolutionParserTwo.getEResource(qs, model);
			EPortfolio port = new EPortfolio();
			String pid = QuerySolutionParserTwo.getIdByURI(qs, model, "?portfolio");
			String valueString = StringExchanger.getCommonString(qs.get("?portfolio_value").toString().trim());
			float value = Float.valueOf(valueString);
			
			port.setId(pid);
			port.setEResource(resource);
			port.setElearner(elearner);
			port.setValue(value);
			portfolios.add(port);
		}
		qe.close();
		return portfolios;
	}
	
	 
	public ArrayList<EResource> getEResourcesByEConcept(EConcept concept) {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?r_name ?r_difficulty ?r_fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:name ?r_name . "+
			"      ?concept base:id " +StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      ?resource base:is_resource_of_C ?concept . "+
			"      ?resource base:difficulty ?r_difficulty . "+
			"      ?resource base:fileLocation ?r_fileLocation . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EResource res = QuerySolutionParserTwo.getEResource(qs, model);
			resources.add(res);
		}
		qe.close();
		return resources;
	}
	 
	public ArrayList<EResource> getAllEResources() {
		ArrayList<EResource> resources = new ArrayList<EResource>();
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?resource ?r_name ?r_difficulty ?r_fileLocation " +
			"WHERE {" +
			"      ?resource rdf:type base:E_Resource . " +
			"      ?resource base:name ?r_name . "+
		 	"      ?resource base:difficulty ?r_difficulty . "+
			"      ?resource base:fileLocation ?r_fileLocation . "+
			"      }";

		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		while(results.hasNext()){
			QuerySolution qs = results.next();
			EResource res = QuerySolutionParserTwo.getEResource(qs, model);
			resources.add(res);
		}
		qe.close();
		return resources;
	}
	 
	public EPerformance getEPerformance(ELearner elearner, EConcept concept) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?performance ?preformance_value " +
			"WHERE {" +
			"      ?performance rdf:type base:E_Performance . " +
			"      ?performance base:value ?preformance_value . "+
		 	"      ?performance base:inverse_of_has_performance ?elearner . "+
			"      ?performance base:inverse_of_is_concept_of_P ?concept . "+
			"      ?concept base:id "+StringExchanger.getSparqlString(concept.getCid())+" . "+
			"      ?elearner base:id "+StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      "+
			"      }";
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		EPerformance performance = null;
		while(results.hasNext()){
			performance = new EPerformance();
			QuerySolution qs = results.next();
			String id = QuerySolutionParserTwo.getIdByURI(qs, model, "?performance");
			String v= qs.get("?preformance_value").toString().trim();
			String valueString = StringExchanger.getCommonString(v);
			float value = Float.parseFloat((valueString));
			performance.setId(id);
			performance.setValue(value);
			performance.setConcept(concept);
			performance.setElearner(elearner);
		}
		qe.close();
		return performance;
	}
	 
	public boolean updatePerformance(EPerformance performance) {
		// TODO Auto-generated method stub
		Resource r = model.getResource(Constant.NS+performance.getId());
		r.removeAll(model.getProperty(Constant.NS+"value"));
		r.addProperty(model.getProperty(Constant.NS+"value"), String.valueOf(performance.getValue()));
		return true;
	}
	
	 
	public EPortfolio getEPortfolio(ELearner elearner, EResource resource) {
		String queryString = 
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
			"PREFIX base: <http://www.owl-ontologies.com/e-learning.owl#> " +
			"SELECT ?portfolio ?portfolio_value " +
			"WHERE {" +
			"      ?portfolio rdf:type base:E_Portfolio . " +
			"      ?portfolio base:value ?portfolio_value . "+
		 	"      ?portfolio base:inverse_of_is_resource_of_P ?resource . "+
			"      ?portfolio base:inverse_of_has_portfolio ?elearner . "+
			"      ?resource base:id "+StringExchanger.getSparqlString(resource.getRid())+" . "+
			"      ?elearner base:id "+StringExchanger.getSparqlString(elearner.getId())+" . "+
			"      }";
		Query query = QueryFactory.create(queryString);
		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();
		
		EPortfolio portfolio = null;
		while(results.hasNext()){
			portfolio = new EPortfolio();
			QuerySolution qs = results.next();
			String id = QuerySolutionParserTwo.getIdByURI(qs, model, "?portfolio");
			String v= qs.get("?portfolio_value").toString().trim();
			String valueString = StringExchanger.getCommonString(v);
			float value = Float.parseFloat((valueString));
			portfolio.setId(id);
			portfolio.setValue(value);
			portfolio.setEResource(resource);
			portfolio.setElearner(elearner);
		}
		qe.close();
		return portfolio;
	}
	 
	public boolean addPropertyIsResourceOfC(EResource resource, EConcept concept) {
		Resource res = model.getResource(Constant.NS+resource.getRid());
		Resource con = model.getResource(Constant.NS+concept.getCid());
		model.add(res, model.getProperty(Constant.NS+"is_resource_of_C"), con);
		return true;
	}
	
}
