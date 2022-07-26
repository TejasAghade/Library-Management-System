package lms;

import java.sql.*;

public class Conn {

	// Declaration of variables
	public Connection con = null;
	
	// declaration for database connectivity
	public String url = "jdbc:mysql://localhost:3306/LMS";
	public String username = "root";
	public String password = "";
	
	Conn() {
		try {
			
			// load and register driver
			Class.forName("com.mysql.cj.jdbc.Driver");
						
			//create connection object			
			con = DriverManager.getConnection(url, username, password);
					
			
			
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}

	}
	
}
