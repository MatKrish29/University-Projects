package lk.oop.cli;

public class FootballClub extends SportsClub implements Comparable<FootballClub> {
	//Football club stats are not initialized in the constructor since all the match details are needed to store accurate match stats

	private static final long serialVersionUID = 149109519942562376L;
	private int wins;
	private int defeats;
	private int draws;
	private int goalsScored;
	private int goalsReceived;
	private int points;
	private int noOfMatches;
	public static boolean required = true;


	//Constructor
	public FootballClub(String clubName, String clubLocation, int memberCount, double membershipFee) {
		super(clubName, clubLocation, memberCount, membershipFee);
	}

	//Setters
	public void setWins(int wins) {
		this.wins = wins;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	public void setGoalsReceived(int goalsReceived) {
		this.goalsReceived = goalsReceived;
	}

	public void setPoints() {
		this.points = (this.wins * 3) + this.draws;
	}

	public void setNoOfMatches() {
		this.noOfMatches = this.wins + this.defeats + this.draws;
	}

	//Getters
	public int getWins() {
		return wins;
	}

	public int getDefeats() {
		return defeats;
	}

	public int getDraws() {
		return draws;
	}

	public int getGoalsScored() {
		return goalsScored;
	}

	public int getGoalsReceived() {
		return goalsReceived;
	}

	public int getPoints() {
		return points;
	}

	public int getNoOfMatches() {
		return noOfMatches;
	}

	//Overriding toString method
	@Override
	public String toString() {
		return super.toString() + "\nWins: " + wins + "\nDefeats: " + defeats + "\nDraws: " + draws + "\nGoals Scored: " + goalsScored + "\nGoals Received: " + goalsReceived + "\nPoints: " + points + "\nMatches Played: " + noOfMatches;
	}

	//Method to get the goal difference of current object
	public int goalDifference() {
		return this.getGoalsScored() - this.getGoalsReceived();
	}

	//Overriding compareTo method
	@Override
	//Comparing the points and goal difference
	public int compareTo(FootballClub footballClub) {
		//The required variable becomes false if sorting according to the points and goal difference is not needed(When sorting scored goals and wins)
		if (required) {
			if (this.getPoints() - footballClub.getPoints() == 0) {
				return this.goalDifference() - footballClub.goalDifference();
			}
			else {
				return this.getPoints() - footballClub.getPoints();
			}
		}
		else {
			return 0;
		}
	}
}