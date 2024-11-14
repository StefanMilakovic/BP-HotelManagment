package edu.hotelmanagment.gui;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.wrapper.WrapperEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EmployeeManagementWindow
{
    public EmployeeManagementWindow()
    {
        Stage employeeStage = new Stage();
        employeeStage.setTitle("New Employee Form");

        // Initialize UI components
        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtEmail = new TextField();
        TextField txtContactNumber = new TextField();
        DatePicker dpHireDate = new DatePicker();

        // Populate the ComboBox with sample position names (Replace this with database data)
        ObservableList<String> positionNames = FXCollections.observableArrayList("Receptionist", "Housekeeping", "Manager");
        ComboBox<String> cmbPosition = new ComboBox<>(positionNames);
        cmbPosition.setPromptText("Select Position");
        cmbPosition.setMaxWidth(175);

        // Submit Button
        Button btnSubmit = new Button("Add");

        // Grid Layout Setup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Add components to grid
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(txtFirstName, 1, 0);

        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(txtLastName, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(txtEmail, 1, 2);

        grid.add(new Label("Contact Number:"), 0, 3);
        grid.add(txtContactNumber, 1, 3);

        grid.add(new Label("Hire Date:"), 0, 4);
        grid.add(dpHireDate, 1, 4);

        grid.add(new Label("Position:"), 0, 5);
        grid.add(cmbPosition, 1, 5);

        grid.add(btnSubmit, 1, 6);

        // Action for Add button (save the data and create Employee object)
        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Get data from the form
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String email = txtEmail.getText();
                String contactNumber = txtContactNumber.getText();
                java.sql.Date hireDate=java.sql.Date.valueOf(dpHireDate.getValue());
                String position = cmbPosition.getValue();
                Employee newEmployee = new Employee(null,firstName, lastName, email, contactNumber, hireDate, 1);
                WrapperEmployee.insert(newEmployee);


                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || contactNumber.isEmpty() || position == null || hireDate == null) {
                    //System.out.println("Error", "Please fill in all fields.");
                } else {
                    // Create a new Employee object
                    //Employee newEmployee = new Employee(firstName, lastName, email, contactNumber, localDate, position);

                    // Print employee details (this could be replaced with actual logic to save to a database)

                    System.out.println("New Employee Added:");
                    //System.out.println(newEmployee);

                    // Show success message
                    //showAlert("Success", "Employee added successfully.");
                }

            }
        });
        Scene scene = new Scene(grid, 300, 250);
        employeeStage.setScene(scene);
        employeeStage.show();

    }
}
