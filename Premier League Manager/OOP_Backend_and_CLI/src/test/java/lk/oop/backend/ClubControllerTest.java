package lk.oop.backend;

import lk.oop.cli.FootballClub;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClubControllerTest {
	List<FootballClub> testList = new ArrayList<>();

	private void CreateTestClubList() {
		FootballClub testClub1 = new FootballClub("Arsenal", null, 0, 0);
		FootballClub testClub2 = new FootballClub("Chelsea", null, 0, 0);
		FootballClub testClub3 = new FootballClub("Liverpool", null, 0, 0);
		FootballClub testClub4 = new FootballClub("Brighton", null, 0, 0);

		testClub1.setWins(2);
		testClub2.setWins(4);
		testClub3.setWins(2);
		testClub4.setWins(5);

		testClub1.setGoalsScored(12);
		testClub2.setGoalsScored(22);
		testClub3.setGoalsScored(16);
		testClub4.setGoalsScored(11);

		testClub1.setGoalsReceived(32);
		testClub2.setGoalsReceived(19);
		testClub3.setGoalsReceived(13);
		testClub4.setGoalsReceived(25);

		testClub1.setPoints();
		testClub2.setPoints();
		testClub3.setPoints();
		testClub4.setPoints();

		testList.add(testClub1);
		testList.add(testClub2);
		testList.add(testClub3);
		testList.add(testClub4);
	}

	@Test
	void getClubs() {
		CreateTestClubList();
		List<FootballClub> pointSortedTestList = new ArrayList<>();
		pointSortedTestList.add(testList.get(3));
		pointSortedTestList.add(testList.get(1));
		pointSortedTestList.add(testList.get(2));
		pointSortedTestList.add(testList.get(0));

		FootballClub.required = true;
		Collections.sort(testList, Collections.reverseOrder());
		assertEquals(pointSortedTestList, testList);
	}

	@Test
	void getWinSortedClubs() {
		CreateTestClubList();
		List<FootballClub> pointSortedTestList = new ArrayList<>();
		pointSortedTestList.add(testList.get(3));
		pointSortedTestList.add(testList.get(1));
		pointSortedTestList.add(testList.get(0));
		pointSortedTestList.add(testList.get(2));

		FootballClub.required = false;
		Collections.sort(testList, new WinsComparator().reversed());
		assertEquals(pointSortedTestList, testList);
	}

	@Test
	void getGoalSortedClubs() {
		CreateTestClubList();
		List<FootballClub> pointSortedTestList = new ArrayList<>();
		pointSortedTestList.add(testList.get(1));
		pointSortedTestList.add(testList.get(2));
		pointSortedTestList.add(testList.get(0));
		pointSortedTestList.add(testList.get(3));

		FootballClub.required = false;
		Collections.sort(testList, new ScoredGoalsComparator().reversed());
		assertEquals(pointSortedTestList, testList);
	}
}