package lk.oop.cli;

import java.io.Serializable;

public class SportsClub implements Serializable {
	private static final long serialVersionUID = 4320144870245297285L;
	private String clubName;
	private String clubLocation;
	private int memberCount;
	private double membershipFee;

	//Constructor 
	public SportsClub(String clubName, String clubLocation, int memberCount, double membershipFee) {
		this.clubName = clubName;
		this.clubLocation = clubLocation;
		this.memberCount = memberCount;
		this.membershipFee = membershipFee;
	}

	//Setters
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public void setClubLocation(String clubLocation) {
		this.clubLocation = clubLocation;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public void setMembershipFee(double membershipFee) {
		this.membershipFee = membershipFee;
	}

	//Getters
	public String getClubName() {
		return clubName;
	}

	public String getClubLocation() {
		return clubLocation;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public double getMembershipFee() {
		return membershipFee;
	}

	//Overriding toString method
	@Override
	public String toString() {
		return "\nClub Name: " + clubName + "\nClub Location: " + clubLocation + "\nMember Count: " + memberCount + "\nMembership Fee: " + membershipFee;
	}
}