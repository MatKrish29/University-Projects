package lk.oop.backend;

import lk.oop.cli.FootballMatch;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchControllerTest {

	@Test
	void getMatches() throws ParseException {
		List<FootballMatch> testMatchList = new ArrayList<>();
		List<FootballMatch> expectedTestMatchList = new ArrayList<>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date date1 = simpleDateFormat.parse("20-12-2020");
		Date date2 = simpleDateFormat.parse("30-12-2020");
		Date date3 = simpleDateFormat.parse("12-11-2020");
		Date date4 = simpleDateFormat.parse("28-11-2020");

		FootballMatch testMatch1 = new FootballMatch("Arsenal", 3, "Chelsea", 2, date1);
		FootballMatch testMatch2 = new FootballMatch("Chelsea", 4, "Arsenal", 0, date2);
		FootballMatch testMatch3 = new FootballMatch("Arsenal", 0, "Liverpool", 1, date3);
		FootballMatch testMatch4 = new FootballMatch("Liverpool", 2, "Arsenal", 3, date4);

		testMatchList.add(testMatch1);
		testMatchList.add(testMatch2);
		testMatchList.add(testMatch3);
		testMatchList.add(testMatch4);

		expectedTestMatchList.add(testMatch3);
		expectedTestMatchList.add(testMatch4);
		expectedTestMatchList.add(testMatch1);
		expectedTestMatchList.add(testMatch2);

		Collections.sort(testMatchList);
		assertEquals(expectedTestMatchList, testMatchList);
	}
}