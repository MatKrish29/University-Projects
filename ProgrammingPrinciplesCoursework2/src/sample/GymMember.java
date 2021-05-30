package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GymMember {
    private final SimpleIntegerProperty membershipNumber;
    private final SimpleStringProperty name;
    private final SimpleStringProperty startMembershipDate;
    private final SimpleStringProperty memberType;
    private final SimpleStringProperty schoolName;
    private final SimpleStringProperty age;

    //Constructor to call a ready to use object(immutable) with initialized values
    GymMember(Integer membershipNumber, String name, String startMembershipDate, String memberType, String schoolName,
              String age) {
        this.membershipNumber = new SimpleIntegerProperty(membershipNumber);
        this.name = new SimpleStringProperty(name);
        this.startMembershipDate = new SimpleStringProperty(startMembershipDate);
        this.memberType = new SimpleStringProperty(memberType);
        this.schoolName = new SimpleStringProperty(schoolName);
        this.age = new SimpleStringProperty(age);
    }

    //Getters

    public int getMembershipNumber() {

        return membershipNumber.get();
    }

    public String getName() {

        return name.get();
    }

    public String getStartMembershipDate() {

        return startMembershipDate.get();
    }

    public String getMemberType() {

        return memberType.get();
    }

    public String getSchoolName() {

        return schoolName.get();
    }

    public String getAge() {

        return age.get();
    }
}
