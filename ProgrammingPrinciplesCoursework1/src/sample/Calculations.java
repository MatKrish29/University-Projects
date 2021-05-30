package sample;

import javafx.scene.control.TextField;

//This class contains all the calculations.
//Textfield.getText() is used to get the text present in a text field.
//Textfield.setText() is used to set a text in a text field.
//Double.parseDouble() is used to convert string to double.
//Math.round() is used to round off the decimal values.
//Math.pow() is used to perform exponents calculations.
public class Calculations extends Main {
    //Method to find Final Value in Savings(Without Payment).
    public static void calculation1(TextField first, TextField second, TextField third, TextField fourth) {
        String principleAmount = first.getText();
        String interestRate = second.getText();
        String time = third.getText();
        double p = Double.parseDouble(principleAmount);
        double r = Double.parseDouble(interestRate);
        double t = Double.parseDouble(time);
        double finalAmount = p*Math.pow(1+(r/100)/12,12*t);
        finalAmount = Math.round(finalAmount*100.00)/100.00;
        fourth.setText("" + finalAmount);
    }
    //Method to find Interest Rate in Savings(Without Payment).
    public static void calculation2(TextField first, TextField second, TextField third, TextField fourth) {
        String principleAmount = first.getText();
        String futureValue = second.getText();
        String time = third.getText();
        double f = Double.parseDouble(futureValue);
        double p = Double.parseDouble(principleAmount);
        double t = Double.parseDouble(time);
        double interestRate = 12*(Math.pow(f/p,1/(12*t))-1);
        interestRate = Math.round(interestRate*100.0)/100.0;
        fourth.setText("" + interestRate);
    }
    //Method to find Principal Amount in Savings(Without Payment).
    public static void calculation3(TextField first, TextField second, TextField third, TextField fourth) {
        String interestRate = first.getText();
        String futureValue = second.getText();
        String time = third.getText();
        double f = Double.parseDouble(futureValue);
        double r = Double.parseDouble(interestRate);
        double t = Double.parseDouble(time);
        double principleAmount = f/Math.pow(1+(r/100)/12,12*t);
        principleAmount = Math.round(principleAmount*100.0)/100.0;
        fourth.setText("" + principleAmount);
    }
    //Method to find Time in Savings(Without Payment).
    public static void calculation4(TextField first, TextField second, TextField third, TextField fourth) {
        String principleAmount = first.getText();
        String interestRate = second.getText();
        String futureValue = third.getText();
        double f = Double.parseDouble(futureValue);
        double p = Double.parseDouble(principleAmount);
        double r = Double.parseDouble(interestRate);
        double time = (Math.log(f/p))/(12*(Math.log(1+(r/100)/12)));
        time = Math.round(time*100.0)/100.0;
        fourth.setText("" + time);
    }
    //Method to find Final Value in Savings(With Payment).
    public static void calculation5(TextField first, TextField second, TextField third, TextField fourth,
                                    TextField fifth) {
        String payment = first.getText();
        String interestRate = second.getText();
        String time = third.getText();
        double p = Double.parseDouble(payment);
        double r = Double.parseDouble(interestRate);
        double t = Double.parseDouble(time);
        double futureValue = p*(((Math.pow(1+(r/100)/12, 12*t))-1)/((r/100)/12));
        calculation1(fifth, second, third, fourth);
        double compoundInterest = Double.parseDouble(fourth.getText());
        double total = compoundInterest + futureValue;
        total = Math.round(total*100.0)/100.0;
        fourth.setText("" + total);
    }
    //Method to find Payment in Savings(With Payment).
    public static void calculation6(TextField first, TextField second, TextField third, TextField fourth) {
        String interestRate = first.getText();
        String futureValue = second.getText();
        String time = third.getText();
        double r = Double.parseDouble(interestRate);
        double f = Double.parseDouble(futureValue);
        double t = Double.parseDouble(time);
        double payment =f/(((Math.pow(1+(r/100)/12, 12*t))-1)/((r/100)/12));
        payment = Math.round(payment*100.0)/100.0;
        fourth.setText("" + payment);
    }
    //Method to find Time in Savings(With Payment).
    public static void calculation7(TextField first, TextField second, TextField third, TextField fourth) {
        String interestRate = first.getText();
        String futureValue = second.getText();
        String payment = third.getText();
        double r = Double.parseDouble(interestRate);
        double f = Double.parseDouble(futureValue);
        double p = Double.parseDouble(payment);
        double time = Math.log10((1+(((r/100)*f)/p)/Math.log10(1+(r/100))*12));
        time = Math.round(time*100.0)/100.0;
        fourth.setText("" + time);
    }
    //Last 3 methods contain a String as an additional parameter to choose between Mortgage and Loan.
    //Method to find Payment in both Mortgage & Loan.
    public static void calculation8(TextField first, TextField second, TextField third, TextField fourth, String button)
    {
        String interestRate = first.getText();
        String totalPayment = second.getText();
        String time = third.getText();
        double r = Double.parseDouble(interestRate);
        double a = Double.parseDouble(totalPayment);
        double t = Double.parseDouble(time);
        double payment1 = a*((r/100)/12)*(Math.pow(1+((r/100)/12), 12*t))/((Math.pow(1+((r/100)/12), 12*t))-1);
        double payment2 = a*((r/100)/12)*(Math.pow(1+((r/100)/12), t))/((Math.pow(1+((r/100)/12), t))-1);
        payment1 = Math.round(payment1*100.0)/100.0;
        payment2 = Math.round(payment2*100.0)/100.0;
        if (button.equals("btn3")) {
            fourth.setText("" + payment1);
        }
        else {
            fourth.setText("" + payment2);
        }
    }
    //Method to find Time in both Mortgage & Loan.
    public static void calculation9(TextField first, TextField second, TextField third, TextField fourth, String button)
    {
        String interestRate = first.getText();
        String totalAmount = second.getText();
        String payment = third.getText();
        double r = Double.parseDouble(interestRate);
        double a = Double.parseDouble(totalAmount);
        double p = Double.parseDouble(payment);
        double time1 = (-Math.log(1-(r/100)/12*a/p))/(Math.log(1+((r/100)/12)));
        double time2 = ((-Math.log(1-(r/100)/12*a/p))/(Math.log(1+((r/100)/12))))/12;
        time1 = Math.round((time1)*100.0)/100.0;
        time2 = Math.round((time2)*100.0)/100.0;
        if (button.equals("btn3")) {
            fourth.setText("" + time2);
        }
        else {
            fourth.setText("" + time1);
        }
    }
    //Method to find Mortgage Amount & Loan Amount.
    public static void calculation10(TextField first, TextField second, TextField third, TextField fourth,
                                     String button) {
        String interestRate = first.getText();
        String monthlyPayment = second.getText();
        String time = third.getText();
        double r = Double.parseDouble(interestRate);
        double p = Double.parseDouble(monthlyPayment);
        double t = Double.parseDouble(time);
        double totalPayment1 = p/((r/100)/12)*(1-(1/Math.pow(1+((r/100)/12), t*12)));
        double totalPayment2 = p/((r/100)/12)*(1-(1/Math.pow(1+((r/100)/12), t)));
        totalPayment1 = Math.round(totalPayment1*100.0)/100.0;
        totalPayment2 = Math.round(totalPayment2*100.0)/100.0;
        if (button.equals("btn3")) {
            fourth.setText("" + totalPayment1);
        }
        else {
            fourth.setText("" + totalPayment2);
        }
    }
}

