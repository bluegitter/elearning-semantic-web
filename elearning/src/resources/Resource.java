package resources;

import java.util.ArrayList;

import user.Person;

public class Resource {
	private String resourceId;
	private String resourceName;
	protected ArrayList<Category> categories;
	protected String type;
	protected String description;
	protected Person creator;
	public Resource(){
		
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
