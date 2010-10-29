package ontology.resources;

import java.util.ArrayList;

import ontology.people.People;
import ontology.resources.classify.E_EducationMaterial;
import ontology.resources.classify.LearningMaterial;


import util.Constant;



public class Lecture extends LearningMaterial{
	public Lecture(){
		courseId = super.getResourceId();
		courseTitle = super.getResourceName();
		type = Constant.type[0];
	}
	private String courseId;
	private String courseTitle;
	private ArrayList<Lecture> preCourses;
	private ArrayList<E_EducationMaterial> courseContents;
	private String techTime;//学时
	private String credits;//学分
	private String difficulty;//难易度
	private ArrayList<Book> referenceBooks;
	private ArrayList<Book> textBooks;
	private People teacher;
}
