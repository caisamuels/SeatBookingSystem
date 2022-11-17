import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SeatBookingSystem {
	static Scanner console = new Scanner(System.in);
	static boolean validInput = false; // Used for validation
	static String UserInput = "";
	
	static void exitPrompt() { //Method to ask user if they would like to quit or return to the main menu when code block is finished
		while (validInput  == false) {
			System.out.println("Would you like to quit or return to main menu?\nQ - Quit\nM - Main menu");
			UserInput = console.nextLine();
			if (UserInput.equalsIgnoreCase("M") || (UserInput.equalsIgnoreCase("Q"))) {
				validInput = true;
			}
			else {
				System.out.println("Error! Input must be \"M \" or \"Q\"");
			}
		}
		if (UserInput.equalsIgnoreCase("M")) {
			UserInput = "";
			validInput = false;
			return;//Returns to code which returns back to switch statement
		}
			else if (UserInput.equalsIgnoreCase("Q")) {
				UserInput = "";
				validInput = false;
				System.exit(0);//Exits program
			}
	}
	
	public static void main(String[] args) throws IOException {
		String fileName = "seats.txt";
		int lineNumber = -1;
		String reserveInput = "";
		String seatClassInput = "";
		String isWindowInput = "";
		String isTableInput = "";
		Double seatPriceInput = 0.00;
		
		ArrayList<User> users = new ArrayList<User>();
		
		Path path = Paths.get("seats.txt");
		
		while(true) {
			FileReader file = new FileReader(fileName);
			Scanner read = new Scanner(file);
			List<String> fileContent = new ArrayList<>(Files.readAllLines(path, StandardCharsets.UTF_8));
			while (read.hasNextLine()) {
				users.add(new User(read.next(),read.next(),read.next(),read.next(),read.next(),read.nextDouble(),read.next()));
				//Add all bookings to ArrayList
			}
			System.out.println("--Main Menu--");
			
			System.out.println("1 - Reserve Seat\n2- Cancel Seat\n3 - View Seat Reservations\nQ - Quit");
			UserInput = console.nextLine();
			switch (UserInput) {
			case "1":
				while (validInput == false) { //Loops until valid input is entered
					System.out.println("First or Standard Class?");
					seatClassInput = console.nextLine();
					if (seatClassInput.equalsIgnoreCase("first")) {
						seatClassInput = "1ST";
						validInput = true;
					} else if (seatClassInput.equalsIgnoreCase("standard")) {
						seatClassInput = "STD";
						validInput = true;
				}
					else {
						System.out.println("Error! Input must be \"First \" or \"Standard\"");
					}
				}
				validInput = false;
				while (validInput == false) { //Loops until valid input is entered
					System.out.println("By Window or Aisle?");
					isWindowInput = console.nextLine();
					if (isWindowInput.equalsIgnoreCase("window")) {
						isWindowInput = "true";
						validInput = true;
					} else if (isWindowInput.equalsIgnoreCase("aisle")) {
						isWindowInput = "false";
						validInput = true;
					}
					else {
						System.out.println("Error! Input must be \"Window \" or \"Aisle\"");
					}
				}
				validInput = false;
				while (validInput == false) { //Loops until valid input is entered
					System.out.println("Would you like a table?");
					isTableInput = console.nextLine();
					if (isTableInput.equalsIgnoreCase("yes")) {
						isTableInput = "true";
						validInput = true;
					} else if (isTableInput.equalsIgnoreCase("no")) {
						isTableInput = "false";
						validInput = true;
					}
					else {
						System.out.println("Error! Input must be \"Yes \" or \"No\"");
					}
				}
				validInput = false;
				while (validInput == false) { //Loops until integer is entered
					try {
						System.out.println("Maximum Seat Price?");
						seatPriceInput = console.nextDouble();
						validInput = true;
					 }
					catch (InputMismatchException e) {
						System.out.println("Error! Input must be a number!");
						console.nextLine();
					}
				}
				validInput = false;
				console.nextLine();
				for(User user : users) {
					lineNumber++;
					if(user.seatClass.equals(seatClassInput) && user.isWindow.equals(isWindowInput) && user.isTable.equals(isTableInput) && user.seatPrice < seatPriceInput && user.eMail.equals("free")){
						while (validInput == false) {
						System.out.println("Match Found! Seat " + user.seatNum + " is available, would you like to reserve?");
						reserveInput = console.nextLine(); 
						if (reserveInput.equalsIgnoreCase("yes") || reserveInput.equalsIgnoreCase("no")) {
							validInput = true;}
						else {
							System.out.println("Error! Input must be \"Yes \" or \"No\"");}
						}
						validInput = false;
						}
					else if(user.seatClass.equals(seatClassInput) && user.isWindow.equals(isWindowInput) && user.isTable.equals(isTableInput) && user.eMail.equals("free")){
						System.out.println("There are no seats that match your criteria under £" + seatPriceInput + " however we have found one for £" + user.seatPrice + ", would you like to reserve it?");
						reserveInput = console.nextLine();
					}
					if (reserveInput.equalsIgnoreCase("yes")) {
						System.out.println("Enter Email Address: ");
						String emailInput = console.nextLine();
				        fileContent.set(lineNumber, user.seatNum + " " + user.seatClass + " " + user.isWindow + " " + user.isAisle + " " + user.isTable + " " + user.seatPrice + " " + emailInput);//Creates a list with new data to overwrite existing data in file
				        String fileContentString = String.join("\n",fileContent);//Converts list to string ready to overwrite existing file
				        Files.write(path, fileContentString.getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);//Clears existing text file, converts fileContentString to Bytes to prevent blank line from being added to end of file which causes issues, then writes to file
				        System.out.println("Seat has been booked!");
				        users.clear(); //Clears ArrayList to be used again
				        fileContent.clear(); //Clears list to be used again
				        read.close(); 
				        read=null; //Deletes FileReader so it can be reinitialised with the latest data from the file
				        file.close();
				        file=null; // //Deletes scanner so it can be reinitialised with the latest data from the file
				        lineNumber = -1; //Resets line number
				        exitPrompt();
				        break;
					}
				}
				if (reserveInput.equalsIgnoreCase("no")){
					System.out.println("No matches found!");
					exitPrompt();
					}
				break;
				case "2":
					System.out.println("Enter Email Address: ");
					String emailInput = console.nextLine();
					for(User user : users) {
						lineNumber++;
						if(user.eMail.equals(emailInput)) {
							System.out.println("There is a booking for " + user.seatNum + " under that email address, would you like to cancel it?");
							String cancelInput = console.nextLine();
							if (cancelInput.equals("yes")) {
								fileContent.set(lineNumber, user.seatNum + " " + user.seatClass + " " + user.isWindow + " " + user.isAisle + " " + user.isTable + " " + user.seatPrice + " free");//Creates a list with new data to overwrite existing data in file
								String fileContentString = String.join("\n",fileContent);//Converts list to string ready to overwrite existing file
						        Files.write(path, fileContentString.getBytes("UTF-8"), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);//Clears existing text file, converts fileContentString to Bytes to prevent blank line from being added to end of file which causes issues, then writes to file
						        System.out.println("Booking cancelled!");
						        exitPrompt();
						        break;
							}
						}
						else {
							System.out.println("No matches found!");
							exitPrompt();
							break;
						}
				}
				break;
				case "3":
					for(User user : users) {
						System.out.println("Seat Number: " + user.seatNum + "\nSeat Class: " + user.seatClass + "\nNext to window: " + user.isWindow + "\nTable: " + user.isTable + "\nPrice: " + user.seatPrice + "\n");
			}
					exitPrompt();
					break;
				case "q":
					System.exit(0);//Quits program
		}
			}
		
	}
}
