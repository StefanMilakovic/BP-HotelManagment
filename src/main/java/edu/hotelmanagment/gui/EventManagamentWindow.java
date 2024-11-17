package edu.hotelmanagment.gui;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.model.Event;
import edu.hotelmanagment.model.EventHasGuest;
import edu.hotelmanagment.model.Guest;
import edu.hotelmanagment.wrapper.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EventManagamentWindow
{
    TableView<Guest> guestTableView = new TableView<>();
    ObservableList<Guest> guests;

    ObservableList<Event> events;
    TableView<Event> eventTableView;
    //Integer employeeID;
    public EventManagamentWindow()
    {
        Stage eventStage = new Stage();
        eventStage.setTitle("Event Management");
        eventStage.setResizable(false);

        Button addEvent = new Button("Add Event");
        Button deleteEvent = new Button("Delete Event");
        Button addGuestsEvent = new Button("Add Guests");
        //Button editEvent = new Button("Edit Event");

        addEvent.setOnAction(e -> addNewEvent());
        //editEvent.setOnAction(e -> editEvent());
        deleteEvent.setOnAction(e -> deleteEvent());
        addGuestsEvent.setOnAction(e->addGuests());

        addEvent.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;");
        deleteEvent.setStyle("-fx-background-color: #de3a3a; -fx-text-fill: white;");
        //editEvent.setStyle("-fx-background-color: #ded93a; -fx-text-fill: black;"); // Yellow button with black text

        TableColumn<Event, Integer> eventIDColumn = new TableColumn<>("Event ID");
        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));

        TableColumn<Event, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Event, java.sql.Date> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableColumn<Event, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Event, String> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));

        eventTableView = new TableView<>();
        eventTableView.getColumns().addAll(
                eventIDColumn, nameColumn, eventDateColumn,
                locationColumn, descriptionColumn,employeeIDColumn
        );

        events = FXCollections.observableArrayList(WrapperEvent.selectAll());
        eventTableView.setItems(events);


        eventTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Event selectedEvent = eventTableView.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    int eventID = selectedEvent.getEventID();
                    showGuestsForEvent(eventID);
                }
            }
        });

        HBox buttonBox = new HBox(10, addEvent, deleteEvent,addGuestsEvent);
        buttonBox.setPadding(new Insets(2, 0, 2, 0));
        buttonBox.setStyle("-fx-alignment: center-left;");

        VBox mainLayout = new VBox(10, buttonBox, eventTableView);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 550, 400);
        eventStage.setScene(scene);
        eventStage.show();
    }

    private void addNewEvent()
    {
        Stage newEventStage = new Stage();
        newEventStage.setTitle("Add New Event");

        int fieldWidth = 150;

// Name label and field
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        nameTextField.setPrefWidth(fieldWidth);

// Event date label and picker
        Label eventDateLabel = new Label("Event Date:");
        DatePicker eventDatePicker = new DatePicker();
        eventDatePicker.setPrefWidth(fieldWidth);

// Location label and field
        Label locationLabel = new Label("Location:");
        TextField locationTextField = new TextField();
        locationTextField.setPrefWidth(fieldWidth);

        Label descriptionLabel = new Label("Description:");
        TextField descriptionTextField = new TextField();
        descriptionTextField.setPrefWidth(fieldWidth);
        descriptionTextField.setPrefHeight(80);

        Label employeeLabel = new Label("Employee:");

        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setMaxWidth(fieldWidth);
        List<Integer> employeeIDs = new ArrayList<>();

        employeeComboBox.setOnShowing(e -> {
            employeeComboBox.getItems().clear();
            employeeIDs.clear();

            List<Employee> employees = WrapperEmployee.selectManagers(); // Metoda za selekciju menadžera
            for (Employee emp : employees) {
                employeeComboBox.getItems().add(emp.getFirstName() + " " + emp.getLastName());
                employeeIDs.add(emp.getEmployeeID());
            }
        });

        final IntegerProperty selectedEmployeeID = new SimpleIntegerProperty();
        employeeComboBox.setOnAction(e -> {
            int selectedIndex = employeeComboBox.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                selectedEmployeeID.set(employeeIDs.get(selectedIndex));
            }
        });

        Button addButton = new Button("Add Event");
        addButton.setOnAction(e -> {
            String name = nameTextField.getText();
            java.sql.Date eventDate = null;
            if (eventDatePicker.getValue() != null) {
                eventDate = java.sql.Date.valueOf(eventDatePicker.getValue());
            }
            String location = locationTextField.getText();
            String description = descriptionTextField.getText();
            Integer employeeID = selectedEmployeeID.get();

            if (name.isEmpty() || eventDate == null || location.isEmpty() || employeeID == null) {
                System.out.println("Invalid Input, Please fill in all required fields.");
            } else {
                Event newEvent = new Event(null, name, eventDate, location, description, employeeID);
                WrapperEvent.insert(newEvent);
                reloadData();
                newEventStage.close();
            }
        });

        VBox buttonBox = new VBox(10, addButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameTextField, 1, 0);
        formGrid.add(eventDateLabel, 0, 1);
        formGrid.add(eventDatePicker, 1, 1);
        formGrid.add(locationLabel, 0, 2);
        formGrid.add(locationTextField, 1, 2);
        formGrid.add(descriptionLabel, 0, 3);
        formGrid.add(descriptionTextField, 1, 3);
        formGrid.add(employeeLabel, 0, 4);
        formGrid.add(employeeComboBox, 1, 4);
        formGrid.add(buttonBox, 0, 5, 2, 1); // Center the button and span two columns

        Scene dialogScene = new Scene(formGrid, 280, 300);
        newEventStage.setScene(dialogScene);

        newEventStage.initModality(Modality.APPLICATION_MODAL);
        newEventStage.showAndWait();

    }
    private void editEvent()
    {

    }
    private void deleteEvent()
    {
        Stage deleteEvent = new Stage();
        deleteEvent.setTitle("Delete Event");

        double textFieldWidth = 100;

        Label eventIdLabel = new Label("Event ID:");
        TextField eventIdTextField = new TextField();
        eventIdTextField.setPrefWidth(textFieldWidth);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String eventIdText = eventIdTextField.getText();
            if (eventIdText.isEmpty()) {
                System.out.println("Please enter a Room ID.");
            } else {
                int eventId = Integer.parseInt(eventIdText);
                WrapperEvent.delete(eventId);
                reloadData();
                deleteEvent.close();
            }});

        VBox buttonBox = new VBox(10, deleteButton); // Button with spacing
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(eventIdLabel, 0, 0);
        formGrid.add(eventIdTextField, 1, 0);
        formGrid.add(buttonBox, 0, 1, 2, 1); // Center the button and span two columns

        Scene dialogScene = new Scene(formGrid, 200, 100); // Adjusted size for Room ID input
        deleteEvent.setScene(dialogScene);

        deleteEvent.initModality(Modality.APPLICATION_MODAL);
        deleteEvent.showAndWait();
    }

    private void addGuests()
    {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Guests to Event");
        dialogStage.setResizable(false);

        Label selectEventLabel = new Label("Select Event:");
        ComboBox<String> eventComboBox = new ComboBox<>();
        eventComboBox.setPrefWidth(200);
        List<Integer> eventIDs = new ArrayList<>();

        List<Event> events = WrapperEvent.selectAll();
        for (Event event : events) {
            eventComboBox.getItems().add("Event ID " + event.getEventID() + ": " + event.getName());
            eventIDs.add(event.getEventID());
        }

        Separator separator = new Separator();

        Label headerLabel = new Label("Select guests:");
        headerLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

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

        guestTableView.getColumns().setAll(
                guestIDColumn, firstNameColumn, lastNameColumn,
                passportNumberColumn, emailColumn, phoneNumberColumn
        );

        guests = FXCollections.observableArrayList(WrapperGuest.selectAll());
        guestTableView.setItems(guests);

        guestTableView.setFixedCellSize(25);
        guestTableView.setPrefHeight(guestTableView.getFixedCellSize() * 6 + 28);

        guestTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            Integer selectedEventIndex = eventComboBox.getSelectionModel().getSelectedIndex();
            if (selectedEventIndex < 0) {
                System.out.println("Please select an event first.");
                return;
            }

            int selectedEventID = eventIDs.get(selectedEventIndex);

            ObservableList<Guest> selectedGuests = guestTableView.getSelectionModel().getSelectedItems();
            if (!selectedGuests.isEmpty()) {
                for (Guest guest : selectedGuests) {
                    EventHasGuest eg=new EventHasGuest(selectedEventID, guest.getGuestID());
                    WrapperEventHasGuest.insert(eg);
                }
            }
            dialogStage.close();
        });

        VBox buttonBox = new VBox(confirmButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        VBox mainLayout = new VBox(10, selectEventLabel, eventComboBox, separator, headerLabel, guestTableView, buttonBox);
        mainLayout.setPadding(new Insets(10));

        Scene dialogScene = new Scene(mainLayout, 500, 500); // Podesavanje velicine prozora
        dialogStage.setScene(dialogScene);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }

    private void reloadData()
    {
        events.clear();
        events = FXCollections.observableArrayList(WrapperEvent.selectAll());
        eventTableView.setItems(events);
    }

    private void showGuestsForEvent(int eventID) {
        Stage guestStage = new Stage();
        guestStage.setTitle("Guests for Event ID " + eventID);

        TableView<Guest> guestTableView = new TableView<>();

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

        ObservableList<Guest> guests = FXCollections.observableArrayList(WrapperGuest.selectByEventID(eventID));
        guestTableView.setItems(guests);

        VBox layout = new VBox(10, guestTableView);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout, 500, 300);
        guestStage.setScene(scene);
        guestStage.initModality(Modality.APPLICATION_MODAL);
        guestStage.showAndWait();
    }


}
