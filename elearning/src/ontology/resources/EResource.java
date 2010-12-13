package ontology.resources;

public class EResource {
	private String rid;
	private String difficulty;
	private String name;
	private String fileLocation;
	private String postfix;
	public EResource(){
		postfix="pdf";
	}
	public EResource(String rid){
		this.rid = rid;
		postfix="pdf";
	}

	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getName() {
		return name;
	}

	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return rid+"\t"+name+"\t"+difficulty+"\n"+fileLocation;
	}
	
}
