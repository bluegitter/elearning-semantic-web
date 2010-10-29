package ontology.people;
import java.util.ArrayList;

import ontology.resources.Lecture;

public class Teacher extends People {
	public Teacher(){
		
	}
	private String teacherId;
	private String teacherName;
	private String position;
	private ArrayList<Lecture> courses;
}
