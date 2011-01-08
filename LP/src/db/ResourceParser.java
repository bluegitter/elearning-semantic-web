package db;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import jena.OwlFactory;
import jena.impl.ELearnerModelImpl;
import ontology.EConcept;
import ontology.resources.ISCB_Resource;
import com.hp.hpl.jena.ontology.OntModel;
import com.mysql.jdbc.Connection;

import exception.jena.IndividualNotExistException;

public class ResourceParser {
	public ResourceParser(){
		emi = new ELearnerModelImpl();
	}
	public ResourceParser(File file){
		emi = new ELearnerModelImpl(file);
	}
	public boolean writeToFile(File file){
		return emi.writeToFile(file);
	}
	public OntModel getOntModel(){
		return emi.getOntModel();
	}
	public HashMap<String,EConcept> getDataStructureResrouces() throws IndividualNotExistException{
		Connection con = DataFactory.getConnection();
		HashMap<String,EConcept> concepts = new HashMap<String,EConcept>();
		try{
			Statement st = con.createStatement();
			//String sql = "select course_resource_id,course_resource_title,course_resource_location,concept1,concept2 from courseresourceinfo";
			String sql = "select course_resource_id,资源标题,文件路径,所属知识点1,所属知识点2 from courseresourceinfo";
			ResultSet rs =  st.executeQuery(sql);
			String idprefix = "cid";
			ArrayList<ISCB_Resource> resources = new ArrayList<ISCB_Resource>();
			EConcept rootConcept = emi.getEConcept("CMP.cf.2");
			while(rs.next()){
				ISCB_Resource resource = new ISCB_Resource();
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
					emi.addPropertyIsSonOf(rootConcept,conceptTemp);
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
			//System.out.println(emi.getEResourcesByEConcept(emi.getEConcept("cid1")));
			rs.close();
			st.close();
			con.close();
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return concepts;
	}
	public ArrayList<EConcept> getBasicEConcepts() throws IndividualNotExistException {
		Connection con = DataFactory.getConnection();
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		try{
			Statement st = con.createStatement();
			String sql = "select concept_id,concept_name from econcept";
			ResultSet rs =  st.executeQuery(sql);
			EConcept rootConcept = emi.getRootConcept();
			
			HashMap<Integer ,EConcept> tempConcepts = new HashMap<Integer,EConcept> ();
			tempConcepts.put(-1, rootConcept);
			while(rs.next()){
				String cid = rs.getString("concept_id");
				String cname = rs.getString("concept_name");
				EConcept concept = new EConcept(cid,cname);
				emi.addEConcept(concept);
				concepts.add(concept);
				int level = countDot(cid);
				EConcept father  = rootConcept;
				if(level != 0){
					father = tempConcepts.get(level);
				}
				emi.addPropertyIsSonOf(father, concept);
				tempConcepts.put((level+1), concept);
			}
			//System.out.println(emi.getAllEConcepts().size());
			System.out.println(concepts.size()+" concepts added\t");
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
	public static void main(String [] args) throws IOException, IndividualNotExistException{
		File file = new File("test\\owl\\conceptsAndresource_RDF-XML.owl");
		ResourceParser rp = new ResourceParser();
		rp.getBasicEConcepts();
		rp.getDataStructureResrouces();
		System.out.println("beginto write");
		OwlOperation.writeOwlFile(rp.getOntModel(), file);
	}
	private ELearnerModelImpl emi;
	
}
