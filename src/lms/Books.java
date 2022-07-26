package lms;

import java.io.*;
import java.sql.*;

public class Books{

	
	String bookName;
	String bookAuthor;
	int bookCode;
	String dept;
	int bookPrice;
	String exit;
	Conn conObj = new Conn();
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	
	Boolean checkExistance(String bName) {
		
		String query = "select * from books where bookName = '"+ bName +"'";
		
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
	
	
	Boolean checkExistance(int bookCode) {
		
		String query = "select * from books where bookCode = '" + bookCode +"'";
		
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
	
	
	
	
	void setBookInfo() {
		
		// clearConsole.CConsole();
		
		System.out.println("**  Library Management System  **\n");
		System.out.println("* Enter Book Details *\n");
		
		try {
		
			
		System.out.println("Enter Book Code ");
		bookCode = Integer.parseInt(br.readLine());
		
		
		if(checkExistance(bookCode)) {
			System.out.println("Book Already Exist ");
			return;
		}
		
		System.out.println("Enter Book Name ");
		bookName = br.readLine();
		
		System.out.println("Enter Book Author ");
		bookAuthor = br.readLine();
		
		System.out.println("Enter Book Department ");
		 dept = br.readLine();
		
		System.out.println("Enter Book Price ");
		bookPrice = Integer.parseInt(br.readLine());
		
		
		// calling add student function to add student into database
		addBooks();
		
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	void addBooks() {
		
	
		
		try {
			
		
			//inserting values into database for book
			PreparedStatement pst = null;
			
			pst = conObj.con.prepareStatement("insert into books values(?, ?, ?, ?, ?)");
			
			pst.setInt(1, bookCode);
			pst.setString(2, bookName);			
			pst.setString(3, bookAuthor);
			pst.setString(4, dept);
			pst.setInt(5, bookPrice);
			
			
			// executing query
			
			int result = pst.executeUpdate();
			
			
			//checking if the record is inserted or not
			if(result>0) {
				
				System.out.println("book Added Sucessfully");
				System.out.print("Press Enter to Continue");
				exit = br.readLine();
				
			}else {
				
				System.out.println("failed to add book");
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
	
	
	

	
	
	void deleteBook(int bookCode) throws IOException {
		
		
		
		if(!checkExistance(bookCode)) {
			System.out.println("Book Does not Exist");
			System.out.print("Press Enter to Continue");
			exit = br.readLine();
			 return;
		}
		
		System.out.println("* Current Details of Book *\n");
		SearchBook(bookCode);

		try {
			
//			SearchBook(bookCode);
			
			System.out.println("\nAre you sure to delete Book press y/n :");
//			String confirm = br.readLine();
			char confirm = (char) br.read();
			
			
			if(confirm =='y') {
				
					PreparedStatement pst = conObj.con.prepareStatement("delete from books where bookCode =" + bookCode);
			
					int result = pst.executeUpdate();
			
					if(result>0) {
						
						System.out.println("Book deleted Successfully");
						System.out.print("Press Enter to Continue");
						exit = br.readLine();
						
					}else {
						
						System.out.println("failed to delete Book");
						System.out.print("Press Enter to Continue");
						exit = br.readLine();
					}
		
			}else {
				System.out.println("operation Cancelled");
				System.out.print("Press Enter to Continue");
				exit = br.readLine();
			}
		
		
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}
		
	}
	
	
	// getting count of books from db
	void getcount() {
		
		int count=0;
		int mba = 0;
		int mca = 0;
		
		try {
			
			Statement stmt = conObj.con.createStatement();
			Statement stmt1 = conObj.con.createStatement();
			Statement stmt2 = conObj.con.createStatement();
			
			String query = "SELECT count(*) from books";
			
			String query1 = "SELECT count(*) from books where bookDept like '%MCA%'";
			
			String query2 = "SELECT count(*) from books where bookDept like '%MBA%'";
			
			
			
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
		
		
		System.out.println("Total Books in Library : " + count + " | Total Books of MCA : "+ mca +" | Total Books of MBA : " + mba +"\n");
		
		
	}
	
	
	
	void displayBooks(int number) throws IOException {
		
		// clearConsole.CConsole();

//		query to fetch limited records
		
		
		
		String query = "select * from books LIMIT "+ number;
		
		
		
		
		if(number== 0) {
			
			
			query = "select * from books";
			
		}
		
		
		try {
			
		Statement stmt = conObj.con.createStatement();
		ResultSet rst = stmt.executeQuery(query);
		
		// showing student detail
		System.out.println("**  Library Management System  **\n");
				
		
			System.out.println("\n\n* Books Details *\n\n");
			
			getcount();
			
			System.out.println("___________________________________________________________________");
			System.out.println("___________________________________________________________________ \n");
		while(rst.next()) {
			System.out.println("\tBook Code       : " + rst.getInt(1) +"\n"+
							   "\tBook Name       :"+ rst.getString(2) +"\n"+
							   "\tBook Author     : " +  rst.getString(3) +"\n"+
							   "\tBook Department : " +  rst.getString(4) +"\n"+
							   "\tBook Price      : "+ rst.getInt(5) +"\n\n");
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
	
	
	// function to update books table
	void updateQuery(String query) throws SQLException, IOException {
		
		
		
		PreparedStatement pst = conObj.con.prepareStatement(query);
		
		int result = pst.executeUpdate();
		
		
		if(result>0) {
			
			System.out.println("Details of Book Updated Successfully");
			System.out.print("Press Enter to Continue");
			exit = br.readLine();
			
		}else {
			
			System.out.println("failed to Update Details of Book");
			
			System.out.print("Press Enter to Continue");
			exit = br.readLine();
			
		
			
		}
		

	

}
	
	
	void updateBook(int bookCode) {
		
		// clearConsole.CConsole();
		
		this.bookCode = bookCode;
		
		if(!checkExistance(bookCode)) {
			System.out.println("Book does not Exist");
			System.out.println("Press Enter to Continue");
			try {
				exit = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		

		try {
			
			//updating values into database for book

			
			
			
			int option;
			
			System.out.println("**  Library Management System  **\n");
			System.out.println("* Current Details of Books *\n");
			SearchBook(bookCode);
			System.out.println("\t* What you want to update *\n\n"
					+ "\t1] Update Name of the book\n"
					+ "\t2] Update Book Author\n"
					+ "\t3] Update Book Department\n"
					+ "\t4] Updtae Book Price\n"
					+ "\t4] Cancel \n");
			
			System.out.print("Enter Option > ");
			option = Integer.parseInt(br.readLine());
			
			
			
			switch (option) {
			
			case 1: {
				
				System.out.println("Enter new name for Book");
				bookName = br.readLine();
				
				updateQuery("UPDATE books set bookName = "+bookName);
				break;
				
			}
			case 2: {
				 System.out.println("Enter new name of Book Author");
				 bookAuthor = br.readLine();
				 
				 updateQuery("UPDATE books set bookAuthor = "+bookAuthor);
				 break;
				 
			}
			case 3: {
				System.out.println("Enter Department of Book");
				dept = br.readLine();
				
				updateQuery("UPDATE books set bookDepartment = "+dept);
				break;
				
			}
			case 4: {
				 System.out.println("Enter new price of Book");
				 bookPrice = Integer.parseInt(br.readLine());
				
				 updateQuery("UPDATE books set bookPrice = "+bookPrice);
				 break;
				 
			}
			default:
				System.out.println("Invalid Option");
				break;
			}
			
			System.out.print("Press Enter to Continue");
			exit = br.readLine();
			
		}
		catch(SQLException sqe) {
			sqe.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	void SearchBook(String bName){
		
		try {
			
			if(checkExistance(bName)) {
				
				String query = "select * from books where bookName like '%" + bName + "%'";
				
				
				Statement st = conObj.con.createStatement();
				ResultSet rst = st.executeQuery(query);
				
				// showing student detail
				System.out.println("**  Library Management System  **\n");
				
				System.out.println("* Books Details *\n\n");
				
				System.out.println("___________________________________________________________________");
				System.out.println("___________________________________________________________________ \n");
				int cnt =0;
				while(rst.next()) {
				System.out.println("\tBook Code       : " + rst.getInt(1) +"\n"+
							"\tBook Name       :"+ rst.getString(2) +"\n"+
							"\tBook Author     : " +  rst.getString(3) +"\n"+
							"\tBook Department : " +  rst.getString(4) +"\n"+
							"\tBook Price      : "+ rst.getInt(5) +"\n");
					System.out.println("___________________________________________________________________");
					System.out.println("___________________________________________________________________ \n\n");
					cnt++;
				}
				
				System.out.println("Total Books found : "+ cnt );
				
				System.out.print("Press Enter to Continue");
				
				try {
					
					exit = br.readLine();
					return;
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}else {
				System.out.println("Book Not Found in Library\n");
				System.out.print("Press Enter to Continue");
				
				try {
					
					exit = br.readLine();
					
				} catch (IOException e) {
					
					e.printStackTrace();
					
				}
			}
			
			
		}catch(SQLException sqe) {
			sqe.printStackTrace();
		}
		
	}//end of search method
	
	
	void SearchBook(int bCode){
		// clearConsole.CConsole();
		
		try {
		
					if(checkExistance(bCode)) {
				
					String query = "select * from books where bookCode = "+bCode;
				
				
					Statement st = conObj.con.createStatement();
					ResultSet rst = st.executeQuery(query);
				
					// showing student detail
					System.out.println("**  Library Management System  **\n");
				
					System.out.println("* Books Details *\n\n");
					
					System.out.println("___________________________________________________________________");
					System.out.println("___________________________________________________________________ \n");
				
							while(rst.next()) {
								System.out.println("\tBook Code       : " + rst.getInt(1) +"\n"+
												   "\tBook Name       :"+ rst.getString(2) +"\n"+
												   "\tBook Author     : " +  rst.getString(3) +"\n"+
												   "\tBook Department : " +  rst.getString(4) +"\n"+
												   "\tBook Price      : "+ rst.getInt(5) +"\n");
								System.out.println("___________________________________________________________________");
								System.out.println("___________________________________________________________________ \n\n");
							
							}
					System.out.print("Press Enter to Continue");
					
					try {
						
						exit = br.readLine();
						return;
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
			}else {
					System.out.println("Book Not Found in Library\n");
					System.out.print("Press Enter to Continue");
					
					try {
						
						exit = br.readLine();
						
					} catch (IOException e) {
						
						e.printStackTrace();
						
					}
				}

	
	}catch(SQLException sqe) {
		sqe.printStackTrace();
	}
		
}//end of search method


}//end of class