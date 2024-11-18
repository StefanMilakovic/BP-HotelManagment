package edu.hotelmanagment.gui;

import edu.hotelmanagment.dao.GuestDAO;
import edu.hotelmanagment.dao.InvoiceDAO;
import edu.hotelmanagment.dao.ReservationDAO;
import edu.hotelmanagment.model.Guest;
import edu.hotelmanagment.model.Invoice;
import edu.hotelmanagment.model.Reservation;
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

public class InvoiceManagementWindow
{
    TableView<Guest> guestTableView = new TableView<>();
    ObservableList<Guest> guests;


    private TableView<Invoice> invoiceTableView = new TableView<>();
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

    public InvoiceManagementWindow()
    {
        Stage invoiceWindow = new Stage();  // Create a new stage
        invoiceWindow.setTitle("Invoice Management");
        invoiceWindow.setResizable(false);

        // Button for creating a new invoice
        Button generateInvoiceButton = new Button("Generate New Invoice");
        //generateInvoiceButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5fa62d; -fx-text-fill: white;");

        generateInvoiceButton.setOnAction(e -> generateNewInvoice());

        // Table for displaying invoices
        //Label invoiceLabel = new Label("Invoices:");
        //invoiceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Columns for the Invoice Table
        TableColumn<Invoice, Integer> invoiceIDColumn = new TableColumn<>("Invoice ID");
        invoiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("InvoiceID"));

        TableColumn<Invoice, Double> totalAmountColumn = new TableColumn<>("Total Amount");
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));

        TableColumn<Invoice, java.sql.Date> issuedDateColumn = new TableColumn<>("Issued Date");
        issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("IssuedDate"));

        TableColumn<Invoice, Integer> guestIDColumn = new TableColumn<>("Guest ID");
        guestIDColumn.setCellValueFactory(new PropertyValueFactory<>("GuestID"));

        TableColumn<Invoice, Integer> reservationIDColumn = new TableColumn<>("Reservation ID");
        reservationIDColumn.setCellValueFactory(new PropertyValueFactory<>("ReservationID"));

        TableColumn<Invoice, Integer> paymentTypeColumn = new TableColumn<>("Payment Type");
        paymentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("PaymentTypeID"));

        // Adding columns to the table
        invoiceTableView.getColumns().setAll(invoiceIDColumn, totalAmountColumn, issuedDateColumn,
                guestIDColumn, reservationIDColumn, paymentTypeColumn);

        // Load the invoices into the table
        loadInvoices();

        // Setup the table view and other GUI elements
        //invoiceTableView.setFixedCellSize(25);
        //invoiceTableView.setPrefHeight(invoiceTableView.getFixedCellSize() * 4 + 28);
        //invoiceTableView.setMaxWidth(600);

        // Create layout for the window
        VBox mainLayout = new VBox(10,
                generateInvoiceButton, invoiceTableView
        );
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        // Create scene and set it to the stage
        Scene scene = new Scene(mainLayout, 530, 300);
        invoiceWindow.setScene(scene);
        invoiceWindow.show();
    }

    private void generateNewInvoice()
    {
        Stage invoiceWindow = new Stage();  // Create a new stage
        invoiceWindow.setTitle("Invoice Management");
        invoiceWindow.setResizable(false);

        Label headerLabel = new Label("Select guest:");
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

        guests = FXCollections.observableArrayList(GuestDAO.selectAll());
        guestTableView.setItems(guests);

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
        Label reservationLabel = new Label("Select a reservation:");
        reservationLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<Reservation> reservationComboBox = new ComboBox<>();
        reservationComboBox.setPromptText("Select a reservation");
        reservationComboBox.setMaxWidth(150);

        guestTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ObservableList<Reservation> reservations = FXCollections.observableArrayList(
                        ReservationDAO.selectByGuestId(newSelection.getGuestID())
                );
                reservationComboBox.setItems(reservations);
            } else {
                reservationComboBox.getItems().clear();
            }
        });

        Label paymentTypeLabel = new Label("Select payment type:");
        paymentTypeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ComboBox<String> paymentTypeComboBox = new ComboBox<>();
        paymentTypeComboBox.setItems(FXCollections.observableArrayList("Cash", "Credit Card", "Debit Card"));
        paymentTypeComboBox.setPromptText("Select payment type");
        paymentTypeComboBox.setMaxWidth(150);

        Button createButton = new Button("Create");
        createButton.setStyle("-fx-font-size: 14px; -fx-background-color: #5fa62d; -fx-text-fill: white;");

        HBox buttonContainer = new HBox(createButton);
        buttonContainer.setAlignment(Pos.CENTER);

        createButton.setOnAction(e -> {
            Guest selectedGuest = guestTableView.getSelectionModel().getSelectedItem();
            Reservation selectedReservation = reservationComboBox.getSelectionModel().getSelectedItem();
            String selectedPaymentType = paymentTypeComboBox.getSelectionModel().getSelectedItem();
            int paymentType=0;

            if("Cash".equals(selectedPaymentType)) {
                paymentType=1;
            }else if("Credit Card".equals(selectedPaymentType)) {
                paymentType=2;
            }
            else if("Debit Card".equals(selectedPaymentType)) {
                paymentType=3;
            }
            InvoiceDAO.generateNewInvoice(guestTableView.getSelectionModel().getSelectedItem().getGuestID(),
                    reservationComboBox.getSelectionModel().getSelectedItem().getReservationID(),paymentType);

        });

        Separator separator1 = new Separator();
        Separator separator2 = new Separator();
        Separator separator3 = new Separator();

        VBox mainLayout = new VBox(10,
                headerLabel, guestTableView, separator1,
                reservationLabel, reservationComboBox, separator2,
                paymentTypeLabel, paymentTypeComboBox, separator3,
                buttonContainer
        );
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(mainLayout, 600, 400);
        invoiceWindow.setScene(scene);
        invoiceWindow.show();
    }

    private void loadInvoices() {
        invoices.clear();  // Clear any existing data

        // Fetch all invoices from the database (using InvoiceDAO.selectAll() as an example)
        invoices.addAll(InvoiceDAO.selectAll()); // Assuming InvoiceDAO.selectAll() returns a List of invoices
        invoiceTableView.setItems(invoices);  // Set the data in the TableView
    }
}
