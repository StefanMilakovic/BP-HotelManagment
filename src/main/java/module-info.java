module edu.hotelmanagment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens edu.hotelmanagment to javafx.fxml;
    exports edu.hotelmanagment;

    opens edu.hotelmanagment.model to javafx.base;
    exports edu.hotelmanagment.model;
}