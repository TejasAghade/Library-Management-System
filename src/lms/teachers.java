package lms;
import java.sql.*;
import java.io.*;

public class teachers {
	
	String TeacherId;
	String TeacherName;
	String TeacherDepartment;
	String TeacherContact;
	String exit;
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	Conn conObj = new Conn();
	
	void setTeacherInfo() {
		// clearConsole.CConsole();
		
		System.out.println("**  Library Management System  **\n");
		System.out.println("* Enter Teacher Details *\n");
		
		
		try {
			
			System.out.println("Enter Teacher Id");
			TeacherId = br.readLine();

		if(checkExistance(TeacherId)) {
			System.out.println("Teacher Already Exist");
			return;
		}
		
		
		System.out.println("Enter Teacher Name");
		TeacherName = br.readLine();
		
		System.out.println("Enter Teacher Department");
		TeacherDepartment = br.readLine();
		
		System.out.println("Enter Teacher Phone Number");
		TeacherContact = br.readLine();
		
		// calling add Teacher function to add Teacher into database
		AddTeacher();
		
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
		
	}
	
	
	Boolean checkExistance(String TeacherId) {
		
		
//		use  ="'"+ string + "'"
		
		String query = "select * from teachers where teacher_id = '" + TeacherId +"'";
		
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
		
		return false;
	}
	
	
	
	//method for adding Teachers to library
	void AddTeacher() {
		

		
		try {
			
		
			//inserting values into database
			
			PreparedStatement pst = conObj.con.prepareStatement("insert into teachers values(?, ?, ?, ?)");
			
			pst.setString(1, TeacherId);
			pst.setString(2, TeacherName);
			pst.setString(3, TeacherDepartment);
			pst.setString(4, TeacherContact);
			
			
			// executing query
			
			int result = pst.executeUpdate();
			
			
			//checking if the record is inserted or not
			if(result>0) {
				
				System.out.println("\nTeacher Added Sucessfully");
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
			
			String query = "SELECT count(*) from teachers";
			
			String query1 = "SELECT count(*) from teachers where teacher_dept like '%MCA%'";
			
			String query2 = "SELECT count(*) from teachers where teacher_dept like '%MBA%'";
			
			
			
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
		
		
		System.out.println("Total Teachers : " + count + " | Total Teachers of MCA : "+ mca +" | Total Teachers of MBA : " + mba +"\n");
		
		
	}
	
	
	// method to show Teacher
	
	void DisplayTeachers(int number) throws IOException {
		
		// clearConsole.CConsole();
		
//		query to fetch limited records
		
		String query = "select * from teachers LIMIT "+ number;
		
		if(number== 0) {
			
			
			query = "select * from teachers";
			
		}
		
		
		try {
			
		Statement st = conObj.con.createStatement();
		ResultSet rst = st.executeQuery(query);
		
		// showing Teacher detail
		System.out.println("**  Library Management System  **\n");
		
		System.out.println("* Teachers Details *\n\n");
		
		getcount();
		
		System.out.println("___________________________________________________________________");
		System.out.println("___________________________________________________________________ \n");
		
			while(rst.next()) {
				System.out.println("\tTeacher Name : " + rst.getString(2) +"\n"+
								   "\tTeacher Id :"+ rst.getString(1) +"\n"+
								   "\tTeacher Department : "+ rst.getString(3) +"\n"+
								   "\tTeacher Phone Number : " + rst.getString(4));
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
	
	
	
	void deleteTeacher(String TeacherId) throws IOException {
		
		// clearConsole.CConsole();
		
		if(!checkExistance(TeacherId)) {
			System.out.println("Teacher Does Not Exist");
			System.out.print("Press Enter to Continue");
			exit = br.readLine();
			return;
		}
		
		
		
		System.out.println("**  Library Management System  **\n");
		
			
		
			try {
				
				System.out.println("* Current Details of Teacher *\n");
				SearchTeacher(TeacherId);
								
				System.out.println("Are you sure You wan to delete this Teacher Enter y/n");
				char confirm = (char) br.read();
				
				
				if(confirm =='y') {
				
				PreparedStatement pst = conObj.con.prepareStatement("delete from teachers where teacher_id =" + TeacherId);
				
				int result = pst.executeUpdate();
				
					if(result>0) {
						
						System.out.println("Teacher deleted Successfully");
						System.out.print("Press Enter to Continue");
						 exit = br.readLine();
						
					}else {
						
						System.out.println("failed to delete");
						System.out.print("Press Enter to Continue");
						exit = br.readLine();
						
					}
				
				}else {
					System.out.println("Operation cancelled");
					System.out.print("Press Enter to Continue");
					exit = br.readLine();
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
				
				System.out.println("\nDetails of Teacher Updated Successfully");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
				
			}else {
				
				System.out.println("failed to Update Details of Teacher");
				System.out.print("Press Enter to Continue");
				 exit = br.readLine();
			}
			
	
		
	
	}
	
	
	void updateTeacher(String TeacherId){
		// clearConsole.CConsole();
		
			this.TeacherId = TeacherId;
			int option;
			
			if(!checkExistance(TeacherId)) {
				
				System.out.println("Teacher does Not Exist");
				System.out.print("Press Enter To Continue");
				try {
					exit = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
				
			}


			try {
			
				System.out.println("**  Library Management System  **\n");
				
				System.out.println("* Current Details of Teacher *\n");
				SearchTeacher(TeacherId);
				
				
				System.out.println("\t* What you want to update *\n\n"
						+ "\t1] Update Name of the Teacher\n"
						+ "\t2] Update Teacher Department\n"
						+ "\t3] Updtae Teacher Contact\n"
						+ "\t4] Cancel \n\n");
				
				System.out.print("Enter Option > ");
				option = Integer.parseInt(br.readLine());
				
				switch (option) {
				
				case 1: {
										
					System.out.println("Enter Name for Teacher");
					TeacherName = br.readLine();
					
					updateQuery("UPDATE teachers set teacher_name ='"+TeacherName+"' where teacher_id ='"+TeacherId+"'");
					break;
					
				}
				
				case 2: {
					
					System.out.println("Enter Department for Teacher");
					TeacherDepartment = br.readLine();
					
					updateQuery("UPDATE teachers set teacher_dept ='"+TeacherDepartment+"' where teacher_id ='"+TeacherId+"'");
					break;
				}
				
				case 3: {
					
					System.out.println("Enter Contact for Teacher");
					TeacherContact = br.readLine();
					
					updateQuery("UPDATE teachers set teacher_contact ='"+TeacherContact+"' where teacher_id ='"+TeacherId+"'");
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
			
			
			
		}
		
	
	void SearchTeacher(String TeacherId){
		
		// clearConsole.CConsole();
		try {
			
			if(checkExistance(TeacherId)) {
				
				String query = "select * from teachers where teacher_id = '"+TeacherId+"'";
				
				
				Statement st = conObj.con.createStatement();
				ResultSet rst = st.executeQuery(query);
				
				// showing Teacher detail
				System.out.println("**  Library Management System  **\n");
				
				rst.next();
				
				System.out.println("* Teachesr Details *\n\n");
				System.out.println("___________________________________________________________________");
				System.out.println("___________________________________________________________________ \n");
						System.out.println("\tTeachers Name : " + rst.getString(2) +"\n"+
										   "\tTeachers Id :"+ rst.getString(1) +"\n"+
										   "\tTeachers Department : "+ rst.getString(3) +"\n"+
										   "\tTeachers Phone Number : " + rst.getString(4));
						System.out.println("___________________________________________________________________");
						System.out.println("___________________________________________________________________ \n\n");
						
						
					
	
				System.out.print("Press Enter to Continue");
				
				 try {
					 
					exit = br.readLine();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
				
			}else {
					System.out.println("Teacher Not Found in Library");
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
