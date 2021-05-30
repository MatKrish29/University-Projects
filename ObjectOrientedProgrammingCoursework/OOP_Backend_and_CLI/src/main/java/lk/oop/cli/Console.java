package lk.oop.cli;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TimeZone;

public class Console {
	public static void main(String[] args) throws IOException {
		//Getting the league manager object
		LeagueManager leagueManager = PremierLeagueManager.getLeagueManagerInstance();

		//Reading the file
		leagueManager.loadStats();

		Scanner sc;

		System.out.println("\nWelcome to Premier League!");

		while (true) {
			System.out.println("\n============INSTRUCTIONS============");
			System.out.println(">>> Press 1 to create a club.");
			System.out.println(">>> Press 2 to delete a club.");
			System.out.println(">>> Press 3 to display club stats.");
			System.out.println(">>> Press 4 to display points table.");
			System.out.println(">>> Press 5 to add a match.");
			System.out.println(">>> Press 6 to invoke GUI.");
			System.out.println(">>> Press 7 to exit.");

			int userInput;
			//Checking for non-integer values
			try {
				sc = new Scanner(System.in);
				userInput = sc.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Integer Value Expected!");
				continue;
			}

			//Reference: https://www.javatpoint.com/java-regex
			//To check non-integer input
			String regex1 = "(.)*(\\d)(.)*";
			//To check alphabetical input
			String regex2 = "^[a-zA-Z ]*$";

			switch (userInput) {
				//To add a club
				case 1:
					String clubName;
					String clubLocation;
					int memberCount;
					double memberFee;

					//Checking the maximum clubs count
					if (leagueManager.getFootballClubList().size() == leagueManager.getMaxClubCount()) {
						System.out.println("No more clubs can be added!");
						continue;
					}

					while (true) {
						boolean present = false;
						sc = new Scanner(System.in);
						System.out.print("Club Name: ");
						clubName = sc.nextLine().toUpperCase();
						//Checking whether the club is already available or not
						for (FootballClub club : leagueManager.getFootballClubList()) {
							if (clubName.equals(club.getClubName())) {
								System.out.println("This club is already added!\n");
								present = true;
								break;
							}
						}
						//If the club is already present asking the user for another input
						if (present) {
							continue;
						}
						//Checking whether club name is a single character or not and asking for another input
						if (clubName.length() < 2) {
							System.out.println("Club Name should have at least 2 letters!\n");
							continue;
						}
						//Validating the club name using regex
						if (validateString(clubName, regex1, regex2, "Club Name")) {
							break;
						}
					}

					do {
						sc = new Scanner(System.in);
						System.out.print("Club Location: ");
						clubLocation = sc.nextLine();
					}
					//Validating club location using regex and asking for another input
					while (!validateString(clubLocation, regex1, regex2, "Club Location"));

					while (true) {
						sc = new Scanner(System.in);
						//Checking for non-integer input
						try {
							System.out.print("Member Count: ");
							memberCount = sc.nextInt();
							//Checking whether the value is leass than zero or not
							if (validatePrimitiveType(memberCount, "Member Count!\n")) {
								break;
							}
						}
						catch (InputMismatchException e) {
							System.out.println("Integer Value Expected!\n");
						}
					}

					while (true) {
						sc = new Scanner(System.in);
						//Checking for non-integer input
						try {
							System.out.print("Membership Fee: ");
							memberFee = sc.nextInt();
							//Checking whether the value is leass than zero or not
							if (validatePrimitiveType(memberFee, "Membership Fee!\n")) {
								break;
							}
						}
						catch (InputMismatchException e) {
							System.out.println("Double Value Expected!\n");
						}
					}

					SportsClub club = new FootballClub(clubName, clubLocation, memberCount, memberFee);

					leagueManager.createClub(club);
					break;

				//To delete a club
				case 2:
					//Checking whether there's any club to delete
					if (leagueManager.getFootballClubList().size() == 0) {
						System.out.println("There's no club to delete!");
						continue;
					}

					String cName;

					//Asking user for the input until a valid club name is given(PremierLeagueManager will check and return true if name is valid)
					while (!PremierLeagueManager.deleteSuccessful) {
						sc = new Scanner(System.in);
						System.out.print("Club Name: ");
						cName = sc.nextLine().toUpperCase();
						leagueManager.deleteClub(cName);
					}
					PremierLeagueManager.deleteSuccessful = false;
					break;

				//To display club stats
				case 3:
					leagueManager.getFootballClubList().clear();
					leagueManager.getMatchList().clear();
					leagueManager.loadStats();

					//Checking whether there's any club to display
					if (leagueManager.getFootballClubList().size() == 0) {
						System.out.println("There's no club to display!");
						continue;
					}

					String name;

					//Asking user for the input until a valid club name is given(PremierLeagueManager will check and return true if name is valid)
					while(!PremierLeagueManager.displaySuccessful) {
						sc = new Scanner(System.in);
						System.out.print("Club Name: ");
						name = sc.nextLine().toUpperCase();
						leagueManager.displayClubStats(name);
					}
					PremierLeagueManager.displaySuccessful = false;
					break;

				//To display league table
				case 4:
					leagueManager.getFootballClubList().clear();
					leagueManager.getMatchList().clear();
					leagueManager.loadStats();

					//Checking whether there's any club to display
					if (leagueManager.getFootballClubList().size() == 0) {
						System.out.println("There are no clubs available!");
						continue;
					}
					leagueManager.displayLeagueTable();
					break;

				//To add a new match
				case 5:
					leagueManager.getFootballClubList().clear();
					leagueManager.getMatchList().clear();
					leagueManager.loadStats();
					//Checking whether there is enough clubs to play a match
					if (leagueManager.getFootballClubList().size() < 2) {
						System.out.println("A match cannot be played without at least two clubs!");
						continue;
					}

					//Checking whether maximum number of matches have been played by all the clubs
					if (leagueManager.getMatchList().size() == (leagueManager.getFootballClubList().size() - 1) * leagueManager.getFootballClubList().size()) {
						System.out.println("No more matches can be played between these available clubs!");
						continue;
					}

					String clName1;
					String clName2;
					int club1Goals;
					int club2Goals;
					String stringDate;
					Date date;

					//Regex for date format
					//Reference: https://stackoverflow.com/questions/42355995/regex-to-match-date-formats-dd-mm-yyyy-and-dd-mm-yyyy
					String dateRegex = "(([0-2][0-9])|(3)[0-1])[-](((0)[0-9])|((1)[0-2]))[-]([0-9]{4})";
					boolean matchIsNotPlayed = true;

					do {
						do {
							sc = new Scanner(System.in);
							System.out.print("Home Club: ");
							clName1 = sc.nextLine().toUpperCase();
						}
						//Looping until valid club is given
						while (validateClub(clName1, leagueManager));

						do {
							while (true) {
								sc = new Scanner(System.in);
								System.out.print("Away Club: ");
								clName2 = sc.nextLine().toUpperCase();
								//Checking whether club1 and club2 are same
								if (!clName2.equals(clName1)) {
									break;
								}
								System.out.println(clName2 + " has already been given as the home club!\n");
							}
						}
						//Looping until valid club is given
						while (validateClub(clName2, leagueManager));
																
						//Checking whether a match between the given two clubs has been already played before
						for (FootballMatch footballMatch : leagueManager.getMatchList()) {
							if (footballMatch.getClub1().equals(clName1) && footballMatch.getClub2().equals(clName2)) {
								System.out.println("Match between " + clName1 + " and " + clName2 + " has already been played at " + clName1 + "'s home ground!");
								matchIsNotPlayed = false;
								break;
							}
							matchIsNotPlayed = true;
						}
					}
					//Looping until two clubs are given for a new match which has not already been played before
					while (!matchIsNotPlayed);
					
					//Asking for goal scored by first club until valid input is given
					while (true) {
						try {
							sc = new Scanner(System.in);
							System.out.print("Goals Scored by " + clName1 + ": ");
							club1Goals = sc.nextInt();
							//Validating goal count
							if (validateGoals(club1Goals)) {
								continue;
							}
							break;
						}
						catch (InputMismatchException e) {
							System.out.println("Integer Value Expected!\n");
						}
					}

					//Asking for goal scored by second club until valid input is given
					while (true) {
						try {
							sc = new Scanner(System.in);
							System.out.print("Goals Scored by " + clName2 + ": ");
							club2Goals = sc.nextInt();
							//Validating goal count
							if (validateGoals(club2Goals)) {
								continue;
							}
							break;
						}
						catch (InputMismatchException e) {
							System.out.println("Integer Value Expected!\n");
						}
					}

					sc.nextLine();

					//Asking for date input until valid date format is given
					while (true) {
						sc = new Scanner(System.in);
						System.out.print("Date (DD-MM-YYYY): ");
						stringDate = sc.nextLine();
						//Converting string to date
						try {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
							simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
							date = simpleDateFormat.parse(stringDate);
						}
						catch (ParseException e) {
							System.out.println("Invalid Date Format!\n");
							continue;
						}
						//Checking for valid date
						if (!stringDate.matches(dateRegex)) {
							System.out.println("Invalid Date!\n");
							continue;
						}
						//Creating match object and adding match
						FootballMatch match = new FootballMatch(clName1, club1Goals, clName2, club2Goals, date);
						leagueManager.addMatch(match);
						break;
					}
					break;

				//To invoke GUI
				case 6:
					runCmd();
					break;

				//To exit the application
				case 7:
					System.out.println("\nHave a good day :)");
					System.exit(0);

				//Reprompting the instructions if invalid input is given
				default:
					System.out.println("\nInvalid Input!");
			}
		}
	}

	//Method to validate club name and club location
	//Method will return true if club name or club location is valid
	private static boolean validateString(String clubStat, String regex1, String regex2, String message) {
		boolean bool = false;
		//Checking for non-integer input
		if (clubStat.matches(regex1)) {
			System.out.println(message + " should be a string!\n");
		}
		//Checking for alphabetical characters
		else if (!clubStat.matches(regex2)) {
			System.out.println(message + " should contain only letters!\n");
		}
		//Checking for empty value
		else if (clubStat.equals("")) {
			System.out.println(message + " should not be a empty!\n");
		}
		else {
			bool = true;
		}
		return bool;
	}

	//Method to validate club member count and club membership fee
	//Method will return true if club member count or club membership fee is valid
	private static boolean validatePrimitiveType(double value, String message) {
		boolean bool = false;
		//Checking for positive value
		if (value > 0) {
			bool = true;
		}
		else {
			System.out.println("Impossible " + message);
		}
		return bool;
	}

	//Method to validate club goals
	//Method will return true if club goal is invalid
	private static boolean validateGoals(int goals) {
		boolean bool = false;
		//Checking for positive value
		if (goals < 0) {
			System.out.println("Impossible Goal Count!\n");
			bool = true;
		}
		//Checking for a reasonable value
		if (goals > 8) {
			System.out.println("Not A Reasonable Goal Count!\n");
			bool = true;
		}
		return bool;
	}

	//Method to validate new match club
	//Method will return true if the new match club is invalid
	private static boolean validateClub(String name, LeagueManager leagueManager) {
		boolean bool = false;
		boolean found = false;
		boolean maxPlayed = false;
		//Looping through football club list
		for (FootballClub footballClub : leagueManager.getFootballClubList()) {
			//If the club is available, checking whether the club has played maximum number of matches
			if (footballClub.getClubName().equals(name)) {
				if (footballClub.getNoOfMatches() == (leagueManager.getFootballClubList().size() - 1) * 2) {
					System.out.println(name + " club has played maximum number of matches between the available clubs!\n");
					maxPlayed = true;
					bool = true;
					break;
				}
				found = true;
				break;
			}
		}
		//Checking whether the club is in the premier league
		if (!found && !maxPlayed) {
			bool = true;
			System.out.println("There's no club called " + name + "!\n");
		}
		return bool;
	}

	//Method to invoke GUI from command prompt
	//Reference: https://www.geeksforgeeks.org/java-program-open-command-prompt-insert-commands/
	private static void runCmd() {
		try {
			//Setting the path and command for frontend
			String runFrontend = "cmd /c start cmd.exe /K \"dir && cd ../../../../OOP-Frontend && ng serve --open\"";
			//Setting the path and command for backend
			String runBackend = "cmd /c start cmd.exe /K \"dir && cd ../../../ && java -jar target/backend-0.0.1-SNAPSHOT.jar\"";

			//Executing backend command
			Runtime.getRuntime().exec(runBackend);
			//Executing frontend command
			Runtime.getRuntime().exec(runFrontend);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}