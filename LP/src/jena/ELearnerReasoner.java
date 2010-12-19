package jena;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import util.Constant;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import exception.ConceptNotExistInModelException;
/*************************************************************
 * Elearner Reasoner is the class used for reasoning.
 * The rules, ontology classes and the properties of the model are the param of this class.
 * 
 * @author William
 *
 */
public class ELearnerReasoner {

	public ELearnerReasoner(){
		
	}
	public ELearnerReasoner(File owlFile,File ruleFile){
		this.owlFile = owlFile;
		this.ruleFile = ruleFile;
	}
	
	/********************************************************************
	 * the initial model location is set in the file Constant which locates in the util package.
	 * @return
	 ******************************************************************/
	public ArrayList<Individual> getAllConcepts(){
		OntModel model = OwlFactory.getOntModel();
		ArrayList <Individual> concepts = new ArrayList<Individual>();
		OntClass concept = model.getOntClass(Constant.NS+"E_Concept");
		Iterator <Individual>iter2 = model.listIndividuals();
		while(iter2.hasNext()){
			Individual indi= (Individual) iter2.next();
			if(concept.equals(indi.getOntClass())){
				concepts.add(indi);
				System.out.println(indi.getLocalName());
			}
		}
		return concepts;
	}
	public ArrayList<Individual> getAllResources(){
		OntModel model = OwlFactory.getOntModel();
		ArrayList <Individual> resources = new ArrayList<Individual>();
		OntClass resource = model.getOntClass(Constant.NS+"E_Resource");
		Iterator <Individual>iter2 = model.listIndividuals();
		while(iter2.hasNext()){
			Individual indi= (Individual) iter2.next();
			if(indi==null)break;
			if(resource.equals(indi.getOntClass())){
				resources.add(indi);
				System.out.println(indi.getLocalName());
			}
		}
		return resources;
	}
	public ArrayList<Individual> getPreConcepts(Resource concept,OntModel model) throws ConceptNotExistInModelException{
		/*
		if(!hasConcept(concept,model)){
			throw new ConceptNotExistInModelException("the model doesn't contain the concept"+concept.getLocalName());
		}*/
		Resource ontConcept = model.getResource(Constant.NS+"RIA");
		SimpleSelector selector = new SimpleSelector(concept, model.getProperty(Constant.NS+"is_post_concept_of"), (RDFNode)ontConcept);
		StmtIterator iter = model.listStatements(selector);
		
		ArrayList <Individual> concepts = new ArrayList<Individual>();
		while(iter.hasNext()){
			Statement s = iter.nextStatement();
			System.out.println(s);
			Individual indi= (Individual) iter.next();
			System.out.println("ms");
				concepts.add(indi);
				System.out.println(indi.getLocalName());
		}
		return concepts;
	}
	public boolean hasConcept(Individual concept,OntModel model){
		Individual con = model.getIndividual(concept.getURI());
		if(con ==null){
			return false;
		}
		return true;
	}
	
	
	public InfModel getInfModel() {
		return infModel;
	}
	public void setInfModel(InfModel infModel) {
		this.infModel = infModel;
	}
	public OntModel getOntModel() {
		return ontModel;
	}
	public void setOntModel(OntModel ontModel) {
		this.ontModel = ontModel;
	}
	public File getOwlFile() {
		return owlFile;
	}
	public void setOwlFile(File owlFile) {
		this.owlFile = owlFile;
	}
	public File getRuleFile() {
		return ruleFile;
	}
	public void setRuleFile(File ruleFile) {
		this.ruleFile = ruleFile;
	}


	private InfModel infModel;
	private OntModel ontModel;
	private File owlFile;
	private File ruleFile;
}

