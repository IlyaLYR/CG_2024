package com.cgvsu;

import com.cgvsu.render_engine.RenderEngine;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.math.typesVectors.Vector3C;


public class GuiController {

    final private float TRANSLATION = 0.5F;

    //Поля для управления мышкой

    private double startX;
    private double startY;


    private Timeline timeline;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private HashMap<String, Model> meshes = new HashMap<>();
    private ContextMenu contextMenu;

    @FXML
    private ListView<String> fileName;
    private final ObservableList<String> tempFileName = FXCollections.observableArrayList();
    private Camera camera = new Camera(
            new Vector3C(0, 0, 100),
            new Vector3C(0, 0, 0),
            1.0F, 1, 0.01F, 100);


    @FXML
    private void initialize() {
        contextMenu = new ContextMenu();
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));


            if (meshes != null) {
                canvas.getGraphicsContext2D().setStroke(Color.BLUE);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, meshes, (int) width, (int) height);
            }
        });

        fileName.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.getItems().clear();
                removeModelFromTheScene(event);
            }
        });


        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        this.tempFileName.add(file.getName());
        this.fileName.setItems(tempFileName);
        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            meshes.put(file.getName(), ObjReader.read(fileContent));
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    // Очистка сцены
    @FXML
    public void clearScene() {
        meshes.clear();
        tempFileName.clear();
        fileName.setItems(tempFileName);
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
        camera.mouseCameraZoom(scrollEvent.getDeltaY());
    }

    @FXML
    public void fixStartCoordinates(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    public void mouseCameraMove(MouseEvent mouseEvent) {
//        camera.mouseCameraMove(startX - mouseEvent.getX(), startY - mouseEvent.getY());
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
    }

    // Удаление моделей в Active Models по клику на модель
    private void removeModelFromTheScene(MouseEvent event) {
        String selectedItem = fileName.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", selectedItem));

        deleteItem.setOnAction(deleteEvent -> {
            meshes.remove(selectedItem);
            tempFileName.remove(selectedItem);
            fileName.setItems(tempFileName);
        });

        contextMenu.getItems().setAll(deleteItem);
        contextMenu.show(fileName, event.getScreenX(), event.getScreenY() + 10.5);
    }
}