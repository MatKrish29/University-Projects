package sample;

import java.util.ArrayList;
import java.util.Scanner;

public class MyGymManager implements GymManager {
    private int membershipNumber;
    private String name;
    private Date startMembershipDate = new Date();
    private String schoolName;
    private int age;
    Mongo object = new Mongo();

    //Method to get common details from the user
    @Override
    public void commonDetails() {
        int count = 0;
        //Getting the membership numbers and storing them in an array
        ArrayList<Object> memberNumber = new ArrayList<Object>();
        memberNumber = (ArrayList<Object>) object.dataRetrieve("Membership Number");
        //Looping until user inputs a valid input
        while (true) {
            try {
                Scanner sc1 = new Scanner(System.in);
                System.out.print("\nEnter the Membership Number     : ");
                membershipNumber = sc1.nextInt();
                //Validating the membership number input
                if (!(membershipNumber >0 && membershipNumber <=100)) {
                    System.out.println("Membership number should be greater than 0 and less than or equal to 100!");
                    continue;
                }
                else {
                    //Checking whether the membership number already exists
                    for (int i=0; i<object.count; i++) {
                        count = object.count;
                        int number = (int) memberNumber.get(i);
                        if (number == membershipNumber) {
                            count-=1;
                            System.out.println("Membership Number " + number + " already exists!");
                            break;
                        }
                    }
                    //If there's no matching value the count value won't change
                    if (count==object.count) {
                        break;
                    }
                }
            }
            catch (Exception e) {
                System.out.println("Membership number should be an integer!");
            }
        }
        //Looping until user inputs a valid input
        while (true) {
            Scanner sc2 = new Scanner(System.in);
            System.out.print("\nEnter the Member Name           : ");
            name = sc2.nextLine().toLowerCase();
            //Checking whether the name contains any number
            if (name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3")
                    || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7")
                    || name.contains("8") || name.contains("9")) {
                System.out.println("Name cannot have an integer!");
                continue;
            }
            else {
                break;
            }
        }
        //Getting the date input
        startMembershipDate.StartMembershipDate();
    }

    //Method to add default members
    @Override
    public void addDefaultMember() {
        commonDetails();
        //Saving the details in the database
        saveMember("d");
    }

    //Method to add student members
    @Override
    public void addStudentMember() {
        commonDetails();
        //Looping until user inputs a valid input
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print("\nEnter the School Name           : ");
            schoolName = sc.nextLine();
            //Checking whether the name contains any number
            if (schoolName.contains("0") || schoolName.contains("1") || schoolName.contains("2")
                    || schoolName.contains("3") || schoolName.contains("4") || schoolName.contains("5")
                    || schoolName.contains("6") || schoolName.contains("7")
                    || schoolName.contains("8") || schoolName.contains("9")) {
                System.out.println("School name cannot have an integer!");
                continue;
            }
            else {
                break;
            }
        }
        //Saving the details in the database
        saveMember("s");
    }

    //Method to add over60 members
    @Override
    public void addOver60Member() {
        commonDetails();
        //Looping until user inputs a valid input
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print("\nEnter the Age                   : ");
                age = sc.nextInt();
                //Checking whether age is above 60
                if (age<=60) {
                    System.out.println("Member should be above age 60!");
                    continue;
                }
                //Checking whether age is below 115
                if (age>115) {
                    System.out.println("The average maximum human life span is 115 years!");
                    continue;
                }
                else {
                    break;
                }
            }
            catch (Exception e) {
                System.out.println("Age should be an integer!");
            }
        }
        //Saving the details in the database
        saveMember("o");
    }

    //Method to save the details in the database
    @Override
    public void saveMember(String command) {
        //Looping until user inputs a valid input
        while (true) {
            Mongo mongo = new Mongo();
            //Giving user the option to save the member details
            Scanner sc1 = new Scanner(System.in);
            System.out.println("\nDo you want to save this member? (y/n)");
            String userCommand = sc1.nextLine().toLowerCase();
            //Saving over60 member details
            if (userCommand.equals("y")) {
                if (command.equals("o")) {
                    //Setting the values
                    Over60Member object = new Over60Member();
                    object.setMembershipNumber(membershipNumber);
                    object.setName(name);
                    object.setStartMembershipDate(startMembershipDate.getStartMembershipDate());
                    object.setAge(age);
                    //Inserting the details into database
                    mongo.dataInsertOver60Member(object.getMembershipNumber(), object.getName(),
                            object.getStartMembershipDate(), object.getAge());
                    //Printing the added details
                    object.getDetails();
                    //Increasing the document count
                    mongo.count+=1;
                    //Printing a response message
                    System.out.println("--------------------------------------");
                    System.out.println("Over age 60 member added successfully!");
                    System.out.println("Available slots : " + (100-mongo.count));
                    break;
                }
                //Saving student member details
                else if (command.equals("s")) {
                    //Setting the values
                    StudentMember object = new StudentMember();
                    object.setMembershipNumber(membershipNumber);
                    object.setName(name);
                    object.setStartMembershipDate(startMembershipDate.getStartMembershipDate());
                    object.setSchoolName(schoolName);
                    //Inserting the details into database
                    mongo.dataInsertStudentMember(object.getMembershipNumber(), object.getName(),
                            object.getStartMembershipDate(), object.getSchoolName());
                    //Printing the added details
                    object.getDetails();
                    //Increasing the document count
                    mongo.count+=1;
                    //Printing a response message
                    System.out.println("----------------------------------");
                    System.out.println("Student member added successfully!");
                    System.out.println("Available slots : " + (100-mongo.count));
                    break;
                }
                //Saving default member details
                else if (command.equals("d")) {
                    //Setting the values
                    DefaultMember object = new DefaultMember();
                    object.setMembershipNumber(membershipNumber);
                    object.setName(name);
                    object.setStartMembershipDate(startMembershipDate.getStartMembershipDate());
                    //Inserting the details into database
                    mongo.dataInsertDefaultMember(object.getMembershipNumber(), object.getName(),
                            object.getStartMembershipDate());
                    //Printing the added details
                    object.getDetailsDefault();
                    //Increasing the document count
                    mongo.count+=1;
                    //Printing a response message
                    System.out.println("----------------------------------");
                    System.out.println("Default member added successfully!");
                    System.out.println("Available slots : " + (100-mongo.count));
                    break;
                }
            }
            //In case user doesn't want to save the member details
            else if (userCommand.equals("n")) {
                System.out.println("Member didn't get saved!");
                break;
            }
            else {
                System.out.println("Enter a valid input!");
            }
        }
    }

    //Method to delete member details
    @Override
    public void deleteMember() {
        int count = 0;
        int membershipNumber;
        Mongo mongo = new Mongo();
        /*Getting membership numbers to check the availability of the data and member type to let the user know the
        type of member who's deleted*/
        ArrayList<Object> memberNumber = new ArrayList<Object>();
        ArrayList<Object> memberType = new ArrayList<Object>();
        memberNumber = (ArrayList<Object>) mongo.dataRetrieve("Membership Number");
        memberType = (ArrayList<Object>) mongo.dataRetrieve("Member Type");
        //Looping until user inputs a valid input
        while (true) {
            try {
                count = 0;
                Scanner sc1 = new Scanner(System.in);
                System.out.print("\nEnter the membership number of the member you want to delete: ");
                membershipNumber = sc1.nextInt();
                //Validating the user input
                if (!(membershipNumber>0 && membershipNumber<=100)) {
                    System.out.println("Membership number should be greater than 0 and less than or equal to 100!");
                    continue;
                }
                /*Finding and deleting the member by looping through each number and finding the matching one with the
                user input*/
                for (int i=0; i<object.count; i++) {
                    count +=1;
                    int number = (int) memberNumber.get(i);
                    if (number == membershipNumber) {
                        count-=1;
                        Mongo object = new Mongo();
                        object.dataDelete(membershipNumber);
                        //Getting the type of member deleted
                        String type = (String) memberType.get(i);
                        System.out.println("\n" + type + " deleted successfully!");
                        object.count-=1;
                        System.out.println("Remaining slots: " + (100-object.count));
                        break;
                    }
                }
                //If the number doesn't match user count, the count won't change
                if (count==object.count) {
                    System.out.println("Membership Number " + membershipNumber + " doesn't exist!");
                    continue;
                }
                else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Membership number should be an integer!");
            }
        }
    }

    //Method to print member details
    @Override
    public void printMembers() {
        int data1;
        String data2;
        String data3;
        String data4;

        ArrayList<Object> membershipNumber = new ArrayList<Object>();
        ArrayList<Object> name = new ArrayList<Object>();
        ArrayList<Object> startMembershipDate = new ArrayList<Object>();
        ArrayList<Object> memberType = new ArrayList<Object>();

        //Getting all the details and storing them in the array lists
        Mongo object = new Mongo();
        membershipNumber = (ArrayList<Object>) object.dataRetrieve("Membership Number");
        name = (ArrayList<Object>) object.dataRetrieve("Member Name");
        startMembershipDate = (ArrayList<Object>) object.dataRetrieve("Start Membership Date");
        memberType = (ArrayList<Object>) object.dataRetrieve("Member Type");

        System.out.println("Membership Number          Member Name          Start Membership Date            " +
                "Member Type");
        System.out.println("=================          ===========          =====================          " +
                "===============");

        //Looping and printing each data
        for (int i = 0; i < object.count; i++) {
            data1 = (int) membershipNumber.get(i);
            data2 = (String) name.get(i);
            data3 = (String) startMembershipDate.get(i);
            data4 = (String) memberType.get(i);
            System.out.format("%-27s%-26s%-26s%-25s\n", data1, data2, data3, data4);
        }
    }

    //Method to sort details according to the name in the ascending order (Bubble sort)
    @Override
    public void sortNames() {
        String holder;
        int numHolder;
        Mongo mongo = new Mongo();

        mongo.dataRetrieve("Member Name");
        ArrayList<Integer> membershipNumber = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> startMembershipDate = new ArrayList<String>();
        ArrayList<String> memberType = new ArrayList<String>();

        //Retrieving the data in the array lists
        membershipNumber = (ArrayList<Integer>) object.dataRetrieve("Membership Number");
        names =  (ArrayList<String>) object.dataRetrieve("Member Name");
        startMembershipDate = (ArrayList<String>) object.dataRetrieve("Start Membership Date");
        memberType = (ArrayList<String>) object.dataRetrieve("Member Type");

        int memberNumber[] = new int[membershipNumber.size()];
        String name[] = new String[names.size()];
        String startDate[] = new String[startMembershipDate.size()];
        String type[] = new String[memberType.size()];

        //Storing the details in the array lists in arrays for the convenience to do bubble sort
        for (int i=0; i<names.size(); i++) {
            memberNumber[i] = membershipNumber.get(i);
            name[i] = names.get(i);
            startDate[i] = startMembershipDate.get(i);
            type[i] = memberType.get(i);
        }
        //Bubble sort
        for (int i=0; i<names.size(); i++) {
           for (int j=i+1; j<names.size(); j++) {
               /*Swapping the values if the value of the name in the left side (first for loop) is higher than the right
               (second for loop)*/
                if (name[i].compareTo(name[j])>0) {
                    holder = name[i];
                    name[i] = name[j];
                    name[j] = holder;

                    numHolder = memberNumber[i];
                    memberNumber[i] = memberNumber[j];
                    memberNumber[j] = numHolder;

                    holder = startDate[i];
                    startDate[i] = startDate[j];
                    startDate[j] = holder;

                    holder = type[i];
                    type[i] = type[j];
                    type[j] = holder;
                }
           }
        }

        System.out.println("Membership Number          Member Name          Start Membership Date            " +
                "Member Type");
        System.out.println("=================          ===========          =====================          " +
                "===============");

        //Looping the data stored in the arrays and printing them
        for (int i=0; i<name.length; i++) {
            System.out.format("%-27s%-26s%-26s%-25s\n", memberNumber[i], name[i], startDate[i], type[i]);
        }
    }
}
