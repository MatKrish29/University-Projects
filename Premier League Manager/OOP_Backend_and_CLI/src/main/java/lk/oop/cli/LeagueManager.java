package lk.oop.cli;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface LeagueManager {

	//Getters
	int getMaxClubCount();
	List<FootballClub> getFootballClubList();
	List<FootballMatch> getMatchList();	

	//The methods a league manager should have 
	void createClub(SportsClub club);
	void deleteClub(String clubName);
	void displayClubStats(String clubName);
	void displayLeagueTable();
	void addMatch(FootballMatch match);
	void saveStats() throws IOException;
	void loadStats() throws IOException;
}