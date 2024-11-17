package edu.hotelmanagment.gui;

import edu.hotelmanagment.model.Employee;
import edu.hotelmanagment.dao.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class EmployeeManagementWindow
{
    ObservableList<Employee> employees;
    TableView<Employee> employeeTableView;
    public EmployeeManagementWindow()
    {
        Stage employeeStage = new Stage();
        employeeStage.setTitle("Employee Management");
        employeeStage.setResizable(false);

        Button addEmployee = new Button("Add Employee");
        Button deleteEmployee = new Button("Delete Employee");
        Button editEmployee = new Button("Edit Employee");

        addEmployee.setOnAction(e -> addNewEmployee());
        editEmployee.setOnAction(e -> editEmployee());
        deleteEmployee.setOnAction(e -> deleteEmployee());

// Set styles for the buttons (using the same colors as the reservation management example)
        addEmployee.setStyle("-fx-background-color: #5fa62d; -fx-text-fill: white;"); // Green button with white text
        deleteEmployee.setStyle("-fx-background-color: #de3a3a; -fx-text-fill: white;"); // Red button with white text
        editEmployee.setStyle("-fx-background-color: #ded93a; -fx-text-fill: black;");
        employeeTableView = new TableView<>();

// Define each column for the Employee TableView
        TableColumn<Employee, Integer> employeeIDColumn = new TableColumn<>("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));

        TableColumn<Employee, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("FirstName"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("LastName"));

        TableColumn<Employee, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));

        TableColumn<Employee, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Employee, java.sql.Date> hireDateColumn = new TableColumn<>("Hire Date");
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));


        TableColumn<Employee, Integer> isActiveColumn = new TableColumn<>("Active Status");
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));


        TableColumn<Employee, Integer> positionIDColumn = new TableColumn<>("Position ID");
        positionIDColumn.setCellValueFactory(new PropertyValueFactory<>("PositionID"));

        employeeTableView.getColumns().addAll(
                employeeIDColumn, firstNameColumn, lastNameColumn,
                emailColumn, phoneNumberColumn, hireDateColumn,isActiveColumn,positionIDColumn
        );

        employees = FXCollections.observableArrayList(EmployeeDAO.selectAll());
        employeeTableView.setItems(employees);


        employeeTableView.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();

            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null && newItem.getEmployeeID() != null)
                {
                    if (newItem.isIsActive()) {
                        row.setStyle("-fx-background-color: lightgreen;");
                    } else {
                        row.setStyle("-fx-background-color: lightcoral;");
                    }
                } else
                {
                    row.setStyle("");
                }
            });

            row.setOnMouseClicked(event -> {
                Employee employee = row.getItem();
                if (employee != null) {
                    employee.setIsActive(!employee.isIsActive());

                    if (employee.isIsActive()) {
                        row.setStyle("-fx-background-color: lightgreen;");
                    } else {
                        row.setStyle("-fx-background-color: lightcoral;");
                    }
                    EmployeeDAO.updateEmployeeStatus(employee);
                }
                reloadData();
            });
            return row;
        });


        HBox addButtonBox = new HBox(10, addEmployee, editEmployee, deleteEmployee);
        addButtonBox.setPadding(new Insets(2, 0, 2, 0));
        addButtonBox.setStyle("-fx-alignment: center-left;");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(addButtonBox,employeeTableView);

        Scene scene = new Scene(layout, 710, 300);
        employeeStage.setScene(scene);
        employeeStage.show();
    }

    private void addNewEmployee()
    {
        Stage employeeStage = new Stage();
        employeeStage.setTitle("New Employee Form");
        employeeStage.setResizable(false);

        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtEmail = new TextField();
        TextField txtPhoneNumber = new TextField();
        DatePicker dpHireDate = new DatePicker();

        ObservableList<String> positionNames = FXCollections.observableArrayList("Receptionist", "Housekeeping", "Manager");
        ComboBox<String> cmbPosition = new ComboBox<>(positionNames);
        cmbPosition.setPromptText("Select Position");
        cmbPosition.setMaxWidth(175);

        Button btnSubmit = new Button("Add");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(txtFirstName, 1, 0);

        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(txtLastName, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(txtEmail, 1, 2);

        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(txtPhoneNumber, 1, 3);

        grid.add(new Label("Hire Date:"), 0, 4);
        grid.add(dpHireDate, 1, 4);

        grid.add(new Label("Position:"), 0, 5);
        grid.add(cmbPosition, 1, 5);

        grid.add(btnSubmit, 1, 6);

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String email = txtEmail.getText();
                String phoneNumber = txtPhoneNumber.getText();
                java.sql.Date hireDate=java.sql.Date.valueOf(dpHireDate.getValue());
                String position = cmbPosition.getValue();
                int choise=0;
                if("Receptionist".equals(position))
                    choise=1;
                else if("Housekeeping".equals(position))
                    choise=2;
                else if("Manager".equals(position))
                    choise=3;


                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || position == null || hireDate == null)
                {
                    System.out.println("Error, Please fill in all fields.");
                } else {
                    Employee newEmployee = new Employee(null,firstName, lastName, email, phoneNumber, hireDate,true,choise);
                    EmployeeDAO.insert(newEmployee);
                    reloadData();
                    employeeStage.close();
                    System.out.println("New Employee Added:");
                }

            }
        });
        Scene scene = new Scene(grid, 300, 250);
        employeeStage.setScene(scene);
        employeeStage.show();

    }
    private void editEmployee()
    {
        Stage employeeStage = new Stage();
        employeeStage.setTitle("Edit Employee");
        employeeStage.setResizable(false);

        Button btnConfirm = new Button("Confirm");
        TextField txtEmployeeID = new TextField();
        txtEmployeeID.setPromptText("Enter Employee ID");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(new Label("Employee ID:"), 0, 0);
        grid.add(txtEmployeeID, 1, 0);

        grid.add(btnConfirm, 1, 1);

        TextField txtFirstName = new TextField();
        TextField txtLastName = new TextField();
        TextField txtEmail = new TextField();
        TextField txtPhoneNumber = new TextField();
        DatePicker dpHireDate = new DatePicker();

        ObservableList<String> positionNames = FXCollections.observableArrayList("Receptionist", "Housekeeping", "Manager");
        ComboBox<String> cmbPosition = new ComboBox<>(positionNames);
        cmbPosition.setPromptText("Select Position");
        cmbPosition.setMaxWidth(175);

        Button btnUpdate = new Button("Update");
        grid.add(new Label("First Name:"), 0, 2);
        grid.add(txtFirstName, 1, 2);

        grid.add(new Label("Last Name:"), 0, 3);
        grid.add(txtLastName, 1, 3);

        grid.add(new Label("Email:"), 0, 4);
        grid.add(txtEmail, 1, 4);

        grid.add(new Label("Phone Number:"), 0, 5);
        grid.add(txtPhoneNumber, 1, 5);

        grid.add(new Label("Hire Date:"), 0, 6);
        grid.add(dpHireDate, 1, 6);

        grid.add(new Label("Position:"), 0, 7);
        grid.add(cmbPosition, 1, 7);

        grid.add(btnUpdate, 1, 8);

        btnConfirm.setOnAction(event -> {
            int employeeID;
            try {
                employeeID = Integer.parseInt(txtEmployeeID.getText());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Employee ID");
                return;
            }
            Employee employee = EmployeeDAO.selectById(employeeID);
            if (employee != null) {
                txtFirstName.setText(employee.getFirstName());
                txtLastName.setText(employee.getLastName());
                txtEmail.setText(employee.getEmail());
                txtPhoneNumber.setText(employee.getPhoneNumber());
                dpHireDate.setValue(employee.getHireDate().toLocalDate());

                if (employee.getPositionID() == 1) {
                    cmbPosition.setValue("Receptionist");
                } else if (employee.getPositionID() == 2) {
                    cmbPosition.setValue("Housekeeping");
                } else if (employee.getPositionID() == 3) {
                    cmbPosition.setValue("Manager");
                }
            } else {
                System.out.println("Employee not found");
            }
        });

        btnUpdate.setOnAction(event -> {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String phoneNumber = txtPhoneNumber.getText();
            java.sql.Date hireDate = java.sql.Date.valueOf(dpHireDate.getValue());
            String position = cmbPosition.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || position == null || hireDate == null) {
                System.out.println("Error, Please fill in all fields.");
            } else {
                int positionID = 0;
                if ("Receptionist".equals(position)) {
                    positionID = 1;
                } else if ("Housekeeping".equals(position)) {
                    positionID = 2;
                } else if ("Manager".equals(position)) {
                    positionID = 3;
                }
                int employeeID = Integer.parseInt(txtEmployeeID.getText());
                Employee updatedEmployee = new Employee(employeeID, firstName, lastName, email, phoneNumber, hireDate, true, positionID);

                EmployeeDAO.update(updatedEmployee);
                reloadData();
                employeeStage.close();
                System.out.println("Employee Updated:");
            }
        });

        Scene scene = new Scene(grid, 300, 320);
        employeeStage.setScene(scene);
        employeeStage.show();

    }
    private void deleteEmployee()
    {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete Employee");

        double textFieldWidth = 100;

        Label employeeIdLabel = new Label("Employee ID:");
        TextField employeeIdTextField = new TextField();
        employeeIdTextField.setPrefWidth(textFieldWidth);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            String roomIdText = employeeIdTextField.getText();
            if (roomIdText.isEmpty()) {
                System.out.println("Please enter a Employee ID.");
            } else {
                int employeeId = Integer.parseInt(roomIdText);
                EmployeeDAO.delete(employeeId);
                reloadData();
                deleteStage.close();
            }});

        VBox buttonBox = new VBox(10, deleteButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(20));

        formGrid.add(employeeIdLabel, 0, 0);
        formGrid.add(employeeIdTextField, 1, 0);
        formGrid.add(buttonBox, 0, 1, 2, 1);

        Scene dialogScene = new Scene(formGrid, 250, 110);
        deleteStage.setScene(dialogScene);

        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.showAndWait();
    }

    private void reloadData()
    {
        employees.clear();
        employees = FXCollections.observableArrayList(EmployeeDAO.selectAll());
        employeeTableView.setItems(employees);
    }

}
