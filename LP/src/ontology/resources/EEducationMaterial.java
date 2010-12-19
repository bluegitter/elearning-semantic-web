package ontology.resources;

public class EEducationMaterial {
	protected String id;
	protected EResource resource;
	public EEducationMaterial(){
		id="id";
	}
	public EEducationMaterial(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public EResource getResource() {
		return resource;
	}
	public void setResource(EResource resource) {
		this.resource = resource;
	}
	
}
