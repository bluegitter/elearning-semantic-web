package ontology.people;
import java.util.ArrayList;

import db.DbOperation;
import ontology.resources.Lecture;

public class ELearner extends People{
	private String grade;
	private String peopleURL;
	private String password;

	public ELearner(String id,String password){
		this.id = id;
		this.password = password;
	}
	public ELearner(){
		id="user";
		password="password";
	}
	
	public String login(){
		DbOperation dbo=new DbOperation();
		try {
			boolean validate = dbo.login(id, password);
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(id+"\t"+name+"\t"+grade+"\t"+password);
		return sb.toString();
	}
	
}
