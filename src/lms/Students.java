package lms;
import java.io.*;
import java.sql.*;

public class Students{
	

	String StudentId;
	int StudentRollNo;
	String StudentName;
	String StudentDepartment;
	String StudentContact;
	String exit;
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	Conn conObj = new Conn();
	
	void setStudentInfo() {
		// clearConsole.CConsole();
		
		System.out.println("**  Library Management System  **\n");
		System.out.println("* Enter Student Details *\n");
		
		
		try {
			
		System.out.println("Enter Student Roll No");
		StudentRollNo = Integer.parseInt(br.readLine());
		

		
		System.out.println("Enter Student Id");
		StudentId = br.readLine();
		
		if(checkExistance(StudentId)) {
			System.out.println("Student Already Exist\n\n");
			return;
		}
		
		System.out.println("Enter Student Name");
		StudentName = br.readLine();
		
		System.out.println("Enter Student Department");
		StudentDepartment = br.readLine();
		
		System.out.println("Enter Student Phone Number");
		StudentContact = br.readLine();
		
		// calling add student function to add student into database
		AddStudent();
		
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
		
	}
	
	
	Boolean checkExistance(String StudentId) {
		
		
//		use  ="'"+ string + "'"
		
		String query = "select * from students where S_id ='"+ StudentId +"'";
		
		try {

			
			Statement st = conObj.con.createStatement();
			ResultSet rst = st.executeQuery(query);
		
		if(rst.next()) {
			
			return true;
			
		}else {
			
			return false;
			
		}
		
		
		}catch(SQLException sqe) {
			
			sqe.printStackTrace();
			
			
		}
		
		return true;
	}
	
	
	
	//method for adding students to library
	void AddStudent() {
		

		
		try {
			
		
			//inserting values into database
			
			PreparedStatement pst = conObj.con.prepareStatement("insert into students values(?, ?, ?, ?, ?)");
			
			pst.setString(1, StudentId);
			pst.setInt(2, StudentRollNo);
			
			pst.setString(3, StudentName);
			pst.setString(4, StudentDepartment);
			pst.setString(5, StudentContact);
			
			
			// executing query
			
			int result = pst.executeUpdate();
			
			
			//checking if the record is inserted or not
			if(result>0) {
				
				System.out.println("\nStudent Added Sucessfully");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				
			}else {
				
				System.out.println("failed to add");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				
			}
			
			
		}
		catch(SQLException sqe) {
			sqe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
void getcount() {
		
		int count=0;
		int mba = 0;
		int mca = 0;
		
		try {
			
			Statement stmt = conObj.con.createStatement();
			Statement stmt1 = conObj.con.createStatement();
			Statement stmt2 = conObj.con.createStatement();
			
			String query = "SELECT count(*) from students";
			
			String query1 = "SELECT count(*) from students where S_dept like '%MCA%'";
			
			String query2 = "SELECT count(*) from students where S_dept like '%MBA%'";
			
			
			
			ResultSet rst = stmt.executeQuery(query);
			
			ResultSet rst1 = stmt1.executeQuery(query1);
			
			ResultSet rst2 = stmt2.executeQuery(query2);
			
			
			while (rst.next()){
				
				count = rst.getInt(1);
			}
			
			while (rst1.next()){
				
				mca = rst1.getInt(1);
			}
			
			while (rst2.next()){
				
                mba = rst2.getInt(1);
            }

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Total Students : " + count + " | Total Students of MCA : "+ mca +" | Total Students of MBA : " + mba +"\n");
		
		
	}
	
	
	// method to show student
	
	void DisplayStudents(int number) throws IOException {
		
		// clearConsole.CConsole();
		
//		query to fetch limited records
		
		String query = "select * from students LIMIT "+ number;
		
		if(number== 0) {
			
			
			query = "select * from students";
			
		}
		
		
		try {
			
		Statement st = conObj.con.createStatement();
		ResultSet rst = st.executeQuery(query);
		
		// showing student detail
		System.out.println("**  Library Management System  **\n");
		
		System.out.println("* Student Details *\n\n");
		getcount();
		
		System.out.println("___________________________________________________________________");
		System.out.println("___________________________________________________________________ \n");
		while(rst.next()) {
			System.out.println("\tStudent Name : " + rst.getString(3) +"\n"+
							   "\tStudent Library Id : "+ rst.getString(1) +"\n"+
							   "\tStudent Roll No : " +  rst.getInt(2) +"\n"+
							   "\tStudent Department : "+ rst.getString(4) +"\n"+
							   "\tStudent Phone Number : " + rst.getString(5));
			System.out.println("___________________________________________________________________");
			System.out.println("___________________________________________________________________ \n\n");
		}
		
		getcount();
		System.out.print("Press Enter to Continue\n");
		 exit = br.readLine();
		
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}
	}
	
	
	
	void deleteStudent(String sId) throws IOException {
		
		// clearConsole.CConsole();
		
		
		
		System.out.println("**  Library Management System  **\n");
		
			if(!checkExistance(sId)) {
				
				System.out.println("Student Does not Exist");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				return;
				
			}
			

		
			try {
				
				System.out.println("* Current Details of Student *\n");
				
				SearchStudent(sId);
								
				System.out.println("Are you sure You wan to delete this student Enter y/n");
				char confirm = (char) br.read();
				
				
				if(confirm =='y') {
				
				PreparedStatement pst = conObj.con.prepareStatement("delete from students where S_id ='" +sId+"'");
				
				int result = pst.executeUpdate();
				
					if(result>0) {
						
						System.out.println("Student deleted Successfully");
						System.out.print("Press Enter to Continue");
						 exit = br.readLine();
						
					}else {
						
						System.out.println("failed to delete");
						System.out.print("Press Enter to Continue");
						exit = br.readLine();
						
					}
				
				}else {
					System.out.println("Operation cancelled");
					return;
				}
				
				
			}catch(SQLException sqe) {
				sqe.printStackTrace();
			}
	
		
		
	}
	
	
	
	void updateQuery(String query) throws SQLException, IOException {
		
		
			
			PreparedStatement pst = conObj.con.prepareStatement(query);
			
			int result = pst.executeUpdate();
			
			
			if(result>0) {
				
				System.out.println("\nDetails of Student Updated Successfully");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				
			}else {
				
				System.out.println("failed to Update Details of Student");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
			}
			
	
		
	
	}
	
	
	void updateStudent(String sId){
		// clearConsole.CConsole();
		
			this.StudentId = sId;
			int option;
			
			if(checkExistance(sId)) {
			
				
				
			try {
			
				System.out.println("**  Library Management System  **\n");
				System.out.println("* Current Details of Student *\n");
				
				SearchStudent(sId);
				
				System.out.println("\t* What you want to update *\n\n"
						+ "\t1] Update Name of the Student\n"
						+ "\t2] Update Student Department\n"
						+ "\t3] Updtae Student Contact\n"
						+ "\t4] Cancel \n\n");
				
				System.out.print("Enter Option > ");
				option = Integer.parseInt(br.readLine());
				
				switch (option) {
				
				case 1: {
										
					System.out.println("Enter Name for Student");
					StudentName = br.readLine();
					
					updateQuery("UPDATE students set S_Name ='"+StudentName+"' where S_id ='"+sId+"'");
					break;
					
				}
				
				case 2: {
					
					System.out.println("Enter Department for Student");
					StudentDepartment = br.readLine();
					
					updateQuery("UPDATE students set S_dept ='"+StudentDepartment+"' where S_id ='"+sId+"'");
					break;
				}
				
				case 3: {
					
					System.out.println("Enter Contact for Student");
					StudentContact = br.readLine();
					
					updateQuery("UPDATE students set S_Contact ='"+StudentContact+"' where S_id ='"+sId+"'");
					break;
					
				}

				default:

					System.out.println("Invalid option\n\n");
					break;
					
				}
				
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				
			}
			catch(IOException e) {
				
				e.printStackTrace();
				
			}catch(SQLException sqe) {
				
				sqe.printStackTrace();
				
			}
			
			}else {
				System.out.println("Student Does Not Exist");
			}
			
		}
		
	
	void SearchStudent(String sId){
		
		// clearConsole.CConsole();
		try {
			
			if(checkExistance(sId)) {
				
				String query = "select * from students where S_id = '"+sId+"'";
				
				
				Statement st = conObj.con.createStatement();
				ResultSet rst = st.executeQuery(query);
				
				// showing student detail
				System.out.println("**  Library Management System  **\n");
				
				System.out.println("* Student Details *\n\n");
					rst.next();
					System.out.println("___________________________________________________________________");
					System.out.println("___________________________________________________________________ \n");
						System.out.println("\tStudent Name : " + rst.getString(3) +"\n"+
										   "\tStudent Library Id :"+ rst.getString(1) +"\n"+
										   "\tStudent Roll No : " +  rst.getInt(2) +"\n"+
										   "\tStudent Department : "+ rst.getString(4) +"\n"+
										   "\tStudent Phone Number : " + rst.getString(5));
						System.out.println("___________________________________________________________________");
						System.out.println("___________________________________________________________________ \n\n");
						
						
					
	
				System.out.print("Press Enter to Continue");
				
				 try {
					 
					exit = br.readLine();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
			}else {
					System.out.println("Student Not Found in Library");
					 System.out.print("Press Enter to Continue");
					 
					 try {
						 
						exit = br.readLine();
						
					} catch (IOException e){
						
						e.printStackTrace();
						
					}
				}
			
		
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}
	
	}

}
