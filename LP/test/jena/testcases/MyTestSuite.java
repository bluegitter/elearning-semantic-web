package jena.testcases;

import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite{
	public static void main(String [] args){
		junit.textui.TestRunner.run(MyTestSuite.suite());
	}
	public static TestSuite suite() { 
	    TestSuite suite= new TestSuite(); 
	    suite.addTestSuite(ELearnerModelTest.class); 
	    return suite; 
	}

}
