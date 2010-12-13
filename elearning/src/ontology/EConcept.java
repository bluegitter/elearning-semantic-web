package ontology;

public class EConcept {
	public EConcept(){
		cid = "tempCID";
		name = "name";
	}
	public EConcept(String cid){
		this.cid = cid;
		this.name = "name";
	}

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return name;
	}

	private String cid;
	private String name;
	
}
