package bak;

import ontology.EConcept;
import ontology.EInterest;
import ontology.EPerformance;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

import com.hp.hpl.jena.rdf.model.Model;

public interface ELearnerInterface {
	Model addELearner(Model model,ELearner elearner);
	Model addResource(Model model,ISCB_Resource resource);
	Model addInterest(Model model,EInterest interest);
	Model addPerfomance(Model model,EPerformance performance);
	
	Model addConcept(Model model,EConcept concept);
	
}
