package edu.hotelmanagment.gui;

import edu.hotelmanagment.model.*;
import edu.hotelmanagment.wrapper.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ReservationManagementWindow
{
    TableView<Reservation> reservationTableView = new TableView<>();
    ObservableList<Reservation> reservations;

    TableView<Guest> guestTableView = new TableView<>();
    ObservableList<Guest> guests;

    TableView<Room> roomTableView = new TableView<>();
    ObservableList<Room> rooms;

    java.sql.Date checkInDate;
    java.sql.Date checkOutDate;
    AtomicInteger numberOfGuests = new AtomicInteger();
    AtomicInteger guestID = new AtomicInteger();
    int roomID;
    AtomicInteger reservationTypeID = new AtomicInteger();
    AtomicInteger employeeID = new AtomicInteger();


    public ReservationManagementWindow()
    {
        Stage roomStage = new Stage();
        roomStage.setTitle("Reservation Management");
        roomStage.setResizable(false);

        Button addReservation = new Button("Add Reservation");
        Button deleteReservation = new Button("Delete Reservation");
        Button editReservation = new Button("Edit Reservation");
        addReservation.setOnAction(e -> addNewReservation());
        editReservation.setOnAction(e-> editReservation());
        deleteReservation.setOnAction(e -> deleteReservation());

        TextField roomIdToDeleteField = new TextField();
        roomIdToDeleteField.setPromptText("Enter Room ID to delete");

        addReservation.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;"); // Zeleno dugme sa crnim tekstom
        deleteReservation.setStyle("-fx-background-color: #de3a3a; -fx-text-fill: white;"); // Crveno dugme sa crnim tekstom
        editReservation.setStyle("-fx-background-color: #ded93a;-fx-text-fill: black;");

        TableColumn<Reservation, Integer> reservationIDColumn = new TableColumn<>("Reservation ID");
        reservationIDColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));

        TableColumn<Reservation, java.sql.Date> checkInDateColumn = new TableColumn<>("Check-In Date");
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        TableColumn<Reservation, java.sql.Date> checkOutDateColumn = new TableColumn<>("Check-Out Date");
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        TableColumn<Reservation, Integer> numberOfGuestsColumn = new TableColumn<>("Number of Guests");
        numberOfGuestsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));

        TableColumn<Reservation, Integer> guestIDColumn = new TableColumn<>("Guest ID");
        guestIDColumn.setCellValueFactory(new PropertyValueFactory<>("guestID"));

        TableColumn<Reservation, Integer> roomIDColumn = new TableColumn<>("Room ID");
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<Reservation, Integer> reservationTypeIDColumn = new TableColumn<>("Reservation Type ID");
        reservationTypeIDColumn.setCellValueFactory(new PropertyValueFactory<>("reservationTypeID"));

        TableColumn<Reservation, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));


        reservationTableView.getColumns().addAll(
                reservationIDColumn, checkInDateColumn, checkOutDateColumn,
                numberOfGuestsColumn, guestIDColumn, roomIDColumn,
                reservationTypeIDColumn, employeeIDColumn
        );

        reservations = FXCollections.observableArrayList(WrapperReservation.selectAll());
        reservationTableView.setItems(reservations);

        reservationTableView.setOnMouseClicked(event -> {
           // System.out.println("Red je selektovan!");
        });


        HBox addButtonBox = new HBox(10, addReservation, editReservation, deleteReservation);
        addButtonBox.setPadding(new Insets(2, 0, 2, 0));
        addButtonBox.setStyle("-fx-alignment: center-left;");

        VBox mainLayout = new VBox(10, addButtonBox, reservationTableView);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 780, 400);
        roomStage.setScene(scene);
        roomStage.show();

    }

    private void addNewReservation()
    {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Reservation");
        dialogStage.setResizable(false);

        guestTableView.getSelectionModel().clearSelection();
        roomTableView.getSelectionModel().clearSelection();

        Label headerLabel = new Label("Select a guest:");
        headerLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label dateLabel = new Label("Select dates:");
        dateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button addButton = new Button("Add New Guest");
        addButton.setOnAction(e -> {
            addNewGuest();
        });

        TableColumn<Guest, Integer> guestIDColumn = new TableColumn<>("Guest ID");
        guestIDColumn.setCellValueFactory(new PropertyValueFactory<>("GuestID"));

        TableColumn<Guest, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

        TableColumn<Guest, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));

        TableColumn<Guest, String> passportNumberColumn = new TableColumn<>("Passport Number");
        passportNumberColumn.setCellValueFactory(new PropertyValueFactory<>("passportNumber"));

        TableColumn<Guest, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Guest, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        guestTableView.getColumns().addAll(
                guestIDColumn, firstNameColumn, lastNameColumn,
                passportNumberColumn, emailColumn, phoneNumberColumn
        );

        guests = FXCollections.observableArrayList(WrapperGuest.selectAll());
        guestTableView.setItems(guests);

        guestTableView.setFixedCellSize(25);
        guestTableView.setPrefHeight(guestTableView.getFixedCellSize() * 3 + 28);

        guestTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        guestTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guestID.set(newValue.getGuestID());
                //System.out.println("Selected Guest ID: " + newValue.getGuestID());//--------------------------------------
            }
        });
        guestTableView.setRowFactory(tv -> {
            TableRow<Guest> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #5fa62d;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        Separator separator = new Separator();

        Button selectDates = new Button("Select Dates");
        selectDates.setOnAction(e -> {
            pickDates();
        });

        Region spacer = new Region();
        Separator dateSeparator = new Separator();

        Label selectRoomLabel = new Label("Select a room:");
        selectRoomLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

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

        roomTableView.getColumns().addAll(roomIDColumn, roomNumberColumn, floorColumn, roomTypeColumn, bedTypeColumn,priceColumn,amenitiesColumn);

        roomTableView.setFixedCellSize(25);
        roomTableView.setPrefHeight(roomTableView.getFixedCellSize() * 3+28);

        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //System.out.println("Selected Room ID: " + newValue.getRoomID());
                roomID=newValue.getRoomID();
            }
        });
        roomTableView.setRowFactory(tv -> {
            TableRow<Room> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #5fa62d;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });


        Separator roomSeparator = new Separator();

        Label numberOfGuestsLabel = new Label("Select number of guests:");
        numberOfGuestsLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<Integer> numberOfGuestsComboBox = new ComboBox<>();
        numberOfGuestsComboBox.setMaxWidth(150);

        for (int i = 1; i <= 6; i++) {
            numberOfGuestsComboBox.getItems().add(i);
        }

        numberOfGuestsComboBox.setOnAction(e -> {
            Integer selectedNumberOfGuests = numberOfGuestsComboBox.getValue();
            if (selectedNumberOfGuests != null) {
                numberOfGuests.set(selectedNumberOfGuests);
                //System.out.println("Number of guests: " + selectedNumberOfGuests);//------------------------------------------
            }
        });


        Separator guestSeparator = new Separator();
        Label reservationMethodLabel = new Label("Select Reservation Method:");
        reservationMethodLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<String> reservationMethodComboBox = new ComboBox<>();
        reservationMethodComboBox.getItems().addAll("Phone", "Hotel Website", "Walk-in", "Booking.com");

        reservationMethodComboBox.setOnAction(e -> {
            int selectedIndex = reservationMethodComboBox.getSelectionModel().getSelectedIndex();
            //System.out.println("Selected Reservation Method Index: " + selectedIndex);  // Ispisuje redni broj---------------------------------------------------------------
            reservationTypeID.set(selectedIndex + 1);
        });

        reservationMethodComboBox.setMaxWidth(150);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox headerBox = new HBox(10, headerLabel, spacer, addButton);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Separator separator1 = new Separator();
        Label employeeLabel = new Label("Select the employee creating the reservation:");
        employeeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setMaxWidth(150);
        List<Integer> employeeIDs = new ArrayList<>();

        employeeComboBox.setOnShowing(e -> {
            employeeComboBox.getItems().clear();
            employeeIDs.clear();

            List<Employee> receptionists = WrapperEmployee.selectReceptionists();
            for (Employee emp : receptionists) {
                employeeComboBox.getItems().add(emp.getFirstName() + " " + emp.getLastName());
                employeeIDs.add(emp.getEmployeeID());
            }
        });

        employeeComboBox.setOnAction(e -> {
            int selectedIndex = employeeComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int selectedEmployeeID = employeeIDs.get(selectedIndex); // Dohvatanje ID-a prema indeksu
                //System.out.println("Selected Employee ID: " + selectedEmployeeID);//------------------------------------------------------------
                employeeID.set(selectedEmployeeID);
            }
        });

        VBox employeeBox = new VBox(5);
        employeeBox.getChildren().addAll(employeeLabel, employeeComboBox);

        Separator separator2 = new Separator();
        Button addReservationButton = new Button("Add Reservation");

        addReservationButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5fa62d; -fx-text-fill: white;");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(separator2, addReservationButton);

        addReservationButton.setOnAction(e -> {
            WrapperReservation.insert(new Reservation(checkInDate,checkOutDate,numberOfGuests.get(),guestID.get(),roomID,reservationTypeID.get(),employeeID.get()));
            dialogStage.close();
            reloadData();
        });

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(headerBox, guestTableView, separator, dateLabel, selectDates, dateSeparator,
                selectRoomLabel, roomTableView, roomSeparator, numberOfGuestsLabel, numberOfGuestsComboBox, guestSeparator,
                reservationMethodLabel,reservationMethodComboBox,separator1, employeeBox,separator2,vbox);

        Scene scene = new Scene(mainLayout, 650, 700);
        dialogStage.setScene(scene);
        dialogStage.show();

    }
    private void addNewGuest()
    {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add New Guest");
        dialogStage.setResizable(false);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label passportNumberLabel = new Label("Passport Number:");
        TextField passportNumberField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label phoneNumberLabel = new Label("Phone Number:");
        TextField phoneNumberField = new TextField();

        Button addButton = new Button("Add Guest");
        addButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String passportNumber = passportNumberField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || passportNumber.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                System.out.println("Please fill in all fields.");
            } else {
                Guest newGuest = new Guest(null, firstName, lastName, passportNumber, email, phoneNumber);

                WrapperGuest.insert(newGuest);
                //System.out.println("New guest added: " + firstName + " " + lastName);
                dialogStage.close();
            }
            reloadGuest();
        });

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(10));

        formGrid.add(firstNameLabel, 0, 0);
        formGrid.add(firstNameField, 1, 0);
        formGrid.add(lastNameLabel, 0, 1);
        formGrid.add(lastNameField, 1, 1);
        formGrid.add(passportNumberLabel, 0, 2);
        formGrid.add(passportNumberField, 1, 2);
        formGrid.add(emailLabel, 0, 3);
        formGrid.add(emailField, 1, 3);
        formGrid.add(phoneNumberLabel, 0, 4);
        formGrid.add(phoneNumberField, 1, 4);

        VBox buttonBox = new VBox(10, addButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(10, formGrid, buttonBox);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(mainLayout, 300, 250);
        dialogStage.setScene(dialogScene);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }

    private void pickDates() {
        Stage dateStage = new Stage();
        dateStage.setTitle("Select Dates");
        dateStage.setResizable(false);

        Label checkInLabel = new Label("Check-In:");
        DatePicker checkInDatePicker = new DatePicker();
        checkInDatePicker.setValue(LocalDate.now()); // Podrazumevani datum je današnji

        Label checkOutLabel = new Label("Check-Out:");
        DatePicker checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setValue(LocalDate.now().plusDays(1)); // Podrazumevani Check-Out je sutra

// Ograničenje za Check-In: samo budući datumi
        checkInDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now())); // Onemogućava prošle datume
            }
        });

        checkOutDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(checkInDatePicker.getValue().plusDays(1)));
            }
        });

        checkInDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && checkOutDatePicker.getValue().isBefore(newDate.plusDays(1))) {
                checkOutDatePicker.setValue(newDate.plusDays(1)); // Postavi Check-Out datum za 1 dan nakon Check-In datuma
            }
        });

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            //System.out.println("Check-In Date: " + checkInDatePicker.getValue());
            //System.out.println("Check-Out Date: " + checkOutDatePicker.getValue());
            dateStage.close();
            loadAvailableRooms(java.sql.Date.valueOf(checkInDatePicker.getValue()),java.sql.Date.valueOf(checkOutDatePicker.getValue()));
            checkInDate=java.sql.Date.valueOf(checkInDatePicker.getValue());
            checkOutDate=java.sql.Date.valueOf(checkOutDatePicker.getValue());

        });

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER); // Poravnanje dugmeta u centar
        buttonBox.getChildren().add(confirmButton);

        VBox layout = new VBox(10); // Razmak između elemenata
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(checkInLabel, checkInDatePicker, checkOutLabel, checkOutDatePicker, buttonBox);

        Scene scene = new Scene(layout, 200, 200); // Veličina prozora
        dateStage.setScene(scene);
        dateStage.show();
    }

    private void editReservation()
    {
        Stage editReservationStage = new Stage();
        editReservationStage.setTitle("Edit Reservation");
        editReservationStage.setResizable(false);
        final Reservation[] r = {new Reservation()};
        final int[] reservationID = {-1};

        Label selectReservationLabel = new Label("Select Reservation:");
        selectReservationLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField reservationIDField = new TextField();
        reservationIDField.setMaxWidth(150);
        reservationIDField.setPromptText("Enter Reservation ID to edit");

        Button confirmButton = new Button("Confirm");

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    //
                    // System.out.println("Reservation ID:"+reservationIDField.getText());
                    reservationID[0] =Integer.parseInt(reservationIDField.getText());
                    r[0] =WrapperReservation.selectById(reservationID[0]);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid reservation ID entered!");
                }
            }
        });

        Separator separator = new Separator();

        Label selectDateLabel = new Label("Select Dates");
        selectDateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Button selectDates = new Button("Select Dates");
        selectDates.setOnAction(a -> {
            pickDates();

        });

        Separator dateSeparator = new Separator();

        Label selectRoomLabel = new Label("Select a room:");
        selectRoomLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

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

        roomTableView.getColumns().addAll(roomIDColumn, roomNumberColumn, floorColumn, roomTypeColumn, bedTypeColumn,priceColumn,amenitiesColumn);

        roomTableView.setFixedCellSize(25);
        roomTableView.setPrefHeight(roomTableView.getFixedCellSize() * 3+28);

        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        roomTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //System.out.println("Selected Room ID: " + newValue.getRoomID());
                r[0].setRoomID(newValue.getRoomID());
            }
        });
        roomTableView.setRowFactory(tv -> {
            TableRow<Room> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #5fa62d;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        Separator separator2 = new Separator();
        Button updateReservationButton = new Button("Finish");

        updateReservationButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5fa62d; -fx-text-fill: white;");
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(separator2, updateReservationButton);

        updateReservationButton.setOnAction(e -> {
            r[0].setCheckInDate(checkInDate);
            r[0].setCheckOutDate(checkOutDate);
            WrapperReservation.update(r[0]);
            //System.out.println(r[0]);
            editReservationStage.close();
            reloadData();
        });

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll( selectReservationLabel,reservationIDField,confirmButton,separator,selectDateLabel,selectDates, dateSeparator, selectRoomLabel, roomTableView,separator2,vbox);

        Scene scene = new Scene(mainLayout, 650, 430);
        editReservationStage.setScene(scene);
        editReservationStage.show();
    }

    private void deleteReservation()
    {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Delete Reservation");
        dialogStage.setResizable(false);

        double textFieldWidth = 100;

        Label reservationIdLabel = new Label("Reservation ID:");
        TextField reservationIdTextField = new TextField();
        reservationIdTextField.setPrefWidth(textFieldWidth);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String roomIdText = reservationIdTextField.getText();
            if (roomIdText.isEmpty()) {
                System.out.println("Please enter a Reservation ID.");
            } else {
                int reservationId = Integer.parseInt(roomIdText);
                WrapperReservation.delete(reservationId);
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

        formGrid.add(reservationIdLabel, 0, 0);
        formGrid.add(reservationIdTextField, 1, 0);
        formGrid.add(buttonBox, 0, 1, 2, 1); // Center the button and span two columns

        Scene dialogScene = new Scene(formGrid, 250, 110); // Adjusted size for Room ID input
        dialogStage.setScene(dialogScene);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }
    private void reloadGuest() {
        guests.clear();
        guests=FXCollections.observableArrayList(WrapperGuest.selectAll());  // Metoda koja učitava podatke iz baze
        guestTableView.setItems(guests);
    }
    private void reloadData() {
        reservations.clear();
        reservations=FXCollections.observableArrayList(WrapperReservation.selectAll());  // Metoda koja učitava podatke iz baze
        reservationTableView.setItems(reservations);
    }
    private void loadAvailableRooms(java.sql.Date checkInDate, java.sql.Date checkOutDate)
    {
        if (checkInDate != null && checkOutDate != null)
        {
            rooms=FXCollections.observableArrayList(WrapperRoom.getAvailableRooms(checkInDate,checkOutDate));
            roomTableView.setItems(rooms);
        }
    }
}
