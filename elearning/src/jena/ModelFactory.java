package jena;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;

import ontology.people.ELearner;
import ontology.people.People;

public class ModelFactory {
	public static void main(String []args){
		ELearner ms = new ELearner();
		ms.setPeopleURL("http://somewhere/elearner_001");
		ms.setName("masheng");
		ms.setId("s001");
		ms.setGrade("Grade Two");

	}
	public void createPeopleModel(People people){

	
	}
}
