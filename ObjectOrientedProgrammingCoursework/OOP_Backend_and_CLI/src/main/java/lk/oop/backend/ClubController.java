package lk.oop.backend;

import lk.oop.cli.FootballClub;
import lk.oop.cli.LeagueManager;
import lk.oop.cli.PremierLeagueManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//Reference: https://www.javatpoint.com/spring-boot-annotations

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/")
public class ClubController {
	LeagueManager leagueManager = PremierLeagueManager.getLeagueManagerInstance();

	//Sending points/goals difference sorted list
	@GetMapping("clubs")
	public List<FootballClub> getClubs() {
		leagueManager.getFootballClubList().clear();
		leagueManager.getMatchList().clear();
		try {
			leagueManager.loadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FootballClub.required = true;
		Collections.sort(leagueManager.getFootballClubList(), Collections.reverseOrder());
		return leagueManager.getFootballClubList();
	}

	//Sending wins sorted list
	@GetMapping("clubs/wins/sorted")
	public List<FootballClub> getWinSortedClubs() {
		leagueManager.getFootballClubList().clear();
		leagueManager.getMatchList().clear();
		try {
			leagueManager.loadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FootballClub.required = false;
		Collections.sort(leagueManager.getFootballClubList(), new WinsComparator().reversed());
		return leagueManager.getFootballClubList();
	}

	//Sending scored goals sorted list
	@GetMapping("clubs/goals/sorted")
	public List<FootballClub> getGoalSortedClubs() {
		leagueManager.getFootballClubList().clear();
		leagueManager.getMatchList().clear();
		try {
			leagueManager.loadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FootballClub.required = false;
		Collections.sort(leagueManager.getFootballClubList(), new ScoredGoalsComparator().reversed());
		return leagueManager.getFootballClubList();
	}
}
