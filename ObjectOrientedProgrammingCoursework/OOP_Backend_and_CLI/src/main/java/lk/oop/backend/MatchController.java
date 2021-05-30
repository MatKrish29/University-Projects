package lk.oop.backend;

import lk.oop.cli.FootballClub;
import lk.oop.cli.FootballMatch;
import lk.oop.cli.LeagueManager;
import lk.oop.cli.PremierLeagueManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//Reference: https://www.javatpoint.com/spring-boot-annotations

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/")
public class MatchController {
	LeagueManager leagueManager = PremierLeagueManager.getLeagueManagerInstance();

	//Sending date sorted match list
	@GetMapping("matches")
	public List<FootballMatch> getMatches() {
		leagueManager.getFootballClubList().clear();
		leagueManager.getMatchList().clear();
		try {
			leagueManager.loadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(leagueManager.getMatchList());
		return leagueManager.getMatchList();
	}

	//Sending generated random match
	@GetMapping("random/match")
	public List<FootballMatch> getRandomMatch() {
		leagueManager.getFootballClubList().clear();
		leagueManager.getMatchList().clear();
		try {
			leagueManager.loadStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<String> clubNameList = new ArrayList<>();
		List<FootballMatch> randomMatchList = new ArrayList<>();
		//Random match won't be generated if maximum matches have been played between available clubs
		while (leagueManager.getMatchList().size() != (leagueManager.getFootballClubList().size() - 1)
				* leagueManager.getFootballClubList().size()){
			//Adding the club names into a separate list
			for(FootballClub club : leagueManager.getFootballClubList()) {
				clubNameList.add(club.getClubName());
			}

			Random random = new Random();

			//Getting random club names
			int randomClub1 = random.nextInt(clubNameList.size());
			int randomClub2 = random.nextInt(clubNameList.size());
			String club1 = clubNameList.get(randomClub1);
			String club2 = clubNameList.get(randomClub2);
			boolean matchIsPlayed = false;

			//Club1 cannot be equal to club2
			if (club1.equals(club2)) {
				continue;
			}
			//Checking for played match between clubs
			for (FootballMatch footballMatch : leagueManager.getMatchList()) {
				if (footballMatch.getClub1().equals(club1) && footballMatch.getClub2().equals(club2)) {
					matchIsPlayed = true;
					break;
				}
			}
			//If match is played generating another set of club names
			if (matchIsPlayed) {
				continue;
			}

			//Generating random goals and random date
			int randomGoal1 = random.nextInt(9);
			int randomGoal2 = random.nextInt(9);
			int randomDay = random.nextInt(32);
			int randomMonth = random.nextInt(13);
			int randomYear = random.nextInt(2) + 2019;
			String sDate = randomDay + "-" + randomMonth + "-" + randomYear;
			Date date = null;
			//Converting string to date
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
				simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				date = simpleDateFormat.parse(sDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//Adding generated random match to matches
			FootballMatch randomMatch = new FootballMatch(club1,randomGoal1, club2, randomGoal2, date);
			leagueManager.addMatch(randomMatch);
			randomMatchList.add(randomMatch);
			break;
		}
		return randomMatchList;
	}
}
