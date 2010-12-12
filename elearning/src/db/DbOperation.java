package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.IllegallPersonException;

import ontology.people.ELearner;
public class DbOperation {
	/*********************************************************************
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
	public boolean login(ELearner el) throws Exception {
		return login(el.getId(),el.getPassword());
	}
	public boolean addELearner(ELearner el) throws Exception{
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "insert into elearner values('"+el.getId()+"','"+el.getName()+"','"+el.getGrade()+"','"+el.getPassword()+"')";
		st.execute(sql);
		System.out.println("add ELearner to DB Successfully");
		return true;
	}
	public boolean removeELearner(ELearner el) throws Exception{
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		if(login(el)){
			String sql = "delete from elearner where elearner_id ='"+el.getId()+"'";
			st.execute(sql);
			System.out.println("remove ELearner to DB Successfully");
		}else{
			System.out.println("user is and password are not matched ");
		}
		return true;
	}
	public boolean updateELearner(ELearner el) throws Exception{
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		if(login(el)){
			String sql = "update elearner set people_name='a',grade='a',password ='a' where elearner_id ='"+el.getId()+"'";
			st.execute(sql);
			System.out.println("update ELearner to DB Successfully");
			return true;
		}else{
			System.out.println("user is and password are not matched ");
			return false;
		}
	}
	public ELearner getELearner(String userId,String password) throws Exception{
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String sql = "select * from elearner where elearner_id='"+userId+"'";
		ResultSet rs =  st.executeQuery(sql);
		ELearner el = new ELearner(userId,password);
		while(rs.next()){
			String pas=(String)rs.getString("password");
			if(pas.equals(password)){
				el.setName((String)rs.getString("people_name"));
				el.setGrade((String)rs.getString("grade"));
			}else{
				throw new IllegallPersonException("user is not a register user");
			}
		}
		return el;
	}
	
	public boolean hasELearner(ELearner el) throws SQLException {
		Connection con = DataFactory.getConnection();
		Statement st = con.createStatement();
		String uid = el.getId();
		String sql = "select elearner_id from elearner where elearner_id='"+uid+"'";
		ResultSet rs =  st.executeQuery(sql);
		while(rs.next()){
			String id=(String)rs.getString("elearner_id");
			if(id.equals(uid)){
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
