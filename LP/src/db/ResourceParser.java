package db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import jena.impl.ELearnerModelImpl;

import ontology.EConcept;
import ontology.resources.EResource;
import util.Constant;

import com.mysql.jdbc.Connection;

public class ResourceParser {
	public ResourceParser(){
		emi = new ELearnerModelImpl();
	}
	public boolean writeToFile(File file){
		return emi.writeToFile(file);
	}
	public HashMap<String,EConcept> getAllEConcepts() throws Exception{
		con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "select course_resource_id,course_resource_title,course_resource_location,concept1,concept2 from courseresourceinfo";
		ResultSet rs =  st.executeQuery(sql);
		String idprefix = "cid";
		HashMap<String,EConcept> concepts = new HashMap<String,EConcept>();
		ArrayList<EResource> resources = new ArrayList<EResource>();
		while(rs.next()){
			EResource resource = new EResource();
			resource.setDifficulty("easy");
			resource.setRid(rs.getString("course_resource_id"));
			resource.setName(rs.getString("course_resource_title"));
			resource.setFileLocation(rs.getString("course_resource_location"));
			resources.add(resource);
			emi.addEResource(resource);
			System.out.println("addRes:"+emi.getEResource(resource.getRid()));
			String con1=(String)rs.getString("concept1");
			EConcept conceptTemp = null;
			boolean isContain1 = concepts.containsKey(con1);
			
			if(!isContain1){
				conceptTemp = new EConcept(idprefix+(concepts.size()+1),con1);
				concepts.put(con1,conceptTemp);
				emi.addEConcept(conceptTemp);
				System.out.println("addOne:"+emi.getEConcept(conceptTemp.getCid()));
				emi.addPropertyIsSonOf(emi.getRootConcept(),conceptTemp);
			}else{
				conceptTemp = concepts.get(con1);
			}
			emi.addPropertyIsResourceOfC(resource, conceptTemp);
			
			String con2=(String)rs.getString("concept2");
			if(con2!=null){
				boolean isContain2 = concepts.containsKey(con2);
				EConcept temp2 = null;
				if(!isContain2){
					temp2 = new EConcept((idprefix+(concepts.size()+1)),con2);
					concepts.put(con2,temp2);
					emi.addEConcept(temp2);
					System.out.println("addTwo:"+emi.getEConcept(temp2.getCid()));
					emi.addPropertyIsSonOf(conceptTemp,temp2);
				}else{
					temp2 = concepts.get(con2);
				}
				emi.addPropertyIsResourceOfC(resource, temp2);
			}
		}
		emi.writeToFile(new File(Constant.userOwlFile));
		
		//System.out.println(emi.getAllEResources().size());
		System.out.println(emi.getEResourcesByEConcept(emi.getEConcept("cid1")));
//		Collection<EConcept> cs = concepts.values();
//		for(EConcept c :cs){
//			System.out.println(c);
//		}
//		//System.out.println(emi.getAllEConcepts());
//		System.out.println(emi.getEConcept("cid1"));
		rs.close();
		st.close();
		con.close();
		return concepts;
	}
	 
	public static void main(String []args) throws Exception{
		ResourceParser rp = new ResourceParser();
		rp.getAllEConcepts();
      //  rp.writeToFile(new File( Constant.userOwlFile));
	}
	private ELearnerModelImpl emi;
	private Connection con;
	
}
