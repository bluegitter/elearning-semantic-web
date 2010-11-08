package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class DataFactory {
	static String url = "jdbc:mysql://192.168.8.86:3306/elearning";        
    static String username = "ms";
    static String password = "ms";

	public static void main(String [] args){
		insertUsers(); 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection)DriverManager.getConnection(url, username, password);        
			Statement sql_statement = (Statement) con.createStatement();
			
            
            con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch( SQLException sqle){
			sqle.printStackTrace();
		}

	}
	public static void getConnection(){
		
	}
	
	public static void insertUsers(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection)DriverManager.getConnection(url, username, password);        
			Statement sql_statement = (Statement) con.createStatement();
			
			ArrayList<String> users= getElearnerDataSQL();
			for(String user:users){
				sql_statement.execute(user);
			}
			System.out.println("insert users successfully");
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch( SQLException sqle){
			sqle.printStackTrace();
		}
	}
	private static ArrayList<String> getElearnerDataSQL(){
		ArrayList <String> users = new ArrayList<String>();
		String start = "insert into elearner values(";
		String end =")";
		
		users.add(start+"'eid0001','ms','grade two','ms'"+end);
		users.add(start+"'eid0002','wsg','grade two','wsg'"+end);
		users.add(start+"'eid0003','ghh','grade two','ghh'"+end);
		
		return users;
	}
	
}
