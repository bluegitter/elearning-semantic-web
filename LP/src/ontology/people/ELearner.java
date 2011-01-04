package ontology.people;
import db.DbOperation;

public class ELearner extends People{
	private String peopleURL;
	private String password;
	private String grade="";
	private String email="";
	private String address="";

	public ELearner(String eid,String password){
		this.id = eid;
		this.password = password;
	}
        public ELearner(String eid,String _password,String _email,String _address){
		this.id = eid;
		this.password = _password;
                this.email = _email;
                this.address = _address;
	}

	public ELearner(String eid){
		this.id = eid;
	}
	public ELearner(){
		id="user";
		password="password";
	}
	
	public String login(){
		try {
			boolean validate = DbOperation.login(id, password);
			System.out.println("login validate:"+validate);
			if(validate){
				return "goToMain";
			}
			return "false";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getPeopleURL() {
		return peopleURL;
	}
	public void setPeopleURL(String peopleURL) {
		this.peopleURL = peopleURL;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(id+"\t"+name+"\t"+grade+"\t"+password+"\n");
		sb.append(grade+"\t"+email+"\t"+address);
		return sb.toString();
	}
	
}
