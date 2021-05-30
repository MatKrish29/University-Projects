package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        //Creating a table view
        TableView table = new TableView();
        table.setEditable(false);

        //Creating columns
        TableColumn number = new TableColumn("Membership Number");
        number.setCellValueFactory(new PropertyValueFactory<GymMember, Integer>("membershipNumber"));

        TableColumn memberName = new TableColumn("Member Name");
        memberName.setCellValueFactory(new PropertyValueFactory<GymMember, String>("name"));

        TableColumn date = new TableColumn("Start Membership Date");
        date.setCellValueFactory(new PropertyValueFactory<GymMember, String>("startMembershipDate"));

        TableColumn type = new TableColumn("Member Type");
        type.setCellValueFactory(new PropertyValueFactory<GymMember, String>("memberType"));

        TableColumn school = new TableColumn("School Name");
        school.setCellValueFactory(new PropertyValueFactory<GymMember, String>("schoolName"));

        TableColumn over60Age = new TableColumn("Member Age");
        over60Age.setCellValueFactory(new PropertyValueFactory<GymMember, String>("age"));

        table.getColumns().addAll(number, memberName, date, type, school, over60Age);

        //Populating the table
        populateTable(table);

        //Creating a border pane
        BorderPane root = new BorderPane();
        //Creating buttons
        Button search = new Button("Search");
        Button showAll = new Button("Show All");
        //Creating a textfield to give input
        TextField searchBox = new TextField();
        searchBox.setPromptText("*Membership Number*");
        //Creating VBox and HBox
        VBox layout1 = new VBox();
        HBox layout2 = new HBox();

        //Setting up border pane and arranging the margins
        root.setCenter(layout1);
        root.setTop(layout2);
        root.setPadding(new Insets(30, 20, 20, 20));
        BorderPane.setMargin(layout1, new Insets(20));
        HBox.setMargin(search, new Insets(20, 5, 0, 20));
        HBox.setMargin(searchBox, new Insets(20, 0, 0, 5));
        HBox.setMargin(showAll, new Insets(20, 0, 0, 443));

        //Adding nodes to the VBox and HBox
        layout1.getChildren().addAll(table);
        layout2.getChildren().addAll(search ,searchBox, showAll);
        //Setting the stage
        primaryStage.setTitle("Gym Members");
        primaryStage.setScene(new Scene(root, 808, 500));
        primaryStage.show();
        primaryStage.setResizable(false);

        //Styling
        number.setStyle("-fx-alignment: center;");
        date.setStyle("-fx-alignment: center;");
        school.setStyle("-fx-alignment: center;");
        over60Age.setStyle("-fx-alignment: center;");
        table.setStyle("-fx-background-color: black");
        search.setStyle("-fx-background-color: orangered; -fx-text-fill: white; -fx-font-family: 'Arial Black';");
        showAll.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-family: 'Arial Black';");

        //Populating the table with appropriate data when user clicks search button
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search(searchBox, table);
            }
        });

        //Populating the table with all the data when the user clicks show all button
        showAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table.getItems().clear();
                populateTable(table);
            }
        });
    }

    //Method to add the retrieved data into the table
    public void populateTable(TableView table) {
        int data1 = 0;
        String data2 = "";
        String data3 = "";
        String data4 = "";
        String data5 = "";
        String data6 = "";

        ArrayList<Object> membershipNumber = new ArrayList<Object>();
        ArrayList<Object> name = new ArrayList<Object>();
        ArrayList<Object> startMembershipDate = new ArrayList<Object>();
        ArrayList<Object> memberType = new ArrayList<Object>();
        ArrayList<Object> schoolName = new ArrayList<Object>();
        ArrayList<Object> age = new ArrayList<Object>();

        //Retrieving the data and storing them in array lists
        Mongo object = new Mongo();
        membershipNumber = (ArrayList<Object>) object.dataRetrieve("Membership Number");
        name  = (ArrayList<Object>) object.dataRetrieve("Member Name");
        startMembershipDate = (ArrayList<Object>) object.dataRetrieve("Start Membership Date");
        memberType = (ArrayList<Object>) object.dataRetrieve("Member Type");
        schoolName = (ArrayList<Object>) object.dataRetrieve("School Name");
        age = (ArrayList<Object>) object.dataRetrieve("Age");

        //Looping through each and every data and adding them into the table
        for (int i=0; i<object.count; i++) {
            data1 = (int) membershipNumber.get(i);
            data2 = (String) name.get(i);
            data3 = (String) startMembershipDate.get(i);
            data4 = (String) memberType.get(i);
            data5 = (String) schoolName.get(i);
            if (age.get(i).equals("-")) {
                data6 = (String) age.get(i);
            }
            else {
                data6 = String.valueOf(age.get(i));
            }
            GymMember member = new GymMember(data1, data2, data3, data4, data5, data6);
            table.getItems().add(member);
        }
    }

    //Method to search the details according to the membership number
    public void search(TextField searchBox, TableView table) {
        int data1 = 0;
        String data2 = "";
        String data3 = "";
        String data4 = "";
        String data5 = "";
        String data6 = "";

        try {
            ArrayList<Object> membershipNumber = new ArrayList<Object>();
            ArrayList<Object> name = new ArrayList<Object>();
            ArrayList<Object> startMembershipDate = new ArrayList<Object>();
            ArrayList<Object> memberType = new ArrayList<Object>();
            ArrayList<Object> schoolName = new ArrayList<Object>();
            ArrayList<Object> age = new ArrayList<Object>();

            //Retrieving the data and storing them in array lists
            Mongo object = new Mongo();
            membershipNumber = (ArrayList<Object>) object.dataRetrieve("Membership Number");
            name  = (ArrayList<Object>) object.dataRetrieve("Member Name");
            startMembershipDate = (ArrayList<Object>) object.dataRetrieve("Start Membership Date");
            memberType = (ArrayList<Object>) object.dataRetrieve("Member Type");
            schoolName = (ArrayList<Object>) object.dataRetrieve("School Name");
            age = (ArrayList<Object>) object.dataRetrieve("Age");

            //Looping through the lists to find the matching membership number
            int count = object.count;
            int memberNumber = Integer.parseInt(searchBox.getText());
            for (int i=0; i<object.count; i++) {
                int number = (int) membershipNumber.get(i);
                if (number==memberNumber) {
                    count-=1;
                    //Assigning all the data found in the particular index to a variable
                    data1 = (int) membershipNumber.get(i);
                    data2 = (String) name.get(i);
                    data3 = (String) startMembershipDate.get(i);
                    data4 = (String) memberType.get(i);
                    data5 = (String) schoolName.get(i);
                    if (age.get(i).equals("-")) {
                        data6 = (String) age.get(i);
                    }
                    else {
                        data6 = String.valueOf(age.get(i));
                    }
                    //Clearing the data before populating the table
                    table.getItems().clear();
                    //Populating the table
                    GymMember member = new GymMember(data1, data2, data3, data4, data5, data6);
                    table.getItems().add(member);
                }
            }
            //Alerting the user if the member is not found
            if (count==object.count) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Member not found!");
                alert.setContentText("Member with membership number " + memberNumber + " doesn't exist!");
                alert.show();
            }
        }
        //Alerting the user if an invalid input is given
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid input!");
            alert.setContentText("Only integer values can be entered!");
            alert.show();
        }
    }
}


