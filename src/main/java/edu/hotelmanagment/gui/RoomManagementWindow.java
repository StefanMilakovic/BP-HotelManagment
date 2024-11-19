package edu.hotelmanagment.gui;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.model.Room;
import edu.hotelmanagment.model.RoomHousekeeping;
import edu.hotelmanagment.dao.EmployeeDAO;
import edu.hotelmanagment.dao.RoomDAO;
import edu.hotelmanagment.dao.RoomHousekeepingDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;


public class RoomManagementWindow {
    TableView<Room> tableView = new TableView<>();
    ObservableList<Room> rooms;

    ObservableList<RoomHousekeeping> housekeepingData;
    TableView<RoomHousekeeping> housekeepingTableView;

    public RoomManagementWindow() {

        Stage roomStage = new Stage();
        roomStage.setTitle("Room Management");

        Button addRoom = new Button("Add Room");
        Button deleteRoom = new Button("Delete Room");
        Button editRoom = new Button("Edit Room");
        Button housekeepingButton = new Button("Room Housekeeping");
        addRoom.setOnAction(e -> handleAddRoom());
        deleteRoom.setOnAction(e -> handleDeleteRoom());
        editRoom.setOnAction(e->handleEditRoom());
        housekeepingButton.setOnAction(e->handleRoomHousekeeping());

        TextField roomIdToDeleteField = new TextField();
        roomIdToDeleteField.setPromptText("Enter Room ID to delete");


        addRoom.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;");
        deleteRoom.setStyle("-fx-background-color: #de3a3a; -fx-text-fill: white;");
        editRoom.setStyle("-fx-background-color: #ded93a;-fx-text-fill: black;");


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

        rooms=FXCollections.observableArrayList(RoomDAO.selectAll());
        tableView.setItems(rooms);

        tableView.setFixedCellSize(25);
        tableView.setPrefHeight(tableView.getFixedCellSize() * 4 + 28);

        Separator separator = new Separator();

        Label housekeepingLabel = new Label("Room Housekeeping");
        housekeepingLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TableColumn<RoomHousekeeping, Integer> roomHousekeepingIDColumn = new TableColumn<>("Housekeeping ID");
        roomHousekeepingIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomHousekeepingID"));

        TableColumn<RoomHousekeeping, java.sql.Date> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<RoomHousekeeping, Integer> roomIDHousekeepingColumn = new TableColumn<>("Room ID");
        roomIDHousekeepingColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<RoomHousekeeping, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        housekeepingTableView = new TableView<>();
        housekeepingTableView.getColumns().addAll(roomHousekeepingIDColumn, dateColumn, roomIDHousekeepingColumn, employeeIDColumn);

        housekeepingData = FXCollections.observableArrayList(RoomHousekeepingDAO.selectAll());
        housekeepingTableView.setItems(housekeepingData);

        housekeepingTableView.setFixedCellSize(25);
        housekeepingTableView.setPrefHeight(housekeepingTableView.getFixedCellSize() * 4 + 28);

        HBox addButtonBox = new HBox(10, addRoom, editRoom, deleteRoom, housekeepingButton);
        addButtonBox.setPadding(new Insets(2, 0, 2, 0));
        addButtonBox.setStyle("-fx-alignment: center-left;");

        VBox mainLayout = new VBox(10, addButtonBox, tableView, separator, housekeepingLabel, housekeepingTableView);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 600, 400);
        roomStage.setScene(scene);
        roomStage.show();
    }

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
            RoomDAO.insert(newRoom);
            reloadData();
            dialogStage.close();
        }
    });

    VBox buttonBox = new VBox(10, addButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10, 0, 0, 0));

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
    formGrid.add(buttonBox, 0, 4, 2, 1);

    Scene dialogScene = new Scene(formGrid, 270, 250);
    dialogStage.setScene(dialogScene);

    dialogStage.initModality(Modality.APPLICATION_MODAL);
    dialogStage.showAndWait();
}

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
                RoomDAO.delete(roomId);
                reloadData();
                dialogStage.close();
            }});

        VBox buttonBox = new VBox(10, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(roomIdLabel, 0, 0);
        formGrid.add(roomIdTextField, 1, 0);
        formGrid.add(buttonBox, 0, 1, 2, 1);

        Scene dialogScene = new Scene(formGrid, 200, 100);
        dialogStage.setScene(dialogScene);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }
    private void handleEditRoom() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Room");

        Label roomIdLabel = new Label("Room ID:");
        TextField roomIdTextField = new TextField();
        roomIdTextField.setPromptText("Enter Room ID");

        Button confirmButton = new Button("Confirm");

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

        Button updateButton = new Button("Update Room");

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(roomIdLabel, 0, 0);
        formGrid.add(roomIdTextField, 1, 0);
        formGrid.add(confirmButton, 1, 1);

        formGrid.add(floorLabel, 0, 2);
        formGrid.add(floorComboBox, 1, 2);
        formGrid.add(roomTypeLabel, 0, 3);
        formGrid.add(roomTypeComboBox, 1, 3);
        formGrid.add(bedTypeLabel, 0, 4);
        formGrid.add(bedTypeComboBox, 1, 4);
        formGrid.add(amenitiesLabel, 0, 5);
        formGrid.add(amenitiesTextField, 1, 5);

        VBox buttonBox = new VBox(10, updateButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        formGrid.add(buttonBox, 0, 6, 2, 1);

        updateButton.setOnAction(e -> {
            String floor = floorComboBox.getValue();
            String roomType = roomTypeComboBox.getValue();
            String bedType = bedTypeComboBox.getValue();
            String amenities = amenitiesTextField.getText();

            if (floor == null || roomType == null || bedType == null) {
                System.out.println("Invalid Input, Please fill in all fields.");
            } else {
                int roomId = Integer.parseInt(roomIdTextField.getText());
                int floorValue = Integer.parseInt(floor);
                Room updatedRoom = new Room(roomId, null, floorValue, roomType, bedType, null, amenities);
                RoomDAO.update(updatedRoom);
                reloadData();
                dialogStage.close();
                System.out.println("Room Updated:");
            }
        });

        Scene dialogScene = new Scene(formGrid, 300, 350);
        dialogStage.setScene(dialogScene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();

    }

    private void reloadData() {
        rooms.clear();
        rooms=FXCollections.observableArrayList(RoomDAO.selectAll());
        tableView.setItems(rooms);
    }
    private void reloadHousekeepingTable()
    {
        housekeepingData.clear();
        housekeepingData = FXCollections.observableArrayList(RoomHousekeepingDAO.selectAll());
        housekeepingTableView.setItems(housekeepingData);
    }

    private void handleRoomHousekeeping()
    {
        Stage housekeepingStage = new Stage();
        housekeepingStage.setTitle("Room Housekeeping");

        Label roomIdLabel = new Label("Enter Room ID:");
        TextField roomIdTextField = new TextField();
        roomIdTextField.setPromptText("Room ID");
        roomIdTextField.setMaxWidth(150);

        Label dateLabel = new Label("Select Date:");
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date");
        datePicker.setMaxWidth(150);

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            String roomId = roomIdTextField.getText();
            if (roomId.isEmpty()) {
                System.out.println("Please enter a valid Room ID.");
                return;
            }
            System.out.println("Room ID confirmed: " + roomId);
        });

        Label employeeLabel = new Label("Select Employee:");
        ComboBox<Employee> employeeComboBox = new ComboBox<>();
        employeeComboBox.setPromptText("Select Employee");
        employeeComboBox.setPrefWidth(150);

        List<Employee> employees = EmployeeDAO.selectHousekeepers();
        for (Employee employee : employees) {
            employeeComboBox.getItems().add(employee);
        }

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            Employee selectedWorker = employeeComboBox.getValue();
            String roomId = roomIdTextField.getText();
            LocalDate date = datePicker.getValue();

            if (selectedWorker == null || roomId.isEmpty() || date == null) {
                System.out.println("Please select an employee, enter a valid Room ID, and select a date.");
                return;
            }

            RoomHousekeeping rh=new RoomHousekeeping(null,java.sql.Date.valueOf(date),Integer.parseInt(roomId),selectedWorker.getEmployeeID());
            RoomHousekeepingDAO.insert(rh);

            housekeepingStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.add(roomIdLabel, 0, 0);
        gridPane.add(roomIdTextField, 1, 0);
        gridPane.add(confirmButton, 1, 1);
        gridPane.add(dateLabel, 0, 2);
        gridPane.add(datePicker, 1, 2);
        gridPane.add(employeeLabel, 0, 3);
        gridPane.add(employeeComboBox, 1, 3);
        gridPane.add(addButton, 1, 4);

        Scene scene = new Scene(gridPane, 300, 230);
        housekeepingStage.setScene(scene);
        housekeepingStage.initModality(Modality.APPLICATION_MODAL);
        housekeepingStage.showAndWait();
        reloadHousekeepingTable();
    }
}
