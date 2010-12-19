package db;

import junit.framework.TestCase;
import ontology.people.ELearner;

public class ElearnerTest extends TestCase{
	public void setUp(){
		elearner = new ELearner();
	}
	public void addELearnerTest(){
		elearner.setId("eltest");
	}
	private ELearner elearner;
}
