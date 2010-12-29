package jena.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.hp.hpl.jena.ontology.HasValueRestriction;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import util.StringExchanger;
import jena.interfaces.ELearnerModelQueryInterface;
import jena.interfaces.ELearnerRuleModel;
/**************************************************************************
 * Model Query with ontology Model
 * @author william
 *
 */
public class ELearnerModelImplThree extends ELearnerModel implements ELearnerModelQueryInterface, ELearnerRuleModel{
	public ELearnerModelImplThree(){
		super();
	}
	public ELearnerModelImplThree(File file){
		super(file);
	}
	public ELearnerModelImplThree(File file,String lang){
		super(file,lang);
	}


    public static void main(String [] args){
    	File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
    	ELearnerModelImplThree emi = new ELearnerModelImplThree(file);
    	emi.getEConcept("CMP");
    	/*
    	 ELearnerModelImpl emi = new ELearnerModelImpl(new File(Constant.OWLFile));
		ELearnerReasoner er = new ELearnerReasoner(emi.getOntModel());
		long time1 = System.currentTimeMillis();
		ArrayList<EResource> er_resources = er.getAllEResources();
		long time2 = System.currentTimeMillis();
		ArrayList<EResource> emi_resources =emi.getAllEResources();
		long time3 = System.currentTimeMillis();
		
		System.out.println("er executing time: "+(time2-time1)+"ms\t"+er_resources.size());
		System.out.println("emi executing time: "+(time3-time2)+"ms\t"+emi_resources.size());
    	 */
    }
    @Override
	public boolean containEConcept(String cid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containELearner(String eid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containEResource(String rid) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ArrayList<EConcept> getAllEConcepts() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EResource> getAllEResources() {
		Resource resourceClass = ontModel.getResource(Constant.NS+"E_Resource");
		SimpleSelector selector = new SimpleSelector(null,ontModel.getProperty(Constant.NSRDF+"type"),resourceClass);
		StmtIterator iter = ontModel.listStatements(selector);
		
		ArrayList<EResource> resources = new ArrayList<EResource>();
		while(iter.hasNext()){
			Statement s = iter.nextStatement();
			System.out.println(s);
			Resource r = s.getResource();
			
			EResource er  = new EResource(r.getLocalName());
	//		String name = r.getRequiredProperty(ontModel.getProperty(Constant.NS+"name")).toString();
	//		String difficulty = r.getRequiredProperty(ontModel.getProperty(Constant.NS+"difficulty")).toString();
			
	//		er.setName(name);
	//		er.setDifficulty(difficulty);
			System.out.println(er);
			resources.add(er);
		}
		return resources;
	}
	@Override
	public EConcept getEConcept(String cid) {
		EConcept concept = new EConcept(cid);
		Individual indi = ontModel.getIndividual(Constant.NS+cid);
		concept.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"name")).asLiteral().getString());
		return concept;
	}
	@Override
	public ELearner getELearner(String eid) {
		ELearner elearner = new ELearner(eid);
		Individual indi = ontModel.getIndividual(Constant.NS+eid);
		elearner.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"name")).asLiteral().getString());
		elearner.setEmail(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"email")).asLiteral().getString());
		elearner.setAddress(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"address")).asLiteral().getString());
		elearner.setGrade(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"grade")).asLiteral().getString());
		return elearner;
	}
	@Override
	public EPerformance getEPerformance(ELearner elearner, EConcept concept) {
		Resource con = ontModel.getResource(Constant.NS+concept.getCid());
		Resource el = ontModel.getResource(Constant.NS+elearner.getId());
		
		SimpleSelector selector_el = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"inverse_of_has_performance"), el);
		StmtIterator iter_el = ontModel.listStatements(selector_el);
		while(iter_el.hasNext()){
			Statement s = iter_el.nextStatement();
			Resource r = s.getSubject();
			
			SimpleSelector selector_con = new SimpleSelector(r, ontModel.getProperty(Constant.NS+"inverse_of_is_concept_of_P"), con);
			StmtIterator iter_con = ontModel.listStatements(selector_con);
			while(iter_con.hasNext()){
				float value = (Float) r.getRequiredProperty(ontModel.getProperty(Constant.NS+"value")).getLiteral().getFloat();
				System.out.println(value);
				EPerformance performance = new EPerformance();
				performance.setElearner(elearner);
				performance.setConcept(concept);
				//performance.setDatetime(datetime);
				performance.setId(r.getLocalName());
				performance.setValue(value);
				return performance;
			}
			
		}
		return null;
	}
	@Override
	public ArrayList<EPerformance> getEPerformances(ELearner elearner) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public EPortfolio getEPortfolio(ELearner elearner, EResource resource) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EPortfolio> getEPortfolios(ELearner elearner) {
    	ArrayList<EPortfolio> portfolios = new ArrayList<EPortfolio>();
    	Resource el = ontModel.getResource(Constant.NS+ elearner.getId());
    	SimpleSelector port_selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"inverse_of_has_portfolio"), el);

		StmtIterator port_iter = ontModel.listStatements(port_selector);
		while(port_iter.hasNext()){
			Statement s = port_iter.nextStatement();
			Resource portResource = s.getSubject();
			float value =(Float) portResource.getRequiredProperty(ontModel.getProperty(Constant.NS+"value")).asTriple().getObject().getLiteralValue();
			String dateString = portResource.getRequiredProperty(ontModel.getProperty(Constant.NS + "datetime")).asTriple().getObject().getLiteralValue().toString();
			Date datetime = StringExchanger.parse(dateString);
			SimpleSelector res_selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"is_resource_of_P"), portResource);
			StmtIterator res_iter = ontModel.listStatements(res_selector);
			EResource resource  = null;
			while(res_iter.hasNext()){
				Statement res_statement = res_iter.nextStatement();
				Resource resResource = res_statement.getSubject();
				String resId = resResource.getLocalName();
				resource = getEResource(resId);
			}
			EPortfolio port = new EPortfolio();
			port.setId(portResource.getLocalName());
            port.setEResource(resource);
            port.setElearner(elearner);
            port.setValue(value);
            port.setDatetime(datetime);
            portfolios.add(port);
		}
        return portfolios;
    }

	@Override
	public EResource getEResource(String rid) {
		EResource resource = new EResource(rid);
		Individual indi = ontModel.getIndividual(Constant.NS+rid);
		resource.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"name")).asLiteral().getString());
		resource.setFileLocation(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"fileLocation")).asLiteral().getString());
		resource.setDifficulty(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"difficulty")).asLiteral().getString());
		return resource;
	}
	@Override
	public ArrayList<EResource> getEResourcesByEConcept(EConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EResource> getEResourcesByInterestEConcepts(
			ELearner elearner, EConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getInterestConcepts(ELearner elearner) {
		// TODO Auto-generated method stub
		return null;
	}
    public ArrayList<EConcept> getSonConcepts(EConcept econcept){
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		Resource concept = ontModel.getResource(Constant.NS+econcept.getCid());
		SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"is_son_of"), concept);
		StmtIterator iter = ontModel.listStatements(selector);
		while(iter.hasNext()){
			Statement s = iter.nextStatement();
			Resource r = s.getSubject();
			String nameString = r.getRequiredProperty(ontModel.getProperty(Constant.NS+"name")).asTriple().getObject().toString();
			String name = StringExchanger.getCommonString(nameString);
			concepts.add(new EConcept(s.getSubject().getLocalName(),name));
		}
		return concepts;
	}
	@Override
	public ArrayList<EConcept> getMemberConcept(EConcept concept) {
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		Resource con = ontModel.getResource(Constant.NS+concept.getCid());
		SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"is_part_of"), con);
		StmtIterator iter = ontModel.listStatements(selector);
		while(iter.hasNext()){
			Statement s = iter.nextStatement();
			Resource r = s.getSubject();
			String nameString = r.getRequiredProperty(ontModel.getProperty(Constant.NS+"name")).asTriple().getObject().toString();
			String name = StringExchanger.getCommonString(nameString);
			concepts.add(new EConcept(s.getSubject().getLocalName(),name));
		}
		return concepts;
	}
	@Override
	public ArrayList<EConcept> getRecommendEConcepts(ELearner elearner, int rule) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ELearner> getRecommendELearners(ELearner elearner, int rule) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EResource> getRecommendEResources(ELearner elearner,
			int rule) {
		// TODO Auto-generated method stub
		return null;
	}
}
