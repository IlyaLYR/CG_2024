package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import com.cgvsu.render_engine.TransferManagerCamera;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.math.typesVectors.Vector3C;


public class GuiController {

    final private float TRANSLATION = 0.5F;


    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    Label info;

    private Model mesh = null;

    private final Camera camera = new Camera(
            new Vector3C(0, 0, 100),
            new Vector3C(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    final private TransferManagerCamera transfer = new TransferManagerCamera(camera);


    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                canvas.getGraphicsContext2D().setStroke(Color.BLUE);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
            info.setText("Camera " + camera.getPosition().toString());
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void setCamera() {
        camera.setPosition(new Vector3C(0, 0, 100));
    }

    @FXML
    public void clearScene() {
        mesh = null;
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3C(0, -TRANSLATION, 0));
    }


    //Управление камерой мышкой
    @FXML
    public void mouseCameraZoom(ScrollEvent scrollEvent) {
        transfer.mouseCameraZoom(scrollEvent.getDeltaY(), 0.02);
    }

    @FXML
    public void onMousePressed(MouseEvent mouseEvent) {
        transfer.fixPoint(mouseEvent.getX(), mouseEvent.getY());
    }

    @FXML

    public void onMouseDragged(MouseEvent event) {
        transfer.onMouseDragged(event.getX(), event.getY(), 0.01);
    }
}