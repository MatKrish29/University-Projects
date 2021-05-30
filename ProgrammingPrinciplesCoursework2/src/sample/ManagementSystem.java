package sample;

import java.util.Scanner;

public class ManagementSystem extends GUI {

    public static void main(String[] args) {
        MyGymManager member = new MyGymManager();
        Mongo object = new Mongo();
        //Looping until user inputs a valid input
        while (true) {
            //Prompting to choose an action
            System.out.print("\n=============INSTRUCTIONS=============");
            System.out.print("\n>>> Press [a] to add new members.");
            System.out.print("\n>>> Press [d] to delete members.");
            System.out.print("\n>>> Press [p] to print the list of members.");
            System.out.println("\n>>> Press [s] to sort the member list.");
            System.out.println(">>> Press [i] to open GUI.");
            Scanner sc = new Scanner(System.in);
            String userCommand1 = sc.next().toLowerCase();
            switch (userCommand1) {
                //Prompting to add members
                case "a":
                    //Checking the available number of slots
                    if (object.count==100) {
                        System.out.println("There're no further slots available!");
                        System.out.println("\nDo you want to perform any other operations? (y/n)");
                        String userCommand = sc.next().toLowerCase();
                        //Looping until user inputs a valid input
                        while (true) {
                            if (userCommand.equals("y")) {
                                continue;
                            }
                            else if (userCommand.equals("n")) {
                                System.out.print("\nHave a good day :)");
                                System.exit(0);
                            }
                            else {
                                while (!userCommand.equals("y") && !userCommand.equals("n")) {
                                    System.out.println("Please enter a valid input (y/n).");
                                    userCommand = sc.next().toLowerCase();
                                }
                                continue;
                            }
                        }
                    }
                    //Prompting to choose the type of member
                    else {
                        System.out.print("=============INSTRUCTIONS=============");
                        System.out.print("\n>>> Press [d] to add default members.");
                        System.out.print("\n>>> Press [s] to add student members.");
                        System.out.println("\n>>> Press [o] to add members over age 60.");
                        String userCommand2 = sc.next().toLowerCase();
                        while (!userCommand2.equals("d") && !userCommand2.equals("s") && !userCommand2.equals("o")) {
                            System.out.println("\nPlease enter a valid input!");
                            System.out.print("\n=============INSTRUCTIONS=============");
                            System.out.print("\n>>> Press [d] to add default members.");
                            System.out.print("\n>>> Press [s] to add student members.");
                            System.out.println("\n>>> Press [o] to add members over age 60.");
                            userCommand2 = sc.next().toLowerCase();
                        }
                        switch (userCommand2) {
                            //Prompting to add default members
                            case "d":
                                member.addDefaultMember();
                                break;
                            //Prompting to add student members
                            case "s":
                                member.addStudentMember();
                                break;
                            //Prompting to add over60 members
                            case "o":
                                member.addOver60Member();
                                break;
                        }
                    }
                    break;
                //Prompting to delete members
                case "d":
                    member.deleteMember();
                    break;
                //Prompting to print members
                case "p":
                    member.printMembers();
                    break;
                //Prompting to sort names
                case "s":
                    member.sortNames();
                    break;
                //Prompting to open GUI
                case "i":
                    launch(args);
                    break;
                default:
                    System.out.println("\nPlease enter a valid input!");
                    continue;
            }
            //Prompting to choose any other options
            System.out.println("\nDo you want to perform any other operations? (y/n)");
            String userCommand = sc.next().toLowerCase();
            if (userCommand.equals("y")) {
                continue;
            }
            else if (userCommand.equals("n")) {
                System.out.print("\nHave a good day :)");
                System.exit(0);
            }
            else {
                while (!userCommand.equals("y") && !userCommand.equals("n")) {
                    System.out.println("Please enter a valid input (y/n).");
                    userCommand = sc.next().toLowerCase();
                }
                if (userCommand.equals("n")) {
                    System.out.print("\nHave a good day :)");
                    System.exit(0);
                }
                else {
                    continue;
                }
            }
        }
    }
}
