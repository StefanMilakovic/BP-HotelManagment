package edu.hotelmanagment.gui;

import edu.hotelmanagment.dao.*;
import edu.hotelmanagment.model.Guest;
import edu.hotelmanagment.model.Reservation;
import edu.hotelmanagment.model.Review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ReviewMenagementWindow
{

    TableView<Guest> guestTableView = new TableView<>();
    ObservableList<Guest> guests;

    public ReviewMenagementWindow()
    {
        Stage reviewWindow = new Stage();
        reviewWindow.setTitle("Review Management");
        reviewWindow.setResizable(false);

        Label headerLabel = new Label("Select a guest:");
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

        guestTableView.getColumns().addAll(
                guestIDColumn, firstNameColumn, lastNameColumn,
                passportNumberColumn, emailColumn, phoneNumberColumn
        );

        guestTableView.setItems(FXCollections.observableArrayList(GuestDAO.selectAll()));

        guestTableView.setFixedCellSize(25);
        guestTableView.setPrefHeight(guestTableView.getFixedCellSize() * 4 + 28);

        guestTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

        Label reservationLabel = new Label("Pick a reservation:");
        reservationLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        ComboBox<Reservation> reservationComboBox = new ComboBox<>();
        reservationComboBox.setPromptText("Select a reservation");
        reservationComboBox.setMaxWidth(150);

        guestTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ObservableList<Reservation> reservations = FXCollections.observableArrayList(
                        ReservationDAO.selectByGuestId(newSelection.getGuestID())
                );
                reservationComboBox.setItems(reservations);
            }
        });

        Separator separator2 = new Separator();

        Label reviewLabel = new Label("Review:");
        reviewLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        Label ratingLabel = new Label("Rate the service (1-5):");
        Spinner<Integer> ratingSpinner = new Spinner<>(1, 5, 1);
        ratingSpinner.setEditable(true);

        Label descriptionLabel = new Label("Description:");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter your review...");
        descriptionField.setWrapText(true);
        descriptionField.setPrefHeight(50);
        descriptionField.setPrefWidth(100);


        Button submitButton = new Button("Submit Review");
        submitButton.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;");
        submitButton.setOnAction(e -> {
            Guest selectedGuest = guestTableView.getSelectionModel().getSelectedItem();
            Reservation selectedReservation = reservationComboBox.getSelectionModel().getSelectedItem();

            if (selectedGuest == null || selectedReservation == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a guest and a reservation.");
                alert.showAndWait();
                return;
            }

            int rating = ratingSpinner.getValue();
            String description = descriptionField.getText();

            if (description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a review description.");
                alert.showAndWait();
            }

            Review newReview = new Review(null,guestTableView.getSelectionModel().getSelectedItem().getGuestID(),
                    rating,description,reservationComboBox.getSelectionModel().getSelectedItem().getReservationID());

            try {
                ReviewDAO.insert(newReview);
                reviewWindow.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "A review already exists for this guest and reservation.");
                alert.showAndWait();
            }
        });

        VBox mainLayout = new VBox(10, headerLabel, guestTableView, separator,
                reservationLabel, reservationComboBox, separator2,
                reviewLabel, ratingLabel, ratingSpinner,
                descriptionLabel, descriptionField, submitButton);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 600, 500);
        reviewWindow.setScene(scene);
        reviewWindow.show();

    }
}
