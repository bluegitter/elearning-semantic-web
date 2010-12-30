package jena;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import jena.impl.ELearnerModelImplOne;

import ontology.EConcept;
import ontology.people.ELearner;

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
import com.hp.hpl.jena.rdf.model.impl.StatementImpl;

import exception.ConceptNotExistInModelException;
/*************************************************************
 * Elearner Reasoner is the class used for reasoning.
 * The rules, ontology classes and the properties of the model are the param of this class.
 * 
 * @author William
 *
 */
public class ELearnerReasoner {

	public static ArrayList<Individual> getAllConcepts(OntModel ontModel){
		ArrayList <Individual> concepts = new ArrayList<Individual>();
		OntClass concept = ontModel.getOntClass(Constant.NS+"E_Concept");
		Iterator <Individual>iter2 = ontModel.listIndividuals();
		while(iter2.hasNext()){
			Individual indi= (Individual) iter2.next();
			if(concept.equals(indi.getOntClass())){
				concepts.add(indi);
			}
		}
		return concepts;
	}
	public static ArrayList<Individual> getAllResources(OntModel ontModel){
		ArrayList <Individual> resources = new ArrayList<Individual>();
		OntClass resource = ontModel.getOntClass(Constant.NS+"E_Resource");
		Iterator <Individual>iter2 = ontModel.listIndividuals();
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
	public static EConcept getEConcept(OntModel ontModel,String cid){
		EConcept concept = new EConcept(cid);
		Individual indi = ontModel.getIndividual(Constant.NS+cid);
		concept.setName(indi.getPropertyValue(ontModel.getProperty(Constant.NS+"name")).asLiteral().getString());
		return concept;
	}
	
	public static ArrayList<EConcept> getRecommendEConcepts_1(OntModel ontModel,ELearner elearner){
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		Resource el = ontModel.getResource(Constant.NS+elearner.getId());
		SimpleSelector selector = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"inverse_of_has_performance"), el);
		StmtIterator iter = ontModel.listStatements(selector);
		while(iter.hasNext()){
			Resource per = iter.nextStatement().getSubject();
			SimpleSelector per_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"is_concept_of_P"), per);
			StmtIterator iter2 = ontModel.listStatements(per_con);
			while(iter2.hasNext()){
				Resource con = iter2.nextStatement().getSubject();
				SimpleSelector con_con = new SimpleSelector(null, ontModel.getProperty(Constant.NS+"is_post_concept_of"), con);
				StmtIterator iter3 = ontModel.listStatements(con_con);
				while(iter3.hasNext()){
					Resource result = iter3.nextStatement().getSubject();
					String id = result.getLocalName();
					System.out.println(id);
					EConcept newCon = getEConcept(ontModel,id);
					concepts.add(newCon);
				}
			}
		}
		return concepts;
	}
}

