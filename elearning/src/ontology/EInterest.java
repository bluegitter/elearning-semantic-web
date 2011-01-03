package ontology;

import ontology.people.ELearner;

public class EInterest {
	public EInterest(){
		
	}
	public EInterest(String id){
		this.id = id;
	}	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	public ELearner getELearner() {
		return elearner;
	}
	public void setELearner(ELearner elearner) {
		this.elearner = elearner;
	}
	public EConcept getEConcept() {
		return econcept;
	}
	public void setEConcept(EConcept econcept) {
		this.econcept = econcept;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	private float value;
	private ELearner elearner;
	private EConcept econcept;
	
}
