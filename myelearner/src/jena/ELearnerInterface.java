package jena;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.EResource;

import com.hp.hpl.jena.rdf.model.Model;

public interface ELearnerInterface {
	Model addELearner(Model model,ELearner elearner);
	Model addResource(Model model,EResource resource);
	Model addInterest(Model model,EInterest interest);
	Model addPerfomance(Model model,EPerformance performance);
	
	Model addConcept(Model model,EConcept concept);
	
}
