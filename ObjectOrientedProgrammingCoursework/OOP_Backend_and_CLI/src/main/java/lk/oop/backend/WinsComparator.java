package lk.oop.backend;

import lk.oop.cli.FootballClub;

import java.util.Comparator;

public class WinsComparator implements Comparator<FootballClub> {
	@Override
	public int compare(FootballClub o1, FootballClub o2) {
		FootballClub.required = false;
		return o1.getWins() - o2.getWins();
	}
}
