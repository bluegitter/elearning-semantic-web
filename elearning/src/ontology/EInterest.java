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

	private float value;
	private ELearner el;
	private EConcept con;
	
}
