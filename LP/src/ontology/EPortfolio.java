package ontology;

import java.util.Date;

import ontology.people.ELearner;
import ontology.resources.EResource;

public class EPortfolio {
	private ELearner elearner;
	private EResource resource;
	private float value;
	private String id;
	private Date datetime;
	public EPortfolio(){
		
	}
	public EPortfolio(String id,ELearner elearner,EResource resource,float value,Date datetime){
		this.id = id;
		this.elearner = elearner;
		this.resource = resource;
		this.value = value;
		this.datetime = datetime;
	}
	public ELearner getElearner() {
		return elearner;
	}
	public void setElearner(ELearner elearner) {
		this.elearner = elearner;
	}
	public EResource getEResource() {
		return resource;
	}
	public void setEResource(EResource resource) {
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
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
	public String toString(){
		return id+"\t"+elearner.getId()+"\t"+resource.getRid()+"\t"+value+"\t"+datetime;
	}
}
