package com.cgvsu.JavaFXUtils;

import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Alert {
    public static void showAlertWindow(AnchorPane anchorPane, javafx.scene.control.Alert.AlertType alertType, String message, ButtonType buttonType) {
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType, message, buttonType);
        alert.initOwner(mainStage);
        alert.showAndWait();
    }
}
