module com.cgvsu.rasterizationfxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires io.github.shimeoki.jfx.rasterization;


    opens com.cgvsu.rasterizationfxapp to javafx.fxml;
    exports com.cgvsu.rasterizationfxapp;
}