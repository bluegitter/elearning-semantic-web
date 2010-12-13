package ontology;

import ontology.people.ELearner;

public class EPerformance {
	public EPerformance(){
		
	}
	
	public ELearner getElearner() {
		return elearner;
	}
	public void setElearner(ELearner elearner) {
		this.elearner = elearner;
	}
	public EConcept getConcept() {
		return concept;
	}
	public void setConcept(EConcept concept) {
		this.concept = concept;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private ELearner elearner;
	private EConcept concept;
	private float value;
	private String id;
}
