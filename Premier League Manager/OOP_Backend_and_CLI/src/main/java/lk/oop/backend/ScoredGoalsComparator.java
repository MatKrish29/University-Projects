package lk.oop.backend;

import lk.oop.cli.FootballClub;

import java.util.Comparator;

public class ScoredGoalsComparator implements Comparator<FootballClub> {
	@Override
	public int compare(FootballClub o1, FootballClub o2) {
		FootballClub.required = false;
		return o1.getGoalsScored() - o2.getGoalsScored();
	}
}
