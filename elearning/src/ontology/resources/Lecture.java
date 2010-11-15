package ontology.resources;

import java.util.ArrayList;

import ontology.people.People;


import util.Constant;



public class Lecture extends EResource{
	public Lecture(){
	}
	private String courseId;
	private String courseTitle;
	private ArrayList<Lecture> preCourses;
	private String techTime;
	private String credits;
	private String difficulty;
	private ArrayList<Book> referenceBooks;
	private ArrayList<Book> textBooks;
	private People teacher;
}
