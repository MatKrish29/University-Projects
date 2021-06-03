package lk.oop.cli;

public class UniversityFootballClub extends FootballClub{
	private String universityName;

	//Constructor
	public UniversityFootballClub(String clubName, String clubLocation, int memberCount, double membershipFee, String universityName) {
		super(clubName, clubLocation, memberCount, membershipFee);
		this.universityName = universityName;
	}

	//Setter
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	//Getter
	public String getUniversityName() {
		return universityName;
	}

	//Overriding toString method
	@Override
	public String toString() {
		return "\nUniversity Name: " + universityName + super.toString() ;
	}
}