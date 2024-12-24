module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}