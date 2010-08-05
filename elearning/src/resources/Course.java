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
	private String techTime;//ѧʱ
	private String credits;//ѧ��
	private String difficulty;//���׶�
	private ArrayList<Book> referenceBooks;
	private ArrayList<Book> textBooks;
	private Person teacher;
}
