package db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
		System.out.println("all concepts size:"+emi.getAllEConcepts().size());
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
			
			String con1=(String)rs.getString("concept1");
			EConcept conceptTemp = null;
			boolean isContain1 = concepts.containsKey(con1);
			
			if(!isContain1){
				conceptTemp = new EConcept(idprefix+(concepts.size()+1),con1);
				concepts.put(con1,conceptTemp);
				emi.addEConcept(conceptTemp);
				emi.addPropertyIsSonOf(emi.getRootConcept(),conceptTemp);
			}else{
				conceptTemp = concepts.get(con1);
			}
			String con2=(String)rs.getString("concept2");
			boolean isContain2 = concepts.containsKey(con2);
			EConcept temp2 = null;
			if(!isContain2){
				temp2 = new EConcept(idprefix+(concepts.size()+1),con1);
				concepts.put(con2,temp2);
				emi.addEConcept(temp2);
				emi.addPropertyIsSonOf(conceptTemp,temp2);
			}else{
				temp2 = concepts.get(con2);
			}
			emi.addPropertyIsResourceOfC(resource, conceptTemp);
			emi.addPropertyIsResourceOfC(resource, temp2);
			
			System.out.println(con1+"\t"+con2);
		}
		rs.close();
		st.close();
		con.close();
		return concepts;
	}
	 
	public static  void main(String []args) throws Exception{
		ResourceParser rp = new ResourceParser();
		rp.getAllEConcepts();
	}
	private ELearnerModelImpl emi;
	private Connection con;
	private String userOwl = Constant.userOwlFile;
	private String owl = Constant.OWLFile;
	private String rule = Constant.RulesFile;
	
}
