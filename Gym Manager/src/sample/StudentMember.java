package sample;

public class StudentMember extends DefaultMember {
    private String schoolName;

    //Setter
    public void setSchoolName(String schoolName) {

        this.schoolName = schoolName;
    }

    //Getter
    public String getSchoolName() {

        return schoolName;
    }

    //Displaying the details in the console
    public void getDetails() {
        getDetailsDefault();
        System.out.println("School Name           : " + schoolName);
    }
}
