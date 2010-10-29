package ontology.resources.classify;

import java.util.ArrayList;

import ontology.people.People;


public class LearningMaterial extends E_EducationMaterial{
	private String resourceId;
	private String resourceName;
	protected ArrayList<LearningExperience> categories;
	protected String type;
	protected String description;
	protected People creator;
	public LearningMaterial(){
		
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
}
