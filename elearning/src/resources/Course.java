package resources;

import java.util.ArrayList;

import user.Person;

import constant.Constant;


public class Course extends Resource{
	public Course(){
		courseId = super.getResourceId();
		courseTitle = super.getResourceName();
		type = Constant.type[0];
	}
	private String courseId;
	private String courseTitle;
	private ArrayList<Course> preCourses;
	private ArrayList<CourseContent> courseContents;
	private String techTime;//学时
	private String credits;//学分
	private String difficulty;//难易度
	private ArrayList<Book> referenceBooks;
	private ArrayList<Book> textBooks;
	private Person teacher;
}
