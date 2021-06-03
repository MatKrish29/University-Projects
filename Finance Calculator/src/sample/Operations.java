package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Operations {
    //Method to add all the controls to the pane.
    public static void controls(Node T1, Node T2, Node T3, Node T4, Node T5, Node T6, Node T7, Node T8, Node T9,
                                Node T0, Node TC, Node TD, Node TE, Node minus, Node back, Node clear, Node history,
                                Node F1, Node F2, Node F3, Node F4, Node F5, Node L1, Node L2, Node L3, Node L4,
                                Node L5, String title, Stage stage, String button) throws FileNotFoundException {
        //Creating pane and adding image to the pane.
        Image image = new Image(Operations.class.getResourceAsStream("/bg2.jpg"));
        ImageView imageView = new ImageView(image);
        Pane root = new Pane(imageView);
        imageView.setFitHeight(500);
        imageView.setFitWidth(600);
        stage.setTitle(title);
        //Setting the layouts for the calculators containing four text fields.
        if (button.equals("btn1") || button.equals("btn3") || button.equals("btn4")) {
            F1.setLayoutY(115);
            F1.setLayoutX(170);
            F2.setLayoutY(190);
            F2.setLayoutX(170);
            F3.setLayoutY(266);
            F3.setLayoutX(170);
            F4.setLayoutY(340);
            F4.setLayoutX(170);
            L1.setLayoutY(115);
            L1.setLayoutX(30);
            L2.setLayoutY(190);
            L2.setLayoutX(30);
            L3.setLayoutY(266);
            L3.setLayoutX(30);
            L4.setLayoutY(340);
            L4.setLayoutX(30);

            //Adding minus button to the Loan calculator.
            if (button.equals("btn4")) {
                root.getChildren().addAll(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TD, TC, TE, minus, clear, history,
                        F1, F2, F3, F4, L1, L2, L3, L4, back);
            }
            else {
                root.getChildren().addAll(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TD, TC, TE, clear, history, F1, F2,
                        F3, F4, L1, L2, L3, L4, back);
            }
        }
        //Setting the layouts for the calculator containing five text fields.
        else if (button.equals("btn2")) {
            F1.setLayoutY(115);
            F1.setLayoutX(170);
            F2.setLayoutY(172);
            F2.setLayoutX(170);
            F3.setLayoutY(228);
            F3.setLayoutX(170);
            F4.setLayoutY(284);
            F4.setLayoutX(170);
            F5.setLayoutY(340);
            F5.setLayoutX(170);
            L1.setLayoutY(115);
            L1.setLayoutX(30);
            L2.setLayoutY(172);
            L2.setLayoutX(30);
            L3.setLayoutY(228);
            L3.setLayoutX(30);
            L4.setLayoutY(284);
            L4.setLayoutX(30);
            L5.setLayoutY(340);
            L5.setLayoutX(30);

            root.getChildren().addAll(T1, T2, T3, T4, T5, T6, T7, T8, T9, T0, TD, TC, TE, minus, clear, history, F1,
                    F2, F3, F4, F5, L1, L2, L3, L4, L5, back);
        }
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    //Method to add functions to the keyboard.
    public static void keys(Button B, TextField TF, String num) {
        B.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String txt = TF.getText();
                TF.setText(txt + num);
            }
        });
    }

    //Method to implement the functions created for the keyboard in the 'keys' method.
    public static void textField(Button T1, Button T2, Button T3, Button T4, Button T5, Button T6, Button T7, Button T8,
                                 Button T9, Button T0, Button TM, Button TD, Button TC, TextField TF) {
        //Texts will appear in the field which the user selects.
        TF.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TF.setEditable(false); //System keyboard cannot be used to input data as text fields are disabled.
                keys(T1, TF, "1");
                keys(T2, TF, "2");
                keys(T3, TF, "3");
                keys(T4, TF, "4");
                keys(T5, TF, "5");
                keys(T6, TF, "6");
                keys(T7, TF, "7");
                keys(T8, TF, "8");
                keys(T9, TF, "9");
                keys(T0, TF, "0");
                /*To avoid repeated decimal points '.' button will be disabled if there's a decimal point. Checked on
                text field click.*/
                if (TF.getText().contains(".")) {
                    TD.setDisable(true);
                }
                /*To avoid  minus being repeated, '-' button will be disabled if there's a minus. Checked on text field
                click.*/
                if (TF.getText().contains("-")) {
                    TM.setDisable(true);
                }
                //Decimal button function.
                TD.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        /*'.' will not be entered if there's only '-' in the text field. Checked on the decimal button
                        click.*/
                        if (!TF.getText().equals("-")) {
                            TF.setText(TF.getText() + ".");
                        }
                        /*'.' will not be entered and button will be disabled if there's a '.' in the text field.
                        Checked on the decimal button click.*/
                        if (TF.getText().contains(".")) {
                            TD.setDisable(true);
                        }
                    }
                });
                //Disabled decimal button will be enabled if there's no '.' is present.
                if (!TF.getText().contains(".")) {
                    TD.setDisable(false);
                }
                //Minus button function.
                TM.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        /*'-' will not be entered if there's only '.' in the text field. Checked on the decimal button
                        click.*/
                        if (!(TF.getText().length() > 0)) {
                            TF.setText(TF.getText() + "-");
                        }
                        /*'-' will not be entered and button will be disabled if there's a '-' in the text field.
                        Checked on the decimal button click.*/
                        if (TF.getText().contains("-")) {
                            TM.setDisable(true);
                        }
                    }
                });
                //Disabled minus button will be enabled if there's no '-' is present.
                if (!TF.getText().contains("-")) {
                    TM.setDisable(false);
                }
                //To delete last entry.
                TC.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String txt = TF.getText();
                        String updated = txt.substring(0, txt.length() -1);
                        TF.setText(updated);
                        //If '.' is deleted, the disabled '.' button will be enabled.
                        if (!TF.getText().contains(".")) {
                            TD.setDisable(false);
                        }
                        //If '-' is deleted, the disabled '.' button will be enabled.
                        if (!TF.getText().contains("-")) {
                            TM.setDisable(false);
                        }
                    }
                });
            }
        });
    }

    //Method to clear all the data inserted in a calculator.
    public static void clear(TextField first, TextField second, TextField third, TextField fourth, TextField fifth) {
        first.setText("");
        second.setText("");
        third.setText("");
        fourth.setText("");
        fifth.setText("");
        first.setStyle("-fx-font-family: Arial");
        second.setStyle("-fx-font-family: Arial");
        third.setStyle("-fx-font-family: Arial");
        fourth.setStyle("-fx-font-family: Arial");
        fifth.setStyle("-fx-font-family: Arial");
    }

    //Method to write files.
    public static void fileWrite(FileWriter FW, File F, PrintWriter PW, String S1, String S2, String S3, String S4,
                                 String S5, String S6, TextField TF1, TextField TF2, TextField TF3, TextField TF4,
                                 TextField TF5, Boolean bool) {
        try {
            FW = new FileWriter(F, bool);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PW = new PrintWriter(FW, true);
        PW.println(S1 + TF1.getText());
        PW.println(S2 + TF2.getText());
        PW.println(S3 + TF3.getText());
        PW.println(S4 + TF4.getText());
        PW.println(S5 + TF5.getText());
        PW.println(S6);
    }

    //Method to write the last entered data on a separate file using the method 'fileWrite' to write files.
    public static void saveFilesLoader(FileWriter FW, PrintWriter PW, File file1, File file2, File file3, File file4,
                                       TextField TF1, TextField TF2, TextField TF3, TextField TF4, TextField TF5,
                                       String button) {
        if (button.equals("btn1")) {
            fileWrite(FW, file1, PW, "", "", "", "", "", "", TF1, TF2, TF3, TF4, TF5, false);
        }
        else if (button.equals("btn2")) {
            fileWrite(FW, file2, PW, "", "", "", "", "", "", TF1, TF2, TF3, TF4, TF5, false);
        }
        else if (button.equals("btn3")) {
            fileWrite(FW, file3, PW, "", "", "", "", "", "", TF1, TF2, TF3, TF4, TF5, false);
        }
        else if (button.equals("btn4")) {
            fileWrite(FW, file4, PW, "", "", "", "", "", "", TF1, TF2, TF3, TF4, TF5, false);
        }
    }

    /*Method to read files which are written using the method 'saveFilesLoader' and insert them into the text fields
    (Method to re-populate last entry fields).*/
    public static void fileRead(TextField TF1, TextField TF2, TextField TF3, TextField TF4, TextField TF5, File file)
            throws FileNotFoundException {
        Scanner reader = new Scanner(file);
        int count = 0;
        while (reader.hasNextLine()) {
            count+=1;
            String data = reader.nextLine();
            if (count==1) {
                TF1.setText(data);
            }
            else if (count==2) {
                TF2.setText(data);
            }
            else if (count==3) {
                TF3.setText(data);
            }
            else if (count==4) {
                TF4.setText(data);
            }
            else if (count==5) {
                TF5.setText(data);
            }
        }
    }

    //Method to view the history of the calculations.
    //Each calculator has it's own history.
    public static void history(File file, TextArea historyArea, String title, Stage historyStage)
            throws FileNotFoundException {
        Pane root = new Pane();
        Scene historyScene = new Scene(root, 510, 400);
        //Reading from the file and inserting them into the created text area.
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            historyArea.setText(historyArea.getText() + data + "\n");
        }
        root.getChildren().add(historyArea);
        historyStage.setScene(historyScene);
        historyStage.setTitle(title);
        historyStage.show();
    }

    //Method to display help window.
    public static void help(Button back, Scene scene, Stage primaryStage) {
        //Creating pane and adding image containing help descriptions.
        Image image = new Image(Operations.class.getResourceAsStream("/help.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        imageView.setLayoutY(5);
        imageView.setLayoutX(50);
        Pane root = new Pane(imageView);
        root.setStyle("-fx-background-color: white;");
        //Creating help window scene.
        Scene myScene = new Scene(root, 600, 500);
        root.getChildren().add(back);
        //Going back to the start window on back button click.
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle("Finance Calculator");
                primaryStage.setScene(scene);
            }
        });
        primaryStage.setScene(myScene);
        primaryStage.setTitle("Help Window");
        primaryStage.show();
    }
}
