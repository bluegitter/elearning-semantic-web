package bak;

import java.util.ArrayList;

import ontology.EConcept;
import ontology.people.ELearner;
import ontology.resources.ISCB_Resource;

public interface ELearnerRuleModel {
	ArrayList<EConcept> getMemberConcept(EConcept concept);

	/***************************************************************************
	 * there are 13 rules now
	 * is_recommend_of_c_ (0--8)-->ELearner VS EConcept
	 * is_recommend_of_L_ (0 1)--> ELearner VS ELearner
	 * is_recommend_of_r_ (0 3) -->ELearner VS Resource
	 * TO BE Test
	 ****************************************************************************/
	
	ArrayList<EConcept> getRecommendEConcepts(ELearner elearner,int rule);
	ArrayList<ISCB_Resource> getRecommendEResources(ELearner elearner,int rule);
	ArrayList<ELearner> getRecommendELearners(ELearner elearner,int rule);
}
