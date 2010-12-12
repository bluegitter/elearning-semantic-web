package db;

import java.io.File;
import java.sql.Date;

import junit.framework.TestCase;

import util.Constant;

public class OwlOperationTest extends TestCase{
	public void testSaveOwlFile(){
		File file = new File(Constant.OWLFile);
		Date d1= new Date(System.currentTimeMillis());
		op.saveOwlFile(id,file,d1);
		Date d2 = op.getOwlFileDate(id);
		assertTrue(d1.toString().equals(d2.toString()));
	}
	public void testGetOwlFileDate(){
		Date d1 = op.getOwlFileDate(id);
		Date d2 = new Date(System.currentTimeMillis());
		assertTrue(d1.toString().equals(d2.toString()));
	}
	public void testRemoveOwlFile(){
		assertTrue(op.removeOwlFile(id));
	}
	public void setUp(){
		op = new OwlOperation();
	}
	private OwlOperation op;
	private String id="owlfile5";
	
}
