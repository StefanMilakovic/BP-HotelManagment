package edu.hotelmanagment.gui;

import edu.hotelmanagment.gui.EmployeeManagementWindow;
import edu.hotelmanagment.gui.ReservationManagementWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuWindow
{
    public MainMenuWindow()
    {
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("hotel.png"));
        primaryStage.setTitle("Hotel Management");
        primaryStage.setResizable(false);
        Label titleLabel = new Label("Welcome, Manager!");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;");

        Button btnReservations = new Button("Reservations");
        Button btnGuests = new Button("Room Management");
        Button btnEmployees = new Button("Employees");
        Button btnItems = new Button("Add Item to Reservation");
        Button btnEvents=new Button("Event Management");
        Button btnInvoices = new Button("Invoice Management");
        Button btnReview=new Button("Reviews");

        btnReservations.setMinWidth(200);
        btnGuests.setMinWidth(200);
        btnEmployees.setMinWidth(200);
        btnItems.setMinWidth(200);
        btnEvents.setMinWidth(200);
        btnInvoices.setMinWidth(200);
        btnReview.setMinWidth(200);

        btnReservations.setOnAction(e -> handleReservations());
        btnGuests.setOnAction(e -> handleRooms());
        btnEmployees.setOnAction(e -> handleEmployees());
        btnItems.setOnAction(e->handleItems());
        btnEvents.setOnAction(e->handleEvents());
        btnInvoices.setOnAction(e -> handleInvoices());
        btnReview.setOnAction(e->handleReview());

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-alignment: center;");
        vbox.getChildren().addAll(titleLabel, btnReservations, btnGuests, btnEmployees,btnItems,btnEvents, btnInvoices,btnReview);


        Scene scene = new Scene(vbox, 400, 420);
        primaryStage.setTitle("Hotel Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleReservations() {
        new ReservationManagementWindow();
    }

    private void handleRooms() {
        new RoomManagementWindow();
    }

    private void handleEmployees() {
        new EmployeeManagementWindow();
    }

    private void handleItems() {
        new ItemReservationWindow();
    }

    private void handleEvents()
    {
        new EventManagamentWindow();
    }

    private void handleInvoices() {
        new InvoiceManagementWindow();
    }

    private void handleReview()
    {
        new ReviewMenagementWindow();
    }
}
