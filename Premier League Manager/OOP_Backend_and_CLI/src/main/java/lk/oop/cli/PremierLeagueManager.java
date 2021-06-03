package lk.oop.cli;

import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class PremierLeagueManager implements LeagueManager {
	private int maxClubCount = 20;
	private List<FootballClub> footballClubList = new ArrayList<>();
	private List<FootballMatch> matchList = new ArrayList<>();
	private static LeagueManager leagueManager = null;
	public static boolean deleteSuccessful;
	public static boolean displaySuccessful;
	private File file = new File("C:\\Users\\krish\\IdeaProjects\\OOP_CWK\\OOP_Backend_and_CLI\\src\\main\\java\\PremierLeagueStats.txt");

	//Constructor
	private PremierLeagueManager() {
	
	}

	//Creating only one object by synchronizing and locking the class (Only one thread)
	public static LeagueManager getLeagueManagerInstance() {
		if (leagueManager == null) {
			synchronized (PremierLeagueManager.class) {
				if (leagueManager == null) {
					leagueManager = new PremierLeagueManager();
				}
			}
		}
		return leagueManager;
	}

	//Getters
	@Override
	public List<FootballClub> getFootballClubList() {
		return footballClubList;
	}

	@Override
	public List<FootballMatch> getMatchList() {
		return matchList;
	}

	@Override
	public int getMaxClubCount() {
		return maxClubCount;
	}

	//Method to add a club to the premier league
	@Override
	public void createClub(SportsClub club) {
		//Adding the created object into the football club list
		footballClubList.add((FootballClub) club);
		try {
			//Saving in the file
			saveStats();
			System.out.println("\nClub Successfully Added!");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Method to relegate a club from the premier league
	@Override
	public void deleteClub(String clubName) {
		//Finding the club in the football club list using for loop and removing it
		for (FootballClub club : footballClubList) {
			if (club.getClubName().equals(clubName)) {
				footballClubList.remove(club);
				try {
					//Saving the updated stats in the file
					saveStats();
					System.out.println("\nClub Successfully Deleted!");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				//Sending feedback to the console when the object is found to delete
				deleteSuccessful = true;
				return;
			}
		}
		System.out.println("Invalid Club Name!\n");
		//Sending feedback to the console when the object is not found
		deleteSuccessful = false;
	}

	//Method to display the stats of a club
	@Override
	public void displayClubStats(String clubName) {
		//Finding the club in the football club list using for loop and printing it
		for (FootballClub club : footballClubList) {
			if (clubName.equals(club.getClubName())) {
				System.out.println("\n========CLUB DETAILS========");
				System.out.println(club + "\n");
				//Sending feedback to the console when the object is found to display
				displaySuccessful = true;
				return;
			}
		}
		System.out.println("Invalid Club Name!\n");
		//Sending feedback to the console when the object is not found
		displaySuccessful = false;
	}

	//Method to display league table 
	@Override
	public void displayLeagueTable() {
		//Sorting the football club list in descending order
		Collections.sort(footballClubList, Collections.reverseOrder());
		System.out.println("   Club Name             Matches       Wins       Defeats     Draws    Goals Scored     Goals Received     Points");
		System.out.println("===============          =======       ====       =======     =====    ============     ==============     ======");

		//Looping through the list to print all the required stats
		for (FootballClub club : footballClubList) {
			String clubName = club.getClubName();
			int matches = club.getNoOfMatches();
			int wins = club.getWins();
			int defeats = club.getDefeats();
			int draws = club.getDraws();
			int goalsScored = club.getGoalsScored();
			int goalsReceived = club.getGoalsReceived();
			int points = club.getPoints();

			//Displaying the stats with better allignment
			System.out.format("%-27s%-13s%-13s%-11s%-12s%-18s%-15s%-14s\n", clubName, matches, wins, defeats, draws, goalsScored, goalsReceived, points);
		}
	}
	
	//Method to add a new match
	@Override
	public void addMatch(FootballMatch match) {
		//Adding to the match list
		matchList.add(match);
		//Looping through the football club list to find the relevant clubs to update the stats
		for (FootballClub club : footballClubList) {
			//Goals scored by both the clubs are used to update goals scored and goals recieved stats of both the clubs
			if (club.getClubName().equals(match.getClub1())) {
				updateStats(club, match.getClub1Goals(), match.getClub2Goals());
			}
			else if (club.getClubName().equals(match.getClub2())) {
				updateStats(club, match.getClub2Goals(), match.getClub1Goals());
			}
		}
		try {
			//Saving the updated stats in the file
			saveStats();
			System.out.println("\nMatch Successfully Added!");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Method to update the stats
	private void updateStats(FootballClub club, int goalsScored, int goalsRecieved) {
		//Updating goal counts of the club
		club.setGoalsScored(club.getGoalsScored() + goalsScored);
		club.setGoalsReceived(club.getGoalsReceived() + goalsRecieved);
		//Updating the number of win/defeat/draw of the club based on the goals scored
		if (goalsScored > goalsRecieved) {
			club.setWins(club.getWins() + 1);
		}
		else if (goalsScored < goalsRecieved) {
			club.setDefeats(club.getDefeats() + 1);
		}
		else {
			club.setDraws(club.getDraws() + 1);
		}
		//Updating the points and matches of the club
		club.setPoints();
		club.setNoOfMatches();
	}

	//Method to save in the file
	@Override
	public void saveStats() throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		//Writing football club objects
		for (FootballClub club : footballClubList) {
			oos.writeObject(club);
		}

		//Writing football match objects
		for (FootballMatch match : matchList) {
			oos.writeObject(match);
		}

		fos.close();
		oos.close();
	}

	//Method to read the file
	@Override
	public void loadStats() throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		//Infinite loop
		for (;;) {
			try {
				Object object = ois.readObject();
				//Reading football club objects
				if (object instanceof FootballClub) {
					FootballClub club = (FootballClub) object;
					footballClubList.add(club);
				}
				//Reading football match objects
				else {
					FootballMatch match = (FootballMatch) object;
					matchList.add(match);
				}
			}
			//Loop breaks when there is an End Of File Exception or Class Not Found Exception
			catch (EOFException e) {
				break;
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
		}

		fis.close();
		ois.close();
	}
}