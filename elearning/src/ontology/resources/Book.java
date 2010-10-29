package ontology.resources;

import ontology.people.People;
import ontology.resources.classify.LearningMaterial;

public class Book extends LearningMaterial{
	private String bookId;
	private String bookName;
	private String ISBN;
	private People author;
	private String publisher;
}
