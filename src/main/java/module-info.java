module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires vecmath;
    requires java.desktop;
    requires java.logging;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}