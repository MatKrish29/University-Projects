package lk.oop.cli;

public class SchoolFootballClub extends FootballClub{
	private String schoolName;

	//Constructor
	public SchoolFootballClub(String clubName, String clubLocation, int memberCount, double membershipFee, String schoolName) {
		super(clubName, clubLocation, memberCount, membershipFee);
		this.schoolName = schoolName;
	} 

	//Setter
	public void setSchoolName(String universityName) {
		this.schoolName = schoolName;
	}

	//Getter
	public String getSchoolName() {
		return schoolName;
	}

	//Overriding toString method
	public String toString() {
		return "\nSchool Name: " + schoolName + super.toString();
	}
}