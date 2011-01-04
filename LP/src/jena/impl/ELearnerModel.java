package jena.impl;

import java.io.File;
import java.io.IOException;
import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.EPortfolio;
import ontology.people.ELearner;
import ontology.resources.EResource;
import util.Constant;
import util.StringExchanger;
import jena.OwlFactory;
import jena.interfaces.ELearnerModelAddOperationInterface;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import db.OwlOperation;
import exception.jena.IndividualNotExistException;

public class ELearnerModel implements ELearnerModelAddOperationInterface{
	protected OntModel ontModel;

    public ELearnerModel() {
        ontModel = OwlFactory.getOntModel();
    }
    public ELearnerModel(OntModel ontModel) {
        this.ontModel = ontModel;
    }
    public ELearnerModel(File file){
    	this.ontModel = OwlFactory.getOntModel(file);
    }
    public ELearnerModel(File file,String lang){
    	this.ontModel = OwlFactory.getOntModel(file, lang);
    }
    
    public boolean writeToFile(File file) {
        try {
            OwlOperation.writeOwlFile(ontModel, file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
	public EConcept getRootConcept(){
		EConcept rootConcept = new EConcept();
		rootConcept.setCid("Software_Engineer");
		rootConcept.setName("software engineering");
		return rootConcept;
	}
    
    public OntModel getOntModel(){
    	return ontModel;
    }
    public InfModel getInfModel(){
    	return ontModel;
    }
    /**************************************************************************************************
     * Check whether the individual exists in the model
     ***************************************************************************************************/
	public boolean containEConcept(String cid) {
    	return ontModel.containsResource(ontModel.getResource(Constant.NS+cid));
	}
	public boolean containELearner(String eid) {
		return ontModel.containsResource(ontModel.getResource(Constant.NS+eid));
	}
	public boolean containEResource(String rid) {
		return ontModel.containsResource(ontModel.getResource(Constant.NS+rid));
	}
	public boolean containEPortfolio(String portId) {
		return ontModel.containsResource(ontModel.getResource(Constant.NS+portId));
	}
	public boolean containEPerformance(String performId){
		return ontModel.containsResource(ontModel.getResource(Constant.NS+performId));
	}
	public boolean containEInterest(String interestId){
		return ontModel.containsResource(ontModel.getResource(Constant.NS+interestId));
	}
    /*******************************************************************************************************
     * Add new Data Operations
     * @throws IndividualNotExistException 
     *******************************************************************************************************/
    @Override
    public boolean addELearner(ELearner elearner) throws IndividualNotExistException {
    	if(containELearner(elearner.getId())){
    		throw new IndividualNotExistException("elearner "+elearner.getId()+" has already existed in the model");
    	}
        Resource el = ontModel.createResource(Constant.NS + elearner.getId(), ontModel.getResource(Constant.NS + "E_Learner"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "id"), elearner.getId());
        ontModel.add(el, ontModel.getProperty(Constant.NS + "name"), elearner.getName(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "grade"), elearner.getGrade(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "address"), elearner.getAddress(), new XSDDatatype("string"));
        ontModel.add(el, ontModel.getProperty(Constant.NS + "email"), elearner.getEmail(), new XSDDatatype("string"));
        return true;
    }

    @Override 
    public boolean addEResource(EResource resource) throws IndividualNotExistException {
    	if(containEResource(resource.getRid())){
    		throw new IndividualNotExistException("EResource "+resource.getRid()+" has already existed in the model");
    	}
    	Resource re = ontModel.createResource(Constant.NS + resource.getRid(), ontModel.getResource(Constant.NS + "E_Resource"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "id"), resource.getRid());
        ontModel.add(re, ontModel.getProperty(Constant.NS + "name"), resource.getName(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "fileLocation"), resource.getFileLocation(), new XSDDatatype("string"));
        ontModel.add(re, ontModel.getProperty(Constant.NS + "difficulty"), resource.getDifficulty(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEPortfolio(EPortfolio portfolio) {
        ELearner el = portfolio.getElearner();
        EResource res = portfolio.getEResource();
        Resource port = ontModel.createResource(Constant.NS + portfolio.getId(), ontModel.getResource(Constant.NS + "E_Portfolio"));
        ontModel.add(port, ontModel.getProperty(Constant.NS + "inverse_of_has_portfolio"), ontModel.getResource(Constant.NS + el.getId()));
        ontModel.add(port, ontModel.getProperty(Constant.NS + "inverse_of_is_resource_of_P"), ontModel.getResource(Constant.NS + res.getRid()));
        ontModel.addLiteral(port, ontModel.getProperty(Constant.NS + "value"), portfolio.getValue());
        ontModel.add(port, ontModel.getProperty(Constant.NS + "datetime"), StringExchanger.parseDateToString(portfolio.getDatetime()), new XSDDatatype("dateTime"));
        return true;
    }

    @Override 
    public boolean addEConcept(EConcept concept) throws IndividualNotExistException {
    	if(containEConcept(concept.getCid())){
    		throw new IndividualNotExistException("EConcept "+concept.getCid()+"  has already existed in the model");
    	}
        Resource con = ontModel.createResource(Constant.NS + concept.getCid(), ontModel.getResource(Constant.NS + "E_Concept"));
        ontModel.add(con, ontModel.getProperty(Constant.NS + "id"), concept.getCid());
        ontModel.add(con, ontModel.getProperty(Constant.NS + "name"), concept.getName(), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEInterest(EInterest interest) {
        ELearner el = interest.getELearner();
        EConcept con = interest.getEConcept();
        Resource in = ontModel.createResource(Constant.NS + interest.getId(), ontModel.getResource(Constant.NS + "E_Interest"));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_has_interest"), ontModel.getResource(Constant.NS + el.getId()));
        in.addProperty(ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_I"), ontModel.getResource(Constant.NS + con.getCid()));
        in.addProperty(ontModel.getProperty(Constant.NS + "value"), String.valueOf(interest.getValue()), new XSDDatatype("string"));
        return true;
    }

    @Override
    public boolean addEPerfomance(EPerformance performance) {
        ELearner el = performance.getElearner();
        EConcept con = performance.getConcept();
        Resource perf = ontModel.createResource(Constant.NS + performance.getId(), ontModel.getResource(Constant.NS + "E_Performance"));
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "inverse_of_has_performance"), ontModel.getResource(Constant.NS + el.getId()));
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "inverse_of_is_concept_of_P"), ontModel.getResource(Constant.NS + con.getCid()));
        ontModel.addLiteral(perf, ontModel.getProperty(Constant.NS + "value"), performance.getValue());
        ontModel.add(perf, ontModel.getProperty(Constant.NS + "datetime"), StringExchanger.parseDateToString(performance.getDatetime()), new XSDDatatype("dateTime"));
        return true;
    }
    @Override
    public boolean addPropertyIsSonOf(EConcept fatherConcept, EConcept sonConcept) {
        Resource son = ontModel.getResource(Constant.NS + sonConcept.getCid());
        Resource father = ontModel.getResource(Constant.NS + fatherConcept.getCid());
        ontModel.add(son, ontModel.getProperty(Constant.NS + "is_son_of"), father);
        return true;
    }

    @Override
    public boolean addPropertyIsResourceOfC(EResource resource, EConcept concept) {
        Individual res = ontModel.getIndividual(Constant.NS + resource.getRid());
        Individual con = ontModel.getIndividual(Constant.NS + concept.getCid());
        ontModel.add(res, ontModel.getProperty(Constant.NS + "is_resource_of_C"), con);
        return true;
    }
	
	
}
