package db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.resources.EResource;
import com.hp.hpl.jena.ontology.OntModel;
import com.mysql.jdbc.Connection;

public class ResourceParser {
	public ResourceParser(){
		emi = new ELearnerModelImpl();
	}
	public boolean writeToFile(File file){
		return emi.writeToFile(file);
	}
	public OntModel getOntModel(){
		return emi.getOntModel();
	}
	public HashMap<String,EConcept> getDataStructureResrouces(){
		Connection con = DataFactory.getConnection();
		HashMap<String,EConcept> concepts = new HashMap<String,EConcept>();
		try{
			Statement st = con.createStatement();
			//String sql = "select course_resource_id,course_resource_title,course_resource_location,concept1,concept2 from courseresourceinfo";
			String sql = "select course_resource_id,资源标题,文件路径,所属知识点1,所属知识点2 from courseresourceinfo";
			ResultSet rs =  st.executeQuery(sql);
			String idprefix = "cid";
			ArrayList<EResource> resources = new ArrayList<EResource>();
			while(rs.next()){
				EResource resource = new EResource();
				resource.setDifficulty("easy");
				resource.setRid(rs.getString("course_resource_id"));
				resource.setName(rs.getString("资源标题"));
				resource.setFileLocation(rs.getString("文件路径"));
				resources.add(resource);
				emi.addEResource(resource);
				//System.out.println("addRes:"+emi.getEResource(resource.getRid()));
				String con1=(String)rs.getString("所属知识点1");
				EConcept conceptTemp = null;
				boolean isContain1 = concepts.containsKey(con1);
				
				if(!isContain1){
					conceptTemp = new EConcept(idprefix+(concepts.size()+1),con1);
					concepts.put(con1,conceptTemp);
					emi.addEConcept(conceptTemp);
					//System.out.println("addOne:"+emi.getEConcept(conceptTemp.getCid()));
					emi.addPropertyIsSonOf(emi.getRootConcept(),conceptTemp);
				}else{
					conceptTemp = concepts.get(con1);
				}
				emi.addPropertyIsResourceOfC(resource, conceptTemp);
				
				String con2=(String)rs.getString("所属知识点2");
				if(con2!=null){
					boolean isContain2 = concepts.containsKey(con2);
					EConcept temp2 = null;
					if(!isContain2){
						temp2 = new EConcept((idprefix+(concepts.size()+1)),con2);
						concepts.put(con2,temp2);
						emi.addEConcept(temp2);
						//System.out.println("addTwo:"+emi.getEConcept(temp2.getCid()));
						emi.addPropertyIsSonOf(conceptTemp,temp2);
					}else{
						temp2 = concepts.get(con2);
					}
					emi.addPropertyIsResourceOfC(resource, temp2);
				}
			}
			System.out.println(concepts.size()+" concepts added\t"+resources.size()+" resources added");
			System.out.println(emi.getEResourcesByEConcept(emi.getEConcept("cid1")));
			rs.close();
			st.close();
			con.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return concepts;
	}
	public ArrayList<EConcept> getBasicEConcepts() {
		Connection con = DataFactory.getConnection();
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		try{
			Statement st = con.createStatement();
			String sql = "select concept_id,concept_name from econcept";
			ResultSet rs =  st.executeQuery(sql);
			EConcept currentRootConcept = emi.getRootConcept();
			EConcept lastConcept = emi.getRootConcept();
			EConcept rootConcept = emi.getRootConcept();
			int currentLevel = 0;
			while(rs.next()){
				String cid = rs.getString("concept_id");
				String cname = rs.getString("concept_name");
				EConcept concept = new EConcept(cid,cname);
				emi.addEConcept(concept);
				concepts.add(concept);
				int level = countDot(cid);
				//add new son
				if(currentLevel<level){
					currentRootConcept = lastConcept;
					emi.addPropertyIsSonOf(currentRootConcept, concept);
					currentLevel = level;
				}
				//add leaf member
				if(currentLevel ==level){
					emi.addPropertyIsSonOf(currentRootConcept, concept);
				}
				//add new son of the root concept
				if(currentLevel > level){
					currentRootConcept = rootConcept;
					emi.addPropertyIsSonOf(currentRootConcept, concept);
					currentLevel = level;
				}
				lastConcept = concept;
			}
			System.out.println(emi.getAllEConcepts().size());
			System.out.println(concepts);
			rs.close();
			st.close();
			con.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		
		return concepts;
	}
	private int countDot(String s){
		char cs []= s.toCharArray();
		int i = 0;
		for(char c :cs){
			if(c=='.'){
				i++;
			}
		}
		return i;
	}
	
	private ELearnerModelImpl emi;
	
}
