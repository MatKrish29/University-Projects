package sample;

public class DefaultMember {
    private int membershipNumber;
    private String name;
    private String startMembershipDate;

    //Setter
    public void setMembershipNumber(int membershipNumber) {

        this.membershipNumber = membershipNumber;
    }

    //Setter
    public void setName(String name) {

        this.name = name;
    }

    //Setter
    public void setStartMembershipDate(String startMembershipDate) {

        this.startMembershipDate = startMembershipDate;
    }

    //Getter
    public int getMembershipNumber() {

        return membershipNumber;
    }

    //Getter
    public String getName() {

        return name;
    }

    //Getter
    public String getStartMembershipDate() {

        return startMembershipDate;
    }

    //Displaying the details in the console
    public void getDetailsDefault() {
        System.out.println("\nMembership Number     : " + membershipNumber);
        System.out.println("Member Name           : " + name);
        System.out.println("Start Membership Date : " + startMembershipDate);
    }
}


