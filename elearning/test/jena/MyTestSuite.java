package jena;

import junit.framework.TestSuite;

public class MyTestSuite extends TestSuite{
	public static void main(String [] args){
		junit.textui.TestRunner.run(MyTestSuite.suite());
	}
	public static TestSuite suite() { 
	    TestSuite suite= new TestSuite(); 
	    suite.addTestSuite(ELearnerModelTest.class); 
	    suite.addTestSuite(ELearnerModelQueryTest.class); 
	    suite.addTestSuite(ELearnerRuleModelTest.class); 
	    return suite; 
	}




}
