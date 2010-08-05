package user;
import java.util.ArrayList;
import resources.Certification;
import resources.Course;

public class Learner extends Person{
	private String learnerId;
	private String learnerName;
	private ArrayList<Course> finishedCourse;
	private String grade;
	private ArrayList<Certification> certifications;
}
