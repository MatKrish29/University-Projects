package lk.oop.cli;

import java.io.Serializable;
import java.util.Date;

//A class to create match objects and access match details
public class FootballMatch implements Serializable, Comparable<FootballMatch> {
	private static final long serialVersionUID = -3543925676464349459L;
	private String club1;
	private String club2;
	private int club1Goals;
	private int club2Goals;
	private Date date;

	//Constructor
	public FootballMatch(String club1, int club1Goals, String club2, int club2Goals, Date date) {
		this.club1 = club1;
		this.club2 = club2;
		this.club1Goals = club1Goals;
		this.club2Goals = club2Goals;
		this.date = date;
	}

	//Setters
	public void setClub1(String club1) {
		this.club1 = club1;
	}

	public void setClub2(String club2) {
		this.club2 = club2;
	}

	public void setClub1Goals(int club1Goals) {
		this.club1Goals = club1Goals;
	}

	public void setClub2Goals(int club2Goals) {
		this.club2Goals = club2Goals;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//Getters	
	public String getClub1() {
		return club1;
	}

	public String getClub2() {
		return club2;
	}

	public int getClub1Goals() {
		return club1Goals;
	}

	public int getClub2Goals() {
		return club2Goals;
	}

	public Date getDate() {
		return date;
	}

	//Overriding toString method
	@Override
	public String toString() {
		return "\nHome Club: " + club1 + "\nAway Club: " + club2 + "\n" + club1 + " Goals: " + club1Goals + "\n" + club2 + " Goals: " + club2Goals + "\nDate: " + date;
	}

	//Overriding compareTo method
	@Override
	//Comparing the date 
	public int compareTo(FootballMatch match) {
		return this.getDate().compareTo(match.getDate());
	}
}