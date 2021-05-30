package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AlphaMethod {
    static File savingsWithoutPay = new File("Savings(Without Payment).txt");
    static File savingsWithPay = new File("Savings(With Payment).txt");
    static File mortgage = new File("Mortgage.txt");
    static File loan = new File("Loan.txt");
    static PrintWriter pw;
    static FileWriter fw;

    static File savingsWithoutPayLoader = new File("Savings(Without Payment) Loader.txt");
    static File savingsWithPayLoader = new File("Savings(With Payment) Loader.txt");
    static File mortgageLoader = new File("Mortgage Loader.txt");
    static File loanLoader = new File("Loan Loader.txt");
    static PrintWriter pwL;
    static FileWriter fwL;

    /*The main method where all the functions are held. It consists of methods from both the classes Calculations and
    Operations.*/
    public static void onActionEvent(Button T1, Button T2, Button T3, Button T4, Button T5, Button T6, Button T7,
                                     Button T8, Button T9, Button T0, Button TD, Button TC, Button TE, Button TM,
                                     Button back, Button clear, Button history, TextField TF1, TextField TF2,
                                     TextField TF3, TextField TF4, TextField TF5, Label L1, Label L2, Label L3,
                                     Label L4, Label L5, TextArea historyArea, String title, Stage stage,
                                     Stage historyStage, Scene scene, String button) throws FileNotFoundException {
        //Function to the back button to go back to the main window.
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Last entry data will be written in a file when user clicks back button.
                Operations.saveFilesLoader(fwL, pwL, savingsWithoutPayLoader, savingsWithPayLoader, mortgageLoader,
                        loanLoader, TF1, TF2, TF3, TF4, TF5, button);
                historyStage.close(); /*If user has history window opened, clicking the back button will cause the
                history window to get closed.*/
                stage.setTitle("Finance Calculator");
                stage.setScene(scene);
            }
        });
        //Last entered data will be written in a file if the app get closed.
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Operations.saveFilesLoader(fwL, pwL, savingsWithoutPayLoader, savingsWithPayLoader, mortgageLoader,
                        loanLoader, TF1, TF2, TF3, TF4, TF5, button);
                historyStage.close();
            }
        } );
        //Function to clear button.
        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Operations.clear(TF1, TF2, TF3, TF4, TF5);
            }
        });
        //To add nodes according to the arguments.
        Operations.controls(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TD, TC, TE, TM, back, clear, history, TF1, TF2, TF3,
                TF4, TF5, L1, L2, L3, L4, L5, title, stage, button);
        //To add text to the text fields according to the user action.
        Operations.textField(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TM, TD, TC, TF1);
        Operations.textField(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TM, TD, TC, TF2);
        Operations.textField(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TM, TD, TC, TF3);
        Operations.textField(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TM, TD, TC, TF4);
        Operations.textField(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TM, TD, TC, TF5);

        //Creating an alert.
        Alert alert = new Alert(Alert.AlertType.ERROR);

        //Checking the arguments to add functionalities accordingly.
        if (button.equals("btn1")) {
            //Last entry data will be read from the file and re-populated in the text fields.
            try {
                Operations.fileRead(TF1, TF2, TF3, TF4, TF5, savingsWithoutPayLoader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Adding function to the history button. All the calculations will be read from a separate file and will be
            populated in the text area.*/
            history.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        Operations.history(savingsWithoutPay, historyArea, "Savings(Without Payment) History",
                                historyStage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Adding functions to calculate button.
            TE.setOnAction(new EventHandler<ActionEvent>() {
                /*When clicked all the criteria will be checked before making the calculations. User will be alerted
                accordingly if they make any mistakes.*/
                @Override
                public void handle(ActionEvent event) {
                    //Alerts when all the fields are filled.
                    if (!TF1.getText().equals("") && !TF2.getText().equals("") && !TF3.getText().equals("") &&
                            !TF4.getText().equals("")) {
                        alert.setHeaderText("Should leave one textfield blank!");
                        alert.setContentText("You shouldn't give input in the field in which you want to calculate.");
                        alert.show();
                    }
                    //Alerts when more than one field is empty.
                    else if ((TF1.getText().equals("") && TF2.getText().equals("")) || (TF1.getText().equals("") &&
                            TF3.getText().equals("")) || (TF1.getText().equals("") && TF4.getText().equals(""))
                            || (TF2.getText().equals("") && TF3.getText().equals("")) || (TF2.getText().equals("")
                            && TF4.getText().equals("")) || (TF3.getText().equals("") && TF4.getText().equals(""))) {
                        alert.setHeaderText("Shouldn't leave more than one textfield blank!");
                        alert.setContentText("You should give at least 3 inputs in order to do calculations.");
                        alert.show();
                    }
                    //Alerts when there's no integer in a required field.
                    else if (TF1.getText().equals(".") || TF2.getText().equals(".") || TF2.getText().equals(".")
                            || TF3.getText().equals(".") || TF4.getText().equals(".") || TF5.getText().equals(".")
                            || TF1.getText().equals("-") || TF2.getText().equals("-") || TF3.getText().equals("-")
                            || TF4.getText().equals("-") || TF5.getText().equals("-")) {
                        alert.setHeaderText("No values to calculate!");
                        alert.setContentText("You should input values to calculate.");
                        alert.show();
                    }
                    //Calculations are made when all the validations are satisfied.
                    else {
                        //Doing calculations according to the empty text field.
                        if (TF1.getText().equals("")) {
                            //Calculating Principal Amount.
                            Calculations.calculation3(TF2, TF3, TF4, TF1);
                            TF1.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF2.getText().equals("")) {
                            //Calculating Interest Rate.
                            Calculations.calculation2(TF1, TF3, TF4, TF2);
                            TF2.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF3.getText().equals("")) {
                            //Calculating Future Value.
                            Calculations.calculation1(TF1, TF2, TF4, TF3);
                            TF3.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF4.getText().equals("")) {
                            //Calculating Time.
                            Calculations.calculation4(TF1, TF2, TF3, TF4);
                            TF4.setStyle("-fx-font-family: 'Arial Black'; ");
                        }
                        //Writing to the file after calculating. (To view all the calculations).
                        Operations.fileWrite(fw, savingsWithoutPay, pw, "Principal Amount: ",
                                "Interest Rate   : ", "Future Value    : ", "Time            : ", "",
                                "", TF1, TF2, TF3, TF4, TF5, true);
                    }
                }
            });
        }
        else if (button.equals("btn2")) {
            //Last entry data will be read from the file and re-populated in the text fields.
            try {
                Operations.fileRead(TF1, TF2, TF3, TF4, TF5, savingsWithPayLoader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Adding function to the history button. All the calculations will be read from a separate file and will be
            populated in the text area.*/
            history.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        Operations.history(savingsWithPay, historyArea, "Savings(With Payment) History",
                                historyStage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Adding functions to calculate button.
            TE.setOnAction(new EventHandler<ActionEvent>() {
                /*When clicked all the criteria will be checked before making the calculations. User will be alerted
                accordingly if they make any mistakes.*/
                @Override
                public void handle(ActionEvent event) {
                    //Alerts when Principal Amount & Future Value fields are empty.
                    if (TF1.getText().equals("") && TF3.getText().equals("")) {
                        alert.setHeaderText("Shouldn't leave Principle Amount and Future Value fields blank!");
                        alert.setContentText("You should give input to Principal Amount field to do the calculation.");
                        alert.show();
                    }
                    //Alerts when Principal Amount & Future Value fields are filled.
                    else if (!TF1.getText().equals("") && !TF3.getText().equals("")) {
                        alert.setHeaderText("Shouldn't fill Principal Amount and Future Value fields!");
                        alert.setContentText("You should empty either one of these fields.");
                        alert.show();
                    }
                    //Alerts when all Time, Payment & Future Value fields are filled.
                    else if (!TF3.getText().equals("") && !TF4.getText().equals("") && !TF5.getText().equals("")) {
                        alert.setHeaderText("Should leave one textfield blank!");
                        alert.setContentText("You should leave either Time, Payment or Future Value empty.");
                        alert.show();
                    }
                    //Alerts when more than one field is empty except Principal Amount field.
                    else if ((TF2.getText().equals("") && TF3.getText().equals("")) || (TF2.getText().equals("")
                            && TF4.getText().equals("")) || (TF3.getText().equals("") && TF4.getText().equals(""))
                            || (TF2.getText().equals("") && TF5.getText().equals("")) || (TF3.getText().equals("")
                            && TF5.getText().equals("")) || (TF4.getText().equals("") && TF5.getText().equals(""))) {
                        alert.setHeaderText("Too many empty text fields!");
                        alert.setContentText("Except Principal Amount, you can't leave two fields empty.");
                        alert.show();
                    }
                    //Alerts when there's no integer in a required field.
                    else if (TF1.getText().equals(".") || TF2.getText().equals(".") || TF2.getText().equals(".")
                            || TF3.getText().equals(".") || TF4.getText().equals(".") || TF5.getText().equals(".")
                            || TF1.getText().equals("-") || TF2.getText().equals("-") || TF3.getText().equals("-")
                            || TF4.getText().equals("-") || TF5.getText().equals("-")) {
                        alert.setHeaderText("No values to calculate!");
                        alert.setContentText("You should input values to calculate.");
                        alert.show();
                    }
                    //Alerts when negative value is inserted in Interest Rate, Time or Future Value.
                    else if (TF2.getText().contains("-") || TF5.getText().contains("-") || TF3.getText().contains("-"))
                    {
                        alert.setHeaderText("Can't include negative vales!");
                        alert.setContentText("Interest Rate, Time or Future Value cannot be negative.");
                        alert.show();
                    }
                    //Calculations are made when all the validations are satisfied.
                    else {
                        //Doing calculations according to the empty text field.
                        if (TF3.getText().equals("")) {
                            //Calculating Future Value.
                            Calculations.calculation5(TF4, TF2, TF5, TF3, TF1);
                            TF3.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF1.getText().equals("") && TF4.getText().equals("")) {
                            //Calculating Payment.
                            Calculations.calculation6(TF2, TF3, TF5, TF4);
                            TF4.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF1.getText().equals("") && TF5.getText().equals("")) {
                            //Calculating Time.
                            Calculations.calculation7(TF2, TF3, TF4, TF5);
                            TF5.setStyle("-fx-font-family: 'Arial Black'; ");
                        }
                        //Writing to the file after calculating (To view all the calculations).
                        Operations.fileWrite(fw, savingsWithPay, pw, "Principal Amount: ", "Interest Rate   : ",
                                "Future Value    : ", "Payment         : ", "Time            : ", "",
                                TF1, TF2, TF3, TF4, TF5, true);
                    }
                }
            });
        }
        else if (button.equals("btn3")) {
            //Last entry data will be read from the file and re-populated in the text fields.
            try {
                Operations.fileRead(TF1, TF2, TF3, TF4, TF5, mortgageLoader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Adding function to the history button. All the calculations will be read from a separate file and will be
            populated in the text area.*/
            history.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        Operations.history(mortgage, historyArea, "Mortgage History", historyStage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Adding functions to calculate button.
            TE.setOnAction(new EventHandler<ActionEvent>() {
                /*When clicked all the criteria will be checked before making the calculations. User will be alerted
                accordingly if they make any mistakes.*/
                @Override
                public void handle(ActionEvent event) {
                    //Alerts when all the fields are filled.
                    if (!TF1.getText().equals("") && !TF2.getText().equals("") && !TF3.getText().equals("")
                            && !TF4.getText().equals("")) {
                        alert.setHeaderText("Should leave one textfield blank!");
                        alert.setContentText("You can't input in the field in which you want to calculate.");
                        alert.show();
                    }
                    //Alerts when more than one field is empty.
                    else if ((TF1.getText().equals("") && TF2.getText().equals("")) || (TF1.getText().equals("")
                            && TF3.getText().equals("")) || (TF1.getText().equals("") && TF4.getText().equals(""))
                            || (TF2.getText().equals("") && TF3.getText().equals("")) || (TF2.getText().equals("")
                            && TF4.getText().equals("")) || TF3.getText().equals("") && TF4.getText().equals("")) {
                        alert.setHeaderText("Shouldn't leave more than one textfield blank!");
                        alert.setContentText("You should give at least 3 inputs in order to do calculations.");
                        alert.show();
                    }
                    //Alerts when Interest Rate field is empty.
                    else if (TF1.getText().equals("")) {
                        alert.setHeaderText("Shouldn't leave Interest Rate textfield blank!");
                        alert.setContentText("Interest Rate is a must fill field.");
                        alert.show();
                    }
                    //Alerts when there's no integer in a required field.
                    else if (TF1.getText().equals(".") || TF2.getText().equals(".") || TF2.getText().equals(".")
                            || TF3.getText().equals(".") || TF4.getText().equals(".") || TF5.getText().equals(".")
                            || TF1.getText().equals("-") || TF2.getText().equals("-") || TF3.getText().equals("-")
                            || TF4.getText().equals("-") || TF5.getText().equals("-")) {
                        alert.setHeaderText("values required to calculate!");
                        alert.setContentText("You should values to calculate.");
                        alert.show();
                    }
                    //Calculations are made when all the validations are satisfied.
                    else {
                        //Doing calculations according to the empty text field.
                        if (TF3.getText().equals("")) {
                            //Calculating Monthly Payment.
                            Calculations.calculation8(TF1, TF2, TF4, TF3, button);
                            TF3.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF4.getText().equals("")) {
                            //Calculating Time.
                            Calculations.calculation9(TF1, TF2, TF3, TF4, button);
                            TF4.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF2.getText().equals("")) {
                            //Calculating Mortgage Amount.
                            Calculations.calculation10(TF1, TF3, TF4, TF2, button);
                            TF2.setStyle("-fx-font-family: 'Arial Black'; ");
                        }
                        //Writing to the file after calculating. (To view all the calculations).
                        Operations.fileWrite(fw, mortgage, pw, "Interest Rate   : ", "Mortgage Amount : ",
                                "Monthly Payment : ", "Time            : ", "", "", TF1, TF2, TF3, TF4,
                                TF5, true);
                    }
                }
            });
        }
        else {
            //Last entry data will be read from the file and re-populated in the text fields.
            try {
                Operations.fileRead(TF1, TF2, TF3, TF4, TF5, loanLoader);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            /*Adding function to the history button. All the calculations will be read from a separate file and will be
            populated in the text area.*/
            history.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    try {
                        Operations.history(loan, historyArea, "Loan History", historyStage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Adding functions to calculate button.
            TE.setOnAction(new EventHandler<ActionEvent>() {
                /*When clicked all the criteria will be checked before making the calculations. User will be alerted
                accordingly if they make any mistakes.*/
                @Override
                public void handle(ActionEvent event) {
                    //Alerts when all the fields are filled.
                    if (!TF1.getText().equals("") && !TF2.getText().equals("") && !TF3.getText().equals("")
                            && !TF4.getText().equals("")) {
                        alert.setHeaderText("Should leave one textfield blank!");
                        alert.setContentText("You can't input in the field in which you want to calculate.");
                        alert.show();
                    }
                    //Alerts when more than one field is empty.
                    else if ((TF1.getText().equals("") && TF2.getText().equals("")) || (TF1.getText().equals("")
                            && TF3.getText().equals("")) || (TF1.getText().equals("") && TF4.getText().equals(""))
                            || (TF2.getText().equals("") && TF3.getText().equals("")) || (TF2.getText().equals("")
                            && TF4.getText().equals("")) || TF3.getText().equals("") && TF4.getText().equals("")) {
                        alert.setHeaderText("Shouldn't leave more than one textfield blank!");
                        alert.setContentText("You should give at least 3 inputs in order to do calculations.");
                        alert.show();
                    }
                    //Alerts when Interest Rate field is empty.
                    else if (TF1.getText().equals("")) {
                        alert.setHeaderText("Shouldn't leave Interest Rate textfield blank!");
                        alert.setContentText("Interest Rate is a must fill field.");
                        alert.show();
                    }
                    //Alerts when there's no value in a required field.
                    else if (TF1.getText().equals(".") || TF2.getText().equals(".") || TF2.getText().equals(".")
                            || TF3.getText().equals(".") || TF4.getText().equals(".") || TF5.getText().equals(".")
                            || TF1.getText().equals("-") || TF2.getText().equals("-") || TF3.getText().equals("-")
                            || TF4.getText().equals("-") || TF5.getText().equals("-")) {
                        alert.setHeaderText("Value required to calculate!");
                        alert.setContentText("You should input values to calculate.");
                        alert.show();
                    }
                    //Alerts when negative value is inserted in Interest Rate or No.of Payments.
                    else if (TF1.getText().contains("-") || TF4.getText().contains("-")) {
                        alert.setHeaderText("Can't include negative vales!");
                        alert.setContentText("Interest Rate or No.of Payments cannot be negative.");
                        alert.show();
                    }
                    //Calculations are made when all the validations are satisfied.
                    else {
                        //Doing calculations according to the empty text field.
                        if (TF3.getText().equals("")) {
                            //Calculating Monthly Payment.
                            Calculations.calculation8(TF1, TF2, TF4, TF3, button);
                            TF3.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF4.getText().equals("")) {
                            //Calculating No. of Payments.
                            Calculations.calculation9(TF1, TF2, TF3, TF4, button);
                            TF4.setStyle("-fx-font-family: 'Arial Black'; ");
                        } else if (TF2.getText().equals("")) {
                            //Calculating Loan Amount.
                            Calculations.calculation10(TF1, TF3, TF4, TF2, button);
                            TF2.setStyle("-fx-font-family: 'Arial Black'; ");
                        }
                        //Writing to the file after calculating. (To view all the calculations).
                        Operations.fileWrite(fw, loan, pw, "Interest Rate      : ", "Loan Amount        : ",
                                "Monthly Payment    : ", "Time               : ", "", "", TF1, TF2, TF3,
                                TF4, TF5, true);
                    }
                }
            });
        }
    }
}
