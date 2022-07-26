package lms;

import java.io.IOException;

public class clearConsole {
	
	public static void CConsole() {
		//clearing the console
		try {
			
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			
		} catch (InterruptedException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			
			e.printStackTrace();
		}
	}
}
