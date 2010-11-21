package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class DbOperation {
	/************************************************
	 * validate whether the userId and userPassword is legal
	 * @param userId
	 * @param userPassword
	 * @return true if the elearner pass the validation, else return false
	 * @throws Exception
	 */
	public boolean login(String userId,String userPassword) throws Exception{
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "select password from elearner where elearner_id='"+userId+"'";
		ResultSet rs =  st.executeQuery(sql);
		while(rs.next()){
			String pas=(String)rs.getString("password");
			System.out.println(pas);
			if(pas.equals(userPassword)){
				return true;
			}
		}
		return false;
	}
	/*****************************************************************
	 * Save the blob data to the database, save the file to the database
	 * @param rid
	 * @param file
	 * @return true/false
	 * 
	 */
	public boolean saveFile(String rid, File file){
		try{
			Connection con = DataFactory.getConnection();
			FileInputStream fin = new FileInputStream(file);
	        String sql = "insert into resourcecontent values(?,?)";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1,rid);
	        stmt.setBinaryStream(2,fin);
	        stmt.executeUpdate();
	        stmt.close();
	        fin.close();
	        con.close();
	        System.out.println("upload the file successfully");
	        return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//read the blob data from the database, and write it to the File
	public boolean getFile(String rid,File file){
		byte[] Buffer = new byte[4096]; 
		try { 
			Connection conn = DataFactory.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement("select * from resourcecontent where resource_id=?"); 
			pstmt.setString(1,rid);
			ResultSet rs = pstmt.executeQuery(); 
			while(rs.next()){
				FileOutputStream fos = new FileOutputStream(file); 
				InputStream is = rs.getBinaryStream("resource_content"); 
				int size = 0; 
				while((size = is.read(Buffer)) != -1) { 
					fos.write(Buffer,0,size); 
				} 
				fos.close();
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			return true;
		}catch(Exception e) { 
			System.out.println("[OutPutFile error : ]" + e.getMessage()); 
			return false;
		} 
	}
}
