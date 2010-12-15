package ontology.people;
import java.util.ArrayList;

import ontology.resources.Lecture;

public class Teacher extends People {
	public Teacher(){
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<Lecture> getCourses() {
		return courses;
	}
	public void setCourses(ArrayList<Lecture> courses) {
		this.courses = courses;
	}

	private String title;
	private ArrayList<Lecture> courses;
}
