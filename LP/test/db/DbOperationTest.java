package db;

import junit.framework.TestCase;
import ontology.people.ELearner;

public class DbOperationTest extends TestCase{
	public void testAddELearner() throws Exception{
		assertTrue(db.addELearner(elUp));
	}
	public void testGetELearner() throws Exception{
		ELearner el2 =db.getELearner(elGet.getId(), elGet.getPassword());
		if(el2.getGrade().equals(elGet.getGrade())){
			if(el2.getName().equals(elGet.getName())){
				assertTrue(true);
			}
		}
	}
	public void testUpdateELearner()throws Exception{
		elUp.setName("UpdateName");
		elUp.setGrade("UpdateGrade");
		boolean b = db.updateELearner(elUp);
		if(b){
			ELearner temp = db.getELearner(elUp.getId(), elUp.getPassword());
			assertTrue(temp.getName().equals(elUp.getName()));
			assertTrue(temp.getGrade().equals(elUp.getGrade()));
		}else{
			assertFalse(false);
		}
	}
	public void testRemoveElearner()throws Exception{
		db.removeELearner(elRemove);
		assertFalse(db.hasELearner(elRemove));
	}
	
	public void setUp()throws Exception{
		db = new DbOperation();
		elRemove = new ELearner("elRemoveID","elRemovePass");
		elRemove.setName("elRemoveName");
		elRemove.setGrade("elRemoveGrade");
		
		elUp = new ELearner("elUpId","elUpPassword");
		elUp.setName("elUpName");
		elUp.setGrade("elUpGrade");
		
		elGet = new ELearner("elGetId","elGetPassword");
		elGet.setName("elGetName");
		elGet.setGrade("elGetGrade");
		
		db.addELearner(elGet);
	}
	public void tearDown()throws Exception{
		db.removeELearner(elUp);
		db.removeELearner(elGet);
	}
	private DbOperation db;
	private ELearner elRemove;
	private ELearner elUp;	
	private ELearner elGet;
}
