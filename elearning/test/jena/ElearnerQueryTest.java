package jena;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class ElearnerQueryTest extends TestCase{
	public void setUp(){
		eq = new ElearnerQuery();
	}
	public void testgetAllConceptNames() throws IOException{
		//if the data is changed,change the num to the number of the concepts
		int num = 31;
		ArrayList<String> concepts = eq.getAllConceptName();
		assertTrue(concepts.size()==num);
	}
	private ElearnerQuery eq;
}
