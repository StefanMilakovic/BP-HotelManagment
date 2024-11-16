package edu.hotelmanagment;

import edu.hotelmanagment.gui.EmployeeManagementWindow;
import edu.hotelmanagment.gui.ItemReservationWindow;
import edu.hotelmanagment.gui.ReservationManagementWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static java.lang.Thread.sleep;

public class HelloApplication extends Application
{

    public void start(Stage primaryStage) {

        primaryStage.getIcons().add(new Image("hotel.png"));
        primaryStage.setTitle("Hotel Management");
        primaryStage.setResizable(false);
        Label titleLabel = new Label("Welcome, Manager!");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px;"); // Stilizuj natpis

        Button btnReservations = new Button("Reservations");
        Button btnGuests = new Button("Room Management");
        Button btnEmployees = new Button("Employees");
        Button btnItems = new Button("Add Item to Reservation");
        Button btnInvoices = new Button("Invoices");

        btnReservations.setMinWidth(200);
        btnGuests.setMinWidth(200);
        btnEmployees.setMinWidth(200);
        btnItems.setMinWidth(200);
        btnInvoices.setMinWidth(200);

        btnReservations.setOnAction(e -> handleReservations());
        btnGuests.setOnAction(e -> handleRooms());
        btnEmployees.setOnAction(e -> handleEmployees());
        btnItems.setOnAction(e->handleItems());
        btnInvoices.setOnAction(e -> handleInvoices());

        VBox vbox = new VBox(15); // Razmak između elemenata je 15px
        vbox.setStyle("-fx-alignment: center;"); // Centriraj sve unutar VBox-a
        vbox.getChildren().addAll(titleLabel, btnReservations, btnGuests, btnEmployees,btnItems, btnInvoices);


        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Hotel Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metode za dugmadi
    private void handleReservations() {
        System.out.println("Opening Reservations window...");
        // Otvori novi prozor za upravljanje rezervacijama
        new ReservationManagementWindow();
    }

    private void handleRooms() {
        new edu.hotelmanagment.gui.RoomManagementWindow();
    }

    private void handleEmployees() {
        new EmployeeManagementWindow();
    }

    private void handleItems()
    {
        System.out.println("Opening Items window...");
        new ItemReservationWindow();
    }

    private void handleInvoices() {
        System.out.println("Opening Invoices window...");
        // Otvori novi prozor za upravljanje fakturama
    }



    public static void main(String[] args) {
        launch(args);
    }

}
