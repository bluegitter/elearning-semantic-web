package ontology;

import ontology.people.ELearner;
import ontology.resources.EResource;

public class EPortfolio {
	private ELearner elearner;
	private EResource resource;
	private float value;
	private String id;
	public EPortfolio(){
		
	}
	public EPortfolio(String id,ELearner elearner,EResource resource,float value){
		this.id = id;
		this.elearner = elearner;
		this.resource = resource;
		this.value = value;
	}
	public ELearner getElearner() {
		return elearner;
	}
	public void setElearner(ELearner elearner) {
		this.elearner = elearner;
	}
	public EResource getResource() {
		return resource;
	}
	public void setResource(EResource resource) {
		this.resource = resource;
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
	
}
