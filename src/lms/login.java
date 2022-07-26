package lms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class login {

	
	String username;
	String password;
	
	
	
	static void loginScreen(String username, String password) {
		
		
		
		
		Statement st = null;
		ResultSet rst = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			
			
			Conn conObj = new Conn();
			
			st = conObj.con.createStatement();
			
			rst = st.executeQuery("select * FROM admin where username = '" + username + "'");
			
			
			if(rst.next()) {
			
				if(password.equals(rst.getString(2))) {
					
					System.out.println("\n\nLogin successful\n");
					
					 LMS lms = new LMS();
					 lms.mainClass();
						
					
					
				}else {
					
					System.out.println("\nLogin Failed\n");
					
				}
			
			}else {
				System.out.println("Wrong Username or Password");
			}
			
				conObj.con.close();
				st.close();
			
			}
			catch(SQLException sqe) {
				
				sqe.printStackTrace();
				
			}
			catch(ClassNotFoundException cnfe) {
				
				cnfe.printStackTrace();
				
			}catch(Exception e) {
				
				e.printStackTrace();
				
			}
		
		
	
		
	}//end of login method
	
	
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\n\n**  Welcome to Library Management System  **");	
		System.out.println("____________________________________________\n");	
		boolean login=false;
		System.out.println("Please Login to Proceed");
		
//		while(login != true) {
			
			
			
			System.out.print("Enter Username > ");
			String username = br.readLine();
			
			System.out.print("Enter password > ");
			String password = br.readLine();
			
			loginScreen(username, password);
			
		
		
		
	}
	
}
