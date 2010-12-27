package jena.impl;

import java.io.File;
import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;

import ontology.EConcept;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
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
	@Override
	public ArrayList<EConcept> getSonConcepts(EConcept concept) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<EConcept> getMemberConcept(EConcept concept) {
		// TODO Auto-generated method stub
		return null;
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
