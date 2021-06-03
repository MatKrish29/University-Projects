package lk.oop.cli;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PremierLeagueManagerTest {

	@Test
	void createClub() {
		FootballClub testClub = new FootballClub("Manchester City", "Manchester", 40000,
				3000);

		List<FootballClub> testList = new ArrayList<>();
		testList.add(testClub);

		for (FootballClub testObject : testList) {
			assertEquals("Manchester City", testObject.getClubName());
			assertEquals("Manchester", testObject.getClubLocation());
			assertEquals(40000, testObject.getMemberCount());
			assertEquals(3000, testObject.getMembershipFee());
		}
	}

	@Test
	void deleteClub() {
		FootballClub testClub1 = new FootballClub("Manchester City", "Manchester", 40000,
				3000);
		FootballClub testClub2 = new FootballClub("Manchester United", "Manchester", 30000,
				3500);

		List<FootballClub> testList = new ArrayList<>();
		List<FootballClub> expectedList = new ArrayList<>();
		testList.add(testClub1);
		testList.add(testClub2);
		expectedList.add(testClub2);

		String name = "Manchester City";
		for (FootballClub testObject: testList) {
			if (testObject.getClubName().equals(name)) {
				testList.remove(testObject);
			}
		}
		assertEquals(expectedList, testList);
	}


	@Test
	void addMatch() {
		FootballClub testClub1 = new FootballClub("Manchester City", "Manchester", 40000,
				3000);
		FootballClub testClub2 = new FootballClub("Manchester United", "Manchester", 30000,
				3500);

		List<FootballClub> testList = new ArrayList<>();
		testList.add(testClub1);
		testList.add(testClub2);

		for (FootballClub testClub : testList) {
			if (testClub.getClubName().equals("Manchester City")) {
				updateStats(testClub, 5, 3);
			}
			else if (testClub.getClubName().equals("Manchester United")) {
				updateStats(testClub, 3, 5);
			}
		}

		assertEquals(1, testClub1.getWins());
		assertEquals(0, testClub1.getDefeats());
		assertEquals(0, testClub1.getDraws());
		assertEquals(5, testClub1.getGoalsScored());
		assertEquals(3, testClub1.getGoalsReceived());
		assertEquals(3, testClub1.getPoints());
		assertEquals(1, testClub1.getNoOfMatches());

		assertEquals(0, testClub2.getWins());
		assertEquals(1, testClub2.getDefeats());
		assertEquals(0, testClub2.getDraws());
		assertEquals(3, testClub2.getGoalsScored());
		assertEquals(5, testClub2.getGoalsReceived());
		assertEquals(0, testClub2.getPoints());
		assertEquals(1, testClub2.getNoOfMatches());
	}

	private void updateStats(FootballClub club, int goalsScored, int goalsReceived) {
		club.setGoalsScored(club.getGoalsScored() + goalsScored);
		club.setGoalsReceived(club.getGoalsReceived() + goalsReceived);
		if (goalsScored > goalsReceived) {
			club.setWins(club.getWins() + 1);
		}
		else if (goalsScored < goalsReceived) {
			club.setDefeats(club.getDefeats() + 1);
		}
		else {
			club.setDraws(club.getDraws() + 1);
		}
		club.setPoints();
		club.setNoOfMatches();
	}
}