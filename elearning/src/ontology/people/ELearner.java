package ontology.people;
import java.util.ArrayList;

import ontology.resources.Certification;
import ontology.resources.Lecture;

public class ELearner extends People{
	private String learnerId;
	private String learnerName;
	private ArrayList<Lecture> finishedCourse;
	private String grade;
	private ArrayList<Certification> certifications;
}
