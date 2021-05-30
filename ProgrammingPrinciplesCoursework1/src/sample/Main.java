package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creating buttons for calculators, keyboard and navigations.
        Button btn1 = new Button("Savings(Without Payment)");
        Button btn2 = new Button("Savings(With Payment)");
        Button btn3 = new Button("Mortgage");
        Button btn4 = new Button("Loan");
        Button btnT1 = new Button("1");
        Button btnT2 = new Button("2");
        Button btnT3 = new Button("3");
        Button btnT4 = new Button("4");
        Button btnT5 = new Button("5");
        Button btnT6 = new Button("6");
        Button btnT7 = new Button("7");
        Button btnT8 = new Button("8");
        Button btnT9 = new Button("9");
        Button btnT0 = new Button("0");
        Button btnTC = new Button("C");
        Button btnTD = new Button(".");
        Button btnTM = new Button("-");
        Button btnTE = new Button("Calculate");
        Button clear = new Button("Clear All");
        Button history = new Button();
        Button help = new Button();
        Button back = new Button();

        //Creating text fields.
        TextField principleAmount = new TextField();
        TextField futureValue = new TextField();
        TextField interestRate = new TextField();
        TextField time = new TextField();
        TextField principleAmount2 = new TextField();
        TextField futureValue2 = new TextField();
        TextField interestRate2 = new TextField();
        TextField payment = new TextField();
        TextField time2 = new TextField();
        TextField mortgageAmount = new TextField();
        TextField interestRate3 = new TextField();
        TextField payment2 = new TextField();
        TextField time3 = new TextField();
        TextField loanAmount = new TextField();
        TextField interestRate4 = new TextField();
        TextField payment3 = new TextField();
        TextField time4 = new TextField();
        TextField extra = new TextField();

        //Creating labels.
        Label principleAmountL = new Label("Principal Amount");
        Label futureValueL = new Label("Future Value");
        Label interestRateL = new Label("Interest Rate (%)");
        Label timeL = new Label("Time (years)");
        Label noOfPaymentsL = new Label("No. of Payments");
        Label paymentL = new Label("Payment");
        Label payment2L = new Label("Monthly Payment");
        Label loanL = new Label("Loan Amount");
        Label mortgageL = new Label("Mortgage Amount");
        Label headingL = new Label("Finance Calculator");

        //Creating text area to view history.
        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);

       //Setting styles to buttons and text area.
        btn1.setStyle("-fx-background-color: linear-gradient(darkred, red); -fx-border-color: black; " +
                "-fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-family: 'Arial Black'; " +
                "-fx-font-weight: bolder; -fx-alignment: center; -fx-text-fill: white; -fx-font-size: 11; ");
        btn2.setStyle("-fx-background-color: linear-gradient(darkred, red); -fx-border-color: black; " +
                "-fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-family: 'Arial Black'; " +
                "-fx-font-weight: bolder; -fx-alignment: center; -fx-text-fill: white; -fx-font-size: 11; ");
        btn3.setStyle("-fx-background-color: linear-gradient(darkred, red); -fx-border-color: black; " +
                "-fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-family: 'Arial Black'; " +
                "-fx-font-weight: bolder; -fx-alignment: center; -fx-text-fill: white; -fx-font-size: 11; ");
        btn4.setStyle("-fx-background-color: linear-gradient(darkred, red); -fx-border-color: black;" +
                " -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-font-family: 'Arial Black';" +
                " -fx-font-weight: bolder; -fx-alignment: center; -fx-text-fill: white; -fx-font-size: 11; ");
        btnT1.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40; " +
                "-fx-min-width: 40;" + " -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20; " +
                "-fx-border-style: double; " + "-fx-border-color: white; ");
        btnT2.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40; " +
                "-fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT3.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT4.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT5.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT6.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                "-fx-border-style: double; -fx-border-color: white; ");
        btnT7.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT8.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT9.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnT0.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 40; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnTC.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 30; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 19;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnTD.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 43; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 20;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        btnTE.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 100; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 15.5;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        clear.setStyle("-fx-background-color: linear-gradient(red, black); -fx-text-fill: white;" +
                " -fx-font-family: 'Arial Black'; -fx-font-size: 20; -fx-border-style: double;" +
                " -fx-border-color: white; -fx-font-size: 14 ");
        btnTM.setStyle("-fx-background-color: linear-gradient(dodgerblue, black); -fx-min-height: 40;" +
                " -fx-min-width: 43; -fx-text-fill: white; -fx-font-family: 'Arial Black'; -fx-font-size: 17;" +
                " -fx-border-style: double; -fx-border-color: white; ");
        history.setStyle("-fx-background-color: linear-gradient(black, darkblue); ");
        back.setStyle("-fx-background-color: white; -fx-background-radius: 30px");
        help.setStyle("-fx-background-color: inherit;");
        historyArea.setStyle("-fx-color: black; -fx-font-family: 'Arial Black';");

        history.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                history.setStyle("-fx-background-color: black;");
            }
        });
        history.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                history.setStyle("-fx-background-color: linear-gradient(black, darkblue);");
            }
        });

        //Setting styles to labels.
        principleAmountL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        interestRateL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;  ");
        futureValueL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black; ");
        timeL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        paymentL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        loanL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        mortgageL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        payment2L.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        noOfPaymentsL.setStyle("-fx-font: bolder; -fx-font-family: 'Arial Black'; -fx-text-fill: black;");
        headingL.setStyle("-fx-text-fill: black; -fx-font-size: 50px; -fx-font-family: Forte;");

        principleAmountL.setUnderline(true);
        interestRateL.setUnderline(true);
        futureValueL.setUnderline(true);
        timeL.setUnderline(true);
        paymentL.setUnderline(true);
        payment2L.setUnderline(true);
        mortgageL.setUnderline(true);
        loanL.setUnderline(true);
        noOfPaymentsL.setUnderline(true);

        //Adding image to help button.
        Image image3 = new Image(getClass().getResourceAsStream("/help-icon.png"));
        ImageView imageView3 = new ImageView(image3);
        help.setGraphic(imageView3);
        imageView3.setFitWidth(45);
        imageView3.setFitHeight(45);

        //Adding image to calculators.
        Image image2 = new Image(getClass().getResourceAsStream("/hi.png"));
        ImageView imageView2 = new ImageView(image2);
        history.setGraphic(imageView2);
        imageView2.setFitHeight(50);
        imageView2.setFitWidth(55);

        //Setting layout and size for buttons and label.
        btn1.setLayoutY(335);
        btn1.setLayoutX(18);
        btn1.setMinSize(165, 70);
        btn2.setLayoutY(420);
        btn2.setLayoutX(18);
        btn2.setMinSize(175, 70);
        btn3.setLayoutY(335);
        btn3.setLayoutX(215);
        btn3.setMinSize(175, 70);
        btn4.setLayoutY(420);
        btn4.setLayoutX(215);
        btn4.setMinSize(175, 70);
        historyArea.setMinSize(200,400);
        btnT1.setLayoutX(400);
        btnT1.setLayoutY(100);
        btnT2.setLayoutX(460);
        btnT2.setLayoutY(100);
        btnT3.setLayoutX(520);
        btnT3.setLayoutY(100);
        btnT4.setLayoutX(400);
        btnT4.setLayoutY(160);
        btnT5.setLayoutX(460);
        btnT5.setLayoutY(160);
        btnT6.setLayoutX(520);
        btnT6.setLayoutY(160);
        btnT7.setLayoutX(400);
        btnT7.setLayoutY(220);
        btnT8.setLayoutX(460);
        btnT8.setLayoutY(220);
        btnT9.setLayoutX(520);
        btnT9.setLayoutY(220);
        btnTC.setLayoutX(400);
        btnTC.setLayoutY(280);
        btnT0.setLayoutX(460);
        btnT0.setLayoutY(280);
        btnTD.setLayoutX(520);
        btnTD.setLayoutY(280);
        btnTE.setLayoutX(400);
        btnTE.setLayoutY(340);
        clear.setLayoutX(30);
        clear.setLayoutY(430);
        btnTM.setLayoutX(520);
        btnTM.setLayoutY(340);
        history.setLayoutX(530);
        help.setLayoutX(538);
        help.setLayoutY(445);
        back.setLayoutX(2);
        back.setLayoutY(4);
        headingL.setLayoutX(105);
        headingL.setLayoutY(30);

        //Adding image to back button.
        Image icon = new Image(getClass().getResourceAsStream("/back.png"));
        ImageView iconView = new ImageView(icon);
        back.setGraphic(iconView);
        iconView.setFitHeight(45);
        iconView.setFitWidth(45);

        //Adding image to start window.
        Image image = new Image(getClass().getResourceAsStream("/bg.jpg"));
        ImageView imageView = new ImageView(image);
        Pane layout = new Pane(imageView);
        imageView.setFitHeight(500);
        imageView.setFitWidth(600);

        //Setting up primary stage with a scene containing a pane.
        primaryStage.setTitle("Finance Calculator");
        Scene myScene = new Scene(layout, 600, 500); //Creating main scene.
        layout.getChildren().addAll(btn1, btn2, btn3, btn4, headingL, help); //Adding all the nodes to the pane.
        primaryStage.setScene(myScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //Creating a stage for history.
        Stage historyStage = new Stage();
        historyStage.setResizable(false);

        //Switching to help window.
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Operations.help(back, myScene, primaryStage);
            }
        });

        /*Following setOnAction events contain onActionEvent method call from AlphaMethod class and the buttons,
        text fields, labels, text area, stages, scene and a string are passed as arguments.*/
        //Switching to Savings(Without Payment) window.
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AlphaMethod.onActionEvent(btnT1, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnT8, btnT9, btnT0,
                            btnTD, btnTC, btnTE, btnTM, back, clear, history, principleAmount, interestRate,
                            futureValue, time, extra, principleAmountL, interestRateL, futureValueL, timeL, paymentL,
                            historyArea, "Savings(Without Payment)", primaryStage, historyStage, myScene,
                            "btn1");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Switching to Savings(With Payment) window.
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AlphaMethod.onActionEvent(btnT1, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnT8, btnT9, btnT0,
                            btnTD, btnTC, btnTE, btnTM, back, clear, history, principleAmount2, interestRate2,
                            futureValue2, payment, time2, principleAmountL, interestRateL, futureValueL, paymentL,
                            timeL, historyArea, "Savings(With Payment)", primaryStage, historyStage, myScene,
                            "btn2");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Switching to Mortgage window.
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AlphaMethod.onActionEvent(btnT1, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnT8, btnT9, btnT0,
                            btnTD, btnTC, btnTE, btnTM, back, clear, history, interestRate3, mortgageAmount, payment2,
                            time3, extra, interestRateL, mortgageL , payment2L, timeL, principleAmountL, historyArea,
                            "Mortgage", primaryStage, historyStage, myScene, "btn3");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        //Switching to Loan window.
        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AlphaMethod.onActionEvent(btnT1, btnT2, btnT3, btnT4, btnT5, btnT6, btnT7, btnT8, btnT9, btnT0,
                            btnTD, btnTC, btnTE, btnTM, back, clear, history, interestRate4, loanAmount, payment3,
                            time4, extra, interestRateL, loanL, payment2L, noOfPaymentsL, principleAmountL, historyArea,
                            "Loan", primaryStage, historyStage, myScene, "btn4");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
