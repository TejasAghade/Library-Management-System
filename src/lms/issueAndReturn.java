package lms;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.util.*;



public class issueAndReturn {
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	Conn conObj = new Conn();
	
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.M.dd");

		
	Books bk = new Books();

	
	
	
	
	
	
	Boolean checkIssue(int bookCode) {
			
				String query = "select * from issue where bookCode = '" + bookCode +"'";
				
				try {
		
					
					Statement stexi = conObj.con.createStatement();
					ResultSet rsexi = stexi.executeQuery(query);
				
				if(rsexi.next()) {
					
					return true;
					
				}else {
					
					return false;
					
				}
				
				
				}catch(SQLException sqe) {
					
					sqe.printStackTrace();
					
					
				}
				
				return false;
		}
	
	
	
	
	
	
	void issueBook(String id, int BookCode, int isTeacher) {
		
		
		
		if(!bk.checkExistance(BookCode)) {
			System.out.println("Book is not Available");
			return;
		}
		
		
		
		
		
		
		
		Statement st1=null;
		Statement st2=null;
		Statement st3=null;
		
		try {
			
			 st1 = conObj.con.createStatement();
		
			 st2 = conObj.con.createStatement();
			 st3 = conObj.con.createStatement();
		
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			
		}
		// query's for getting student and book details
		
		String tQuery= "";
		String sQuery = "";
		
		
		
		String StudentId = "";
		int StudentRollNo = 0;
		String StudentName = "";
		String StudentDept = "";
		String StudentContact = "";

		

		String teacherName="";
		String teacherDept="";
		String teacherContact="";
		
		
		if(isTeacher == 1) {
			
			 tQuery = "select * from teachers where teacher_id ='"+ id +"'";
			 ResultSet TeacherRst;
			 
			try {
				
				TeacherRst = st3.executeQuery(tQuery);
				TeacherRst.next();
			 
				 teacherName = TeacherRst.getString(2);
				 teacherDept = TeacherRst.getString(3);
				 teacherContact = TeacherRst.getString(4);
			 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
		}else {
			
			sQuery = "select * from students where S_id ='"+ id +"'";
			ResultSet StudentRst;
			try {
				
					StudentRst = st1.executeQuery(sQuery);
				
					StudentRst.next();
					
					 StudentRollNo = StudentRst.getInt(2);
					 StudentId = StudentRst.getString(1);
					
					 StudentName = StudentRst.getString(3);
					
					 StudentDept = StudentRst.getString(4);
					 StudentContact = StudentRst.getString(5);
			 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		String bQuery = "select * from books where bookCode ="+BookCode;
		
		try {
			
		
			
			
			
			
			
			ResultSet BookRst = st2.executeQuery(bQuery);
			
			
			BookRst.next();
			String IssueStatus = "issued";

			int bookCode = BookRst.getInt(1);
			String BookName = BookRst.getString(2);
			
			
			
			//getting current date to add in issue table
			
			String query;
			
			if(isTeacher == 0) {
			
				 query = "insert into issue (S_roll_no, id, bookCode, bookName, Name, dept, is_teacher, contact, issue_date, issue_status) values ("+StudentRollNo+",'"+StudentId+"','"+bookCode+"','"+BookName+"','"+StudentName+"','"+StudentDept+"','N','"+StudentContact+"','"+sdf.format(date)+"','"+IssueStatus+"')";
				 
			}else {
				
				query = "insert into issue (id, bookCode, bookName, Name, dept, contact,is_teacher, issue_date, issue_status) values ('"+id+"','"+bookCode+"','"+BookName+"','"+teacherName+"','"+teacherDept+"','"+teacherContact+"', 'Y' , '"+sdf.format(date)+"','"+IssueStatus+"')";
			}
			
			PreparedStatement pst = conObj.con.prepareStatement(query);
			
			
//			System.out.println(query);
			
			int result = pst.executeUpdate();
			
			if(result>0) {
				System.out.println("Book Issued Successfully");
				 System.out.print("Press Enter to Continue");
				 
				 try {
					 
					String exit = br.readLine();
					
				} catch (IOException e){
					
					e.printStackTrace();
					
				}
			}else {
				System.out.println("Failed to Issue Book");
				 System.out.print("Press Enter to Continue");
				 
				 try {
					 
					String exit = br.readLine();
					
				} catch (IOException e){
					
					e.printStackTrace();
					
				}
			}
			
			
			
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	//function for return book
	void bookReturn(int bookCode) {
		
		if(!bk.checkExistance(bookCode)) {
			System.out.println("Book does not exist");
			return;
		}
		
		

		if(!checkIssue(bookCode)) {
			System.out.println("Book is not Issued to Anyone!");
			return;
		}
		
		
		
		try {
			
			Books book = new Books();
			
			Statement st = conObj.con.createStatement();
			
			ResultSet rst = st.executeQuery("select * from issue where bookCode="+bookCode+" AND issue_status ='issued'");
			
		
			
			if(rst.next()) {
				
				String isTeacher = rst.getString(9);
				Date issueDate = rst.getDate(10);
				
				 Date d1 = issueDate;
		            Date d2 = sdf.parse(sdf.format(date));
		  
		            long difference_In_Time
	                = d2.getTime() - d1.getTime();
		            
		            
		            // Calucalte time difference
		            // in days
		  
		            long difference_In_Days
		                = (difference_In_Time
		                   / (1000 * 60 * 60 * 24))
		                  % 365;
			
				
		            
		            if(difference_In_Days > 15) {
		            	
		            	
		            		int fine = ((int)difference_In_Days)*2;
		            	
		            		
		            		String query="";
		            		
		            		
		            		if(isTeacher == "N") {
			            		 query = "update issue set issue_status ='returned', return_date ='"+sdf.format(date)+"', fine ="+fine+" where bookCode ="+bookCode+" and issue_status='issued'";
			            		 System.out.println("\n Your fine is " + fine);
		            		}else {
		            			
			            		 query = "update issue set issue_status ='returned', return_date ='"+sdf.format(date)+"', where bookCode ="+bookCode+" and issue_status='issued'";

		            		}
		            		
		            		
		            		
		            		
		            		PreparedStatement pst = conObj.con.prepareStatement(query);
						
		            		int result = pst.executeUpdate();
						 
							if(result>0) {
								 
								System.out.println("Book Returned Successfully");
								 System.out.print("Press Enter to Continue");
								 
								 try {
									 
									String exit = br.readLine();
									
								} catch (IOException e){
									
									e.printStackTrace();
									
								}
								
								 
							}else {
								 
								System.out.println("something goes wrong with returning book");
								System.out.print("Press Enter to Continue");
								 
								 try {
									 
									String exit = br.readLine();
									
								} catch (IOException e){
									
									e.printStackTrace();
									
								}
								 
							}
		            	
		            }else {
		            	
		            	
		            	
		            	
		            	String query = "update issue set issue_status ='returned', return_date ='"+sdf.format(date)+"' where bookCode ="+bookCode +" and issue_status = 'issued'";
		            
		            		
		            	
		            		PreparedStatement pst = conObj.con.prepareStatement(query);
						
		            		int result = pst.executeUpdate();
						 
							if(result>0) {
								 
								 System.out.println("Book Returned Successfully");
								 System.out.print("Press Enter to Continue");
								 
								 try {
									 
									String exit = br.readLine();
									
								} catch (IOException e){
									
									e.printStackTrace();
									
								}
								 
							 }else {
								 
								 System.out.println("something goes wrong with returning book");
								 System.out.print("Press Enter to Continue");
								 
								 try {
									 
									String exit = br.readLine();
									
								} catch (IOException e){
									
									e.printStackTrace();
									
								}
								 
							 }

		            	}
			
				
			}else {
				System.out.println("Book is not issued to anyone");
				System.out.print("Press Enter to Continue");
				 
				 try {
					 
					String exit = br.readLine();
					
				} catch (IOException e){
					
					e.printStackTrace();
					
				}
			}
			
		
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	
	void DisplayReturned() {
		
		try {
			
			Statement st = conObj.con.createStatement();
			
			ResultSet rst = st.executeQuery("select * from issue where issue_status = 'returned'");
			
					
			System.out.println("\n**  Details Returned Books  **");
			
			while(rst.next()) {
				
				System.out.println("=================================================\n");
				System.out.println("\tRoll No         : "+ rst.getInt(2)+"\n"
						+ "\tStudent Id      : "+rst.getString(3)+"\n"
						+ "\tBook Code       : "+rst.getInt(4)+"\n"
						+ "\tBook Name       : "+rst.getString(5)+"\n"
						+ "\tStudent name    : "+rst.getString(6)+"\n"
						+ "\tStudent Dept    : "+rst.getString(7)+"\n"
						+ "\tStudent Contact : "+rst.getString(8)+"\n"
						+ "\tIssue Date      : "+rst.getDate(10)+"\n"
						+ "\tStatus          : "+rst.getString(11)+"\n"
						+ "\tReturn Date     : "+rst.getDate(12)+"\n"
						+ "\tFine            : "+rst.getInt(13));
				System.out.println("\n=================================================");
				
			}
			
			System.out.print("Press Enter to Continue");
			 
			 try {
				 
				String exit = br.readLine();
				
			} catch (IOException e){
				
				e.printStackTrace();
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
	
	//details of issued books
	void DisplayIssue() {
		
		try {
			
			Statement st = conObj.con.createStatement();
			
			ResultSet rst = st.executeQuery("select * from issue where issue_status = 'issued'");
			
				
				
			
			System.out.println("\n**  Details Issued Books  **");
				
				while(rst.next()) {
								
				System.out.println("=================================================\n");
				System.out.println("\tRoll No         : "+ rst.getInt(2)+"\n"
								 + "\tStudent Id      : "+rst.getString(3)+"\n"
								 + "\tBook Code       : "+rst.getInt(4)+"\n"
								 + "\tBook Name       : "+rst.getString(5)+"\n"
								 + "\tStudent name    : "+rst.getString(6)+"\n"
								 + "\tStudent Dept    : "+rst.getString(7)+"\n"
								 + "\tStudent Contact : "+rst.getString(8)+"\n"
								 + "\tIssue Date      : "+rst.getDate(10)+"\n"
								 + "\tStatus          : "+rst.getString(11)+"\n"
								 + "\tReturn Date     : "+rst.getDate(12));
				System.out.println("\n=================================================");
				
				}
			
				System.out.print("Press Enter to Continue");
				String exit = br.readLine();
					
				
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
