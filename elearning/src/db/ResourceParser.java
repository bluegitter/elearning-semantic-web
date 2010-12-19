package db;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import jena.ELearnerModelImpl;

import ontology.EConcept;
import util.Constant;

import com.mysql.jdbc.Connection;

public class ResourceParser {
	public ResourceParser(){
		
		emi = new ELearnerModelImpl(owl,rule);
	}
	public boolean writeToFile(File file){
		return emi.writeToFile(file);
	}
	public ArrayList<EConcept> getAllEConcepts() throws Exception{
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "select concept1,concept2 from courseresourceinfo";
		ResultSet rs =  st.executeQuery(sql);
		String idprefix = "cid";
		System.out.println("all concepts size:"+emi.getAllEConcepts().size());
		ArrayList<String> record = new ArrayList<String>();
		while(rs.next()){
			String con1=(String)rs.getString("concept1");
			EConcept conceptTemp = null;
			boolean isContain1 = record.contains(con1);
			if(!isContain1){
				record.add(con1);
				conceptTemp = new EConcept(idprefix+(concepts.size()+1),con1);
				concepts.add(conceptTemp);
				emi.addEConcept(conceptTemp);
				emi.addPropertyIsSonOf(emi.getRootConcept(),conceptTemp);
			}else{
				conceptTemp = new EConcept(idprefix+(concepts.size()),con1);
			}
			String con2=(String)rs.getString("concept2");
			boolean isContain2 = record.contains(con2);
			if(!isContain2){
				record.add(con2);
				EConcept temp = new EConcept(idprefix+(concepts.size()+1),con1);
				concepts.add(temp);
				emi.addEConcept(temp);
				emi.addPropertyIsSonOf(conceptTemp,temp);
			}
			System.out.println(con1+"\t"+con2);
		}
		emi.writeToFile(new File(newOwl));
		System.out.println("all concepts new size:"+emi.getAllEConcepts().size());
		System.out.println("new Concepts Size:"+concepts.size());
		System.out.println("hah\n"+emi.getSonConcepts(emi.getRootConcept()));
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
	private String newOwl = Constant.newOwlFile;
	private String owl = Constant.OWLFile;
	private String rule = Constant.RulesFile;
	
}
