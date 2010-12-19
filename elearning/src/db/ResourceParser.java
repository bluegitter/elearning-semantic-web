package db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ontology.EConcept;

import com.mysql.jdbc.Connection;

public class ResourceParser {
	public ResourceParser(){
	}
	public static ArrayList<EConcept> getAllEConcepts() throws Exception{
		ArrayList<EConcept> concepts = new ArrayList<EConcept>();
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "select concept1,concept2 from courseresourceinfo";
		ResultSet rs =  st.executeQuery(sql);
		ArrayList<String> c = new ArrayList<String>();
		while(rs.next()){
			String con1=(String)rs.getString("concept1");
			String con2=(String)rs.getString("concept2");
			c.add(con1);
			c.add(con2);
			System.out.println(con1+"\t"+con2);
		}
		System.out.println(c.size());
		rs.close();
		st.close();
		con.close();
		return concepts;
	}
	public static boolean addConcept(String concept){
		return false;
	}
	public static boolean addConcept(){
		return false;
	}
	public static void main(String []args) throws Exception{
		ResourceParser.getAllEConcepts();
	}
}
