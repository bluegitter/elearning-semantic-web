package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import util.Constant;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
/*****************************************************************
 * DataFactory gives the Database Connection.
 * 
 * @author William
 *
 */
public class DataFactory {
	private static String url = Constant.DBURL;
	private static String username = Constant.DBUSER;
	private static String password = Constant.DBPASSWORD;

	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = (Connection)DriverManager.getConnection(url, username, password);        
			System.out.println("connect to db successfully");
			return con;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch( SQLException sqle){
			sqle.printStackTrace();
			return null;
		}
	}
	
	public static void insertResourceType(){
		try{
			Connection conn= getConnection();
			Statement stm = (Statement)conn.createStatement();
			String start = "insert into resourcetype values(";
			String end =")";
			
			for(int i = 0;i<Constant.ERESOURCE_TYPES.length;i++){
				String sql =start+"'rtid"+(i+1)+"',"+"'"+Constant.ERESOURCE_TYPES[i]+"'"+end;
				stm.execute(sql);
			}
			for(int i = 0;i<Constant.EDUCATION_TYPES.length;i++){
				String sql =start+"'etid"+(i+1)+"',"+"'"+Constant.EDUCATION_TYPES[i]+"'"+end;
				stm.execute(sql);
			}
		}catch( SQLException sqle){
			sqle.printStackTrace();
		}
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
			sql_statement.close();
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
