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
	public EConcept(String cid,String cname){
		this.cid = cid;
		this.name = cname;
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
        public boolean equals(Object o){
            if(o instanceof EConcept){
                EConcept con = (EConcept) o;
                if(!this.cid.equals(con.getCid())){
                    return false;
                }
                if(!this.name.equals(con.getName())){
                    return false;
                }
                return true;
            }
            return false;
        }
	private String cid;
	private String name;
	
}
