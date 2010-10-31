package jena;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

import ontology.people.ELearner;
import ontology.people.People;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.SomeValuesFromRestriction;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.vocabulary.RDFS;
public class OntologyModelSample {
	public static String NS ="http://www.elearning.com/";
	public static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
	
	public static void main(String []args){
		ELearner ms = new ELearner();
		ms.setPeopleURL(NS+"elearner_001");
		ms.setName("masheng");
		ms.setId("s001");
		ms.setGrade("Grade Two");

	}

	public static void initOntologyModel(){
		//people Class
		OntClass people = model.createClass(NS+"E_People");
		OntClass elearner = model.createClass(NS+"E_Learner");
		OntClass teacher = model.createClass(NS+"E_Teacher");
		people.setSubClass(elearner);
		people.setSubClass(teacher);
		
		//concept and resources Class
		OntClass concept = model.createClass(NS+"E_Concept");
		OntClass resource = model.createClass(NS+"E_Resource");
		
		OntClass eEducationMaterial = model.createClass(NS+"E_EducationMaterial");
		OntClass learningMaterial = model.createClass(NS+"LearningMaterial");
		OntClass lecture = model.createClass(NS+"Lecture");
		eEducationMaterial.addSubClass(learningMaterial);
		learningMaterial.addSubClass(lecture);
		
		//people characters
		OntClass people_characters = model.createClass(NS+"E_People_Characters");
		OntClass preference = model.createClass(NS+"E_Preference");
		OntClass interest = model.createClass(NS+"E_Interest");
		
		people_characters.addSubClass(preference);
		preference.addSubClass(interest);
		
		
		//Properties
		//the property between Resource and Education Material
		ObjectProperty is_application_of = model.createObjectProperty(NS+"is_application_of");
		is_application_of.addRange(resource);
		is_application_of.addDomain(eEducationMaterial);
		SomeValuesFromRestriction application_part = model.createSomeValuesFromRestriction(null, is_application_of, eEducationMaterial);
		
		
		//the properties between Concepts and Resources
		ObjectProperty has_resource = model.createObjectProperty(NS+"has_resource");
		has_resource.addDomain(concept);
		has_resource.addRange(resource);
		
		ObjectProperty is_resource_of = model.createObjectProperty(NS+"is_resource_of");
		is_resource_of.addDomain(resource);
		is_resource_of.addRange(concept);
		
		//the properties between Elearner and Concepts
		ObjectProperty is_concept_of = model.createObjectProperty(NS+"is_concept_of");
		is_concept_of.addDomain(concept);
		is_concept_of.addRange(interest);
		
		ObjectProperty has_concept = model.createObjectProperty(NS+"has_concept");
		has_concept.addDomain(interest);
		has_concept.addRange(concept);
		
		ObjectProperty is_interest_of = model.createObjectProperty(NS+"is_interest_of");
		is_interest_of.addDomain(interest);
		is_interest_of.addRange(elearner);
		
		ObjectProperty has_interest = model.createObjectProperty(NS+"has_interest");
		has_interest.addDomain(elearner);
		has_interest.addRange(interest);
		
		//individual
		Individual ms = model.createIndividual(NS+"e001",elearner);
		Individual wsg = model.createIndividual(NS+"e002",elearner);
		Individual interst1 = model.createIndividual(NS+"interest1",interest);
		
		ms.addProperty(has_interest, "concept_interst1");
		
	}
	public static void initIndividual(){
		
	}

}
