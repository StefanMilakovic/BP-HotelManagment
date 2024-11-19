package edu.hotelmanagment.gui;

import edu.hotelmanagment.dao.ReservationViewDAO;
import edu.hotelmanagment.model.Item;
import edu.hotelmanagment.model.Reservation;
import edu.hotelmanagment.model.ReservationHasItem;
import edu.hotelmanagment.dao.ItemDAO;
import edu.hotelmanagment.dao.ReservationHasItemDAO;
import edu.hotelmanagment.model.ReservationView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class ItemReservationWindow
{
    Integer itemID;
    Integer reservationID;
    Integer quantity;
    public ItemReservationWindow()
    {
        addItemsToReservationWindow();
    }
    private void addItemsToReservationWindow()
    {
        Stage window = new Stage();
        window.setTitle("Add Items to Reservation");
        window.setResizable(false);

        TableView<Item> itemTableView = new TableView<>();

        TableColumn<Item, Integer> itemIDColumn = new TableColumn<>("Item ID");
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, String> itemTypeColumn = new TableColumn<>("Item Type");
        itemTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));

        itemTableView.getColumns().addAll(
                itemIDColumn, nameColumn, priceColumn, itemTypeColumn
        );

        ObservableList<Item> items = FXCollections.observableArrayList(ItemDAO.selectAll());
        itemTableView.setItems(items);

        itemTableView.setOnMouseClicked(event -> {
            Item selectedItem = itemTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Selected Item ID: " + selectedItem.getItemID());
            }
        });

        itemTableView.setRowFactory(tv -> {
            TableRow<Item> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #5fa62d;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        itemTableView.setFixedCellSize(25);
        itemTableView.setPrefHeight(itemTableView.getFixedCellSize() * 5+28);

        Separator separator = new Separator();

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, 1);
        quantitySpinner.setEditable(true);

        TableView<ReservationView> reservationTableView = new TableView<>();
        ObservableList<ReservationView> reservations;


        TableColumn<ReservationView, Integer> reservationIDColumn = new TableColumn<>("Reservation ID");
        reservationIDColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));

        TableColumn<ReservationView, java.sql.Date> checkInDateColumn = new TableColumn<>("Check-In Date");
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        TableColumn<ReservationView, java.sql.Date> checkOutDateColumn = new TableColumn<>("Check-Out Date");
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        TableColumn<ReservationView, Integer> numberOfGuestsColumn = new TableColumn<>("Number of Guests");
        numberOfGuestsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));

        TableColumn<ReservationView, String> guestNameColumn = new TableColumn<>("Guest Name");
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        TableColumn<ReservationView, Integer> roomIDColumn = new TableColumn<>("Room ID");
        roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<ReservationView, String> reservationTypeColumn = new TableColumn<>("Reservation Type");
        reservationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("reservationType"));

        TableColumn<ReservationView, String> employeeNameColumn = new TableColumn<>("Employee Name");
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        reservationTableView.getColumns().addAll(
                reservationIDColumn, checkInDateColumn, checkOutDateColumn,
                numberOfGuestsColumn, guestNameColumn, roomIDColumn,
                reservationTypeColumn, employeeNameColumn
        );

        reservations = FXCollections.observableArrayList(ReservationViewDAO.selectAll());
        reservationTableView.setItems(reservations);

        reservationTableView.setOnMouseClicked(event -> {
            ReservationView selectedReservation = reservationTableView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                reservationID=selectedReservation.getReservationID();}
        });

        reservationTableView.setRowFactory(tv -> {
            TableRow<ReservationView> row = new TableRow<>();
            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    row.setStyle("-fx-background-color: #5fa62d;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });

        reservationTableView.setFixedCellSize(25);
        reservationTableView.setPrefHeight(reservationTableView.getFixedCellSize() * 5+28);

        Button addItemButton = new Button("Add Item to Reservation");
        addItemButton.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;");
        addItemButton.setOnAction(e -> {
            Item selectedItem = itemTableView.getSelectionModel().getSelectedItem();
            quantity=quantitySpinner.getValue();
            if (selectedItem != null) {
                itemID=selectedItem.getItemID();
                System.out.println("Adding " + quantity + " of Item: " + selectedItem.getName());
                ReservationHasItem ri=new ReservationHasItem(null, Date.valueOf(LocalDate.now()),quantity,itemID,reservationID);
                ReservationHasItemDAO.insert(ri);
                window.close();
            } else {
                System.out.println("No item selected or invalid quantity!");
            }
        });

        HBox buttonContainer = new HBox(addItemButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label selectItemLabel = new Label("Select an Item");
        selectItemLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label selectReservationLabel = new Label("Select Reservation");
        selectReservationLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        layout.getChildren().addAll(selectItemLabel, itemTableView, quantitySpinner, separator, selectReservationLabel, reservationTableView, buttonContainer);

        Scene scene = new Scene(layout, 780, 500);
        window.setScene(scene);
        window.show();
    }
}
