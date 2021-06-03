package sample;

import java.util.Scanner;

public class Date {

    private String startMembershipDate;

    //Setter
    public void StartMembershipDate() {
        //Looping until user inputs a valid input
        while (true) {
            try {
                Scanner sc2 = new Scanner(System.in);
                System.out.print("\nEnter the Start Membership Date (YYYY/MM/DD) : ");
                String startMembershipDate = sc2.next();
                //Getting the year from user input
                String year = startMembershipDate.substring(0, 4);
                //Converting string to int
                int yearI = Integer.parseInt(year);
                //Validating the year
                if (yearI > 2010 && yearI < 2030) {
                    //Getting the month from user input
                    String month = startMembershipDate.substring(5, 7);
                    //Converting string to int
                    int monthI = Integer.parseInt(month);
                    //Validating the month
                    if (monthI > 0 && monthI < 13) {
                        //Getting the day from user input
                        String day = startMembershipDate.substring(8, 10);
                        //Converting string to int
                        int dayI = Integer.parseInt(day);
                        //Validating leap year february month
                        if (monthI == 2) {
                            if ((yearI / 4) == 0) {
                                //Validating the day
                                if (dayI > 0 && dayI < 30) {
                                    //Setting the date in a standard way
                                    startMembershipDate = (year + "-" + month + "-" + day);
                                    this.startMembershipDate = startMembershipDate;
                                    break;
                                } else {
                                    System.out.println("Invalid day input!");
                                    continue;
                                }
                            } else {
                                //Validating the day
                                if (dayI > 0 && dayI < 29) {
                                    //Setting the date in a standard way
                                    startMembershipDate = (year + "-" + month + "-" + day);
                                    this.startMembershipDate = startMembershipDate;
                                    break;
                                } else {
                                    System.out.println("Invalid day input!");
                                    continue;
                                }
                            }
                        } else if (monthI == 1 || monthI == 3 || monthI == 5 || monthI == 7 || monthI == 8
                                || monthI == 10 || monthI==12) {
                            //Validating the day
                            if (dayI > 0 && dayI < 32) {
                                //Setting the date in a standard way
                                startMembershipDate = (year + "-" + month + "-" + day);
                                this.startMembershipDate = startMembershipDate;
                                break;
                            } else {
                                System.out.println("Invalid day input!");
                                continue;
                            }
                        } else {
                            //Validating the day
                            if (dayI > 0 && dayI < 31) {
                                //Setting the date in a standard way
                                startMembershipDate = (year + "-" + month + "-" + day);
                                this.startMembershipDate = startMembershipDate;
                                break;
                            } else {
                                System.out.println("Invalid day input!");
                                continue;
                            }
                        }
                    } else {
                        System.out.println("Invalid month input!");
                        continue;
                    }
                } else {
                    System.out.println("Invalid year input!");
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println("Recheck the date format!");
            }
        }
    }

    //Getter
    public String getStartMembershipDate() {
        return (startMembershipDate);
    }
}
