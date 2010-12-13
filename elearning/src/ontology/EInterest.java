package ontology;

import ontology.people.ELearner;

public class EInterest {
	public EInterest(){
		
	}
	
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public ELearner getEl() {
		return el;
	}
	public void setEl(ELearner el) {
		this.el = el;
	}
	public EConcept getCon() {
		return con;
	}
	public void setCon(EConcept con) {
		this.con = con;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	private float value;
	private ELearner el;
	private EConcept con;
	
}
