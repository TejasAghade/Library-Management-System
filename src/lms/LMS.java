package lms;

import java.io.*;
import java.sql.*;


public class LMS{
	
	void mainClass() throws NumberFormatException, IOException {
		
		
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		
		int loop = 0;
		
    
        //creating objects of classes
        
        Students stu = new Students();
        teachers teac = new teachers();
        
        Books bk = new Books();
        
        issueAndReturn issueReturn = new issueAndReturn();
        
        
        
        int option = 0;
		
		while(loop != 1) {
			
//			clearConsole.CConsole();
			
			System.out.println("\t**  Welcome to Library Management System  **");	
			System.out.println("\t____________________________________________\n");	
			
			
			
			System.out.println("  1] Add Student In Library \n"
							 + "  2] Display Students  \n"
							 + "  3] Update Student From Library \n"
							 + "  4] Remove Student From Library \n"
							 + "  5] Search Student in Library \n"
							 + "________________________________\n\n"
							 + "  6] Add Teachers In Library \n"
							 + "  7] Display Teacher \n"
							 + "  8] Update Teacher From Library \n"
							 + "  9] Remove Teacher From Library \n"
							 + " _______________________________\n\n"
							 + " 10] Issue Book To Student \n"
							 + " 11] Return Book \n"
							 + " _______________________________\n\n"
							 + " 12] Add Books To Library \n"
							 + " 13] Display Books Of Library \n"
							 + " 14] Update Books From Library \n"
							 + " 15] Delete Books From Library \n"
							 + " 16] Search Books \n"
							 + " _______________________________\n\n"
							 + " 17] Display issued Books \n"
							 + " 18] Display Returned Books \n"
							 + " _______________________________\n\n"
							 + "  0] Exit\n");

			System.out.print("Enter Option > ");
			
		    	option = Integer.parseInt(br.readLine());
		    	System.out.println("\n\n");
			
				if(option == 0) {
					
					System.out.println("Your option is Exit");
					System.out.println("\t**  Thanks For using the System **\n");
					loop=1;
					System.exit(1);
					
				}
				
				
				String sId;
				int bookCode;
				String TeacherId;
				
				switch (option) {
				
					case 1: {
						
						stu.setStudentInfo();
						break;
					}
					case 2: {
						
						System.out.println("Enter How many Students you want to Display, Enter 0 for all Records :");
						int no = Integer.parseInt(br.readLine());
						stu.DisplayStudents(no);
						break;
						
					}
					case 3: {
						
						System.out.println("Please Enter Student Id to Update:");
						sId = br.readLine();
						
						stu.updateStudent(sId);
						break;
						
					}
					case 4: {
						
						System.out.print("Enter Student Id To Remove Student :");
						sId = br.readLine();
						stu.deleteStudent(sId);
						break;
						
					}
					case 5: {
						
						System.out.println("Enter Student Id To search student :");
						sId = br.readLine();
						
					    stu.SearchStudent(sId);
					    break;
					
	
	
						
					}
					case 6: {
						
						teac.setTeacherInfo();
						break;
						
					}case 7: {
						
						System.out.println("Enter How many Teachers you want to Display, Enter 0 for all Records :");

						int number = Integer.parseInt(br.readLine());
						
						teac.DisplayTeachers(number);
						break;
						
					}case 8: {
						System.out.println("Enter Teacher Id To Update Teacher :");
						TeacherId = br.readLine();
						teac.updateTeacher(TeacherId);
						break;
						
					}case 9: {
						
						System.out.println("Enter Teacher Id To Delete Teacher :");
						TeacherId = br.readLine();
						teac.deleteTeacher(TeacherId);
						break;
						
					}
					
					
					
					
					
					
					case 10: {
						
						int isTeacher;
						
						System.out.println("Issue Book :");
						
						System.out.println("is Teacher? Enter 1 for Yes / 0 for No :");
						
						isTeacher = Integer.parseInt(br.readLine());
						
						
						String id="";
						
						if(isTeacher==1) {
							
							System.out.println("Please Enter Teacher Id");
							id = br.readLine();
							
							
						}else {
							
							System.out.println("Please Enter Student Library Id :");
							
							id = br.readLine();
							
						}
						
						
						
						
						System.out.println("Please Enter Book Code :");
						
						bookCode = Integer.parseInt(br.readLine());
						
						
						
						issueReturn.issueBook(id, bookCode, isTeacher);
						
						
						break;
						
					}
					case 11: {
						
						System.out.println("Return book :");
						
						System.out.println("Enter book code to return book :");
						
						bookCode = Integer.parseInt(br.readLine());
						
						issueReturn.bookReturn(bookCode);
						break;
						//write code to show returned book details with student name
						
					}
					case 12: {
						bk.setBookInfo();
						break;
						
					}
					case 13: {
						
						
						System.out.println("Enter How many books you want to Display :\n"
								+ "Enter 0 for all Records :");
						int qty = Integer.parseInt(br.readLine());
						bk.displayBooks(qty);
						break;
						
					}
					case 14: {
						
						System.out.println("Enter Book Code to update Book :");
						bookCode = Integer.parseInt(br.readLine());
						
						bk.updateBook(bookCode);
						break;
						
					}
					case 15: {
						
						System.out.println("Enter Book Code to delete Book :");
						bookCode = Integer.parseInt(br.readLine());
						
						bk.deleteBook(bookCode);
						break;
						
					}
					case 16: {
						
						
						System.out.println("\n Search Book By\n");
						
							System.out.println("\t1. by Name\n\t2. by Book Code");
							
							System.out.print("\tEnter choice: ");
							
							int op = Integer.parseInt(br.readLine());
							
							if(op == 1) {
								
								System.out.println("Enter Book Name to search Book :");
								String bookName = br.readLine();
								bk.SearchBook(bookName);
								
								
							}else if(op == 2) {
								
								System.out.println("Enter Book Code to search Book :");
								bookCode = Integer.parseInt(br.readLine());
								
								bk.SearchBook(bookCode);
							}
						
						
//						System.out.println("Enter Book Code to search Book :");
//						bookCode = Integer.parseInt(br.readLine());
//						
//						bk.SearchBook(bookCode);
						break;
						
					}
					case 17: {
						
						issueReturn.DisplayIssue();
						break;
						
					}
					case 18: {
						issueReturn.DisplayReturned();
						break;
					}
					
					
					
					default:
						System.out.println("Enter valid Option :");
						break;
				}
			
			
		}

//        issueAndReturn is = new issueAndReturn();
//		
//		is.DisplayReturned();
//		
		
	}
	


}
