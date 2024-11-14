package edu.hotelmanagment.gui;

import edu.hotelmanagment.controller.ControllerRoom;
import edu.hotelmanagment.model.Room;
import edu.hotelmanagment.wrapper.WrapperRoom;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class RoomManagementWindow {
    TableView<Room> tableView = new TableView<>();
    ObservableList<Room> rooms;

    public RoomManagementWindow() {

        Stage roomStage = new Stage();
        roomStage.setTitle("Room Management");

        Button addRoom = new Button("Add Room");
        Button deleteRoom = new Button("Delete Room");
        addRoom.setOnAction(e -> handleAddRoom());
        deleteRoom.setOnAction(e -> handleDeleteRoom());

        TextField roomIdToDeleteField = new TextField();
        roomIdToDeleteField.setPromptText("Enter Room ID to delete");
        //Integer roomIdToDelete = Integer.parseInt(roomIdToDeleteField.getText());

        addRoom.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: black;"); // Zeleno dugme sa crnim tekstom
        deleteRoom.setStyle("-fx-background-color: #de3a3a; -fx-text-fill: black;"); // Crveno dugme sa crnim tekstom




        TableColumn<Room, Integer> roomIDColumn = new TableColumn<>("Room ID");
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<Room, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<Room, Integer> floorColumn = new TableColumn<>("Floor");
        floorColumn.setCellValueFactory(new PropertyValueFactory<>("floor"));

        TableColumn<Room, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<Room, String> bedTypeColumn = new TableColumn<>("Bed Type");
        bedTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bedType"));

        TableColumn<Room, String> priceColumn = new TableColumn<>("Price Per Night");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));

        TableColumn<Room, String> amenitiesColumn = new TableColumn<>("Amenities");
        amenitiesColumn.setCellValueFactory(new PropertyValueFactory<>("amenities"));

        tableView.getColumns().addAll(roomIDColumn, roomNumberColumn, floorColumn, roomTypeColumn, bedTypeColumn,priceColumn,amenitiesColumn);

        rooms=FXCollections.observableArrayList(WrapperRoom.selectAll());
        tableView.setItems(rooms);



        HBox addButtonBox = new HBox(10, addRoom,deleteRoom);
        addButtonBox.setPadding(new Insets(2, 0, 2, 0));
        addButtonBox.setStyle("-fx-alignment: center-left;");

        VBox mainLayout = new VBox(10, addButtonBox, tableView);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 605, 400);
        roomStage.setScene(scene);
        roomStage.show();

    }

//-------------------------------------------------------------------------------------------------------------------------
private void handleAddRoom() {
    Stage dialogStage = new Stage();
    dialogStage.setTitle("Add New Room");

    double comboBoxWidth = 150;

    Label floorLabel = new Label("Floor:");
    ComboBox<String> floorComboBox = new ComboBox<>();
    floorComboBox.setPrefWidth(comboBoxWidth);
    floorComboBox.getItems().addAll("1", "2", "3", "4");
    floorComboBox.setPromptText("Select Floor");

    Label roomTypeLabel = new Label("Room Type:");
    ComboBox<String> roomTypeComboBox = new ComboBox<>();
    roomTypeComboBox.setPrefWidth(comboBoxWidth);
    roomTypeComboBox.getItems().addAll("Single Room", "Double Room", "Suite", "Deluxe Room");
    roomTypeComboBox.setPromptText("Select Room Type");

    Label bedTypeLabel = new Label("Bed Type:");
    ComboBox<String> bedTypeComboBox = new ComboBox<>();
    bedTypeComboBox.setPrefWidth(comboBoxWidth);
    bedTypeComboBox.getItems().addAll("Single Bed", "Double Bed", "King Bed", "Sofa Bed");
    bedTypeComboBox.setPromptText("Select Bed Type");

    Label amenitiesLabel = new Label("Amenities:");
    TextField amenitiesTextField = new TextField();
    amenitiesTextField.setPrefWidth(comboBoxWidth);

    Button addButton = new Button("Add Room");
    addButton.setOnAction(e -> {
        String floor = floorComboBox.getValue();
        String roomType = roomTypeComboBox.getValue();
        String bedType = bedTypeComboBox.getValue();
        String amenities = amenitiesTextField.getText();

        if (floor == null || roomType == null || bedType == null) {
            System.out.println("Invalid Input, Please fill in all fields.");
        } else {
            Room newRoom=new Room(null,null,Integer.parseInt(floor),roomType,bedType,null,amenities);
            WrapperRoom.insert(newRoom);
            reloadData();
            dialogStage.close();
        }
    });

    VBox buttonBox = new VBox(10, addButton); // 10px spacing between button and other elements
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Padding to keep distance from form fields

    GridPane formGrid = new GridPane();
    formGrid.setVgap(10);
    formGrid.setHgap(10);
    formGrid.setPadding(new Insets(20));

    formGrid.add(floorLabel, 0, 0);
    formGrid.add(floorComboBox, 1, 0);
    formGrid.add(roomTypeLabel, 0, 1);
    formGrid.add(roomTypeComboBox, 1, 1);
    formGrid.add(bedTypeLabel, 0, 2);
    formGrid.add(bedTypeComboBox, 1, 2);
    formGrid.add(amenitiesLabel, 0, 3);
    formGrid.add(amenitiesTextField, 1, 3);
    formGrid.add(buttonBox, 0, 4, 2, 1); // Center the button and span two columns

    Scene dialogScene = new Scene(formGrid, 270, 250); // Adjusted window size to accommodate new fields
    dialogStage.setScene(dialogScene);

    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.showAndWait();
}

    //---------------------------------------------------------------------------------------------------------

    private void handleDeleteRoom()
    {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Delete Room");

        double textFieldWidth = 100;

        Label roomIdLabel = new Label("Room ID:");
        TextField roomIdTextField = new TextField();
        roomIdTextField.setPrefWidth(textFieldWidth);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String roomIdText = roomIdTextField.getText();
            if (roomIdText.isEmpty()) {
                System.out.println("Please enter a Room ID.");
            } else {
                int roomId = Integer.parseInt(roomIdText);
                WrapperRoom.delete(roomId);
                reloadData();
                dialogStage.close();
            }});

        VBox buttonBox = new VBox(10, deleteButton); // Button with spacing
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(roomIdLabel, 0, 0);
        formGrid.add(roomIdTextField, 1, 0);
        formGrid.add(buttonBox, 0, 1, 2, 1); // Center the button and span two columns

        Scene dialogScene = new Scene(formGrid, 200, 100); // Adjusted size for Room ID input
        dialogStage.setScene(dialogScene);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }


    private void handleModifyRoom() {
        // Modifikovanje odabrane sobe
        System.out.println("Modifying selected room...");
    }

    private void handleViewRoom() {
        // Prikazivanje detalja o odabranoj sobi
        System.out.println("Viewing details for selected room...");
    }

    private void reloadData() {
        rooms.clear();
        rooms=FXCollections.observableArrayList(WrapperRoom.selectAll());  // Metoda koja učitava podatke iz baze
        tableView.setItems(rooms);
    }
}
