package ontology.people;
import java.util.ArrayList;

import ontology.resources.Certification;
import ontology.resources.Lecture;

public class ELearner extends People{
	private String grade;
	private String peopleURL;
	
	public ELearner(){
		
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
	
}
