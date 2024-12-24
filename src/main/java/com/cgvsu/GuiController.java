package com.cgvsu;

import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjReaderException;
import com.cgvsu.objwriter.ObjWriterClass;
import com.cgvsu.render_engine.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.cgvsu.render_engine.Camera;


@SuppressWarnings({"rawtypes", "DuplicateExpressions"})
public class GuiController {

    final private float TRANSLATION = 0.5F;
    public Button buttonSaveModel;
    public Button addModel;
    public ListView fileNameCamera;
    public Button buttonApplyCamera;
    public Button buttonAddCamera;
    public TextField positionX;
    public TextField positionY;
    public TextField positionZ;
    public TextField targetX;
    public TextField targetY;
    public TextField targetZ;
    public Button buttonApplyModel;
    public TextField fieldWriteCoordinate;
    public ToggleButton buttonRemoveVertex;
    public ToggleButton buttonRemoveFace;
    public ToggleButton buttonTriangulation;
    public TextField rotateX;
    public TextField rotateY;
    public TextField rotateZ;
    public TextField scaleX;
    public TextField scaleY;
    public TextField scaleZ;
    public TextField translateX;
    public TextField translateY;
    public TextField translateZ;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Label labelPercent;

    @FXML
    Slider sliderMouseSensitivity;

    @FXML
    private Canvas canvas;

    @FXML
    private CheckBox checkBoxTransform;

    private final HashMap<String, Model> meshes = new HashMap<>();
    private final HashMap<String, Model> transformMeshes = new HashMap<>();

    private ContextMenu contextMenu;

    @FXML
    private ListView<String> fileNameModel;
    private final ObservableList<String> tempFileName = FXCollections.observableArrayList();

    private final ObjWriterClass objWriter = new ObjWriterClass();
    private final Camera camera = new Camera(new Vector3C(0, 0, 100), new Vector3C(0, 0, 0), 1.0F, 1, 0.01F, 100);

    final private TransferManagerCamera transfer = new TransferManagerCamera(camera);
    private final TransferManagerModel transferModel = new TransferManagerModel();

    private final boolean isCtrlPressed = false;


    @FXML
    private void initialize() {
        contextMenu = new ContextMenu();
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            //TODO Убрали if()
            canvas.getGraphicsContext2D().setStroke(Color.BLUE);
            RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformMeshes, (int) width, (int) height);

        });

        // начальное значение чувствительности камеры
        double initialSensitivity = 10 / 10000.0;
        transfer.setSensitivity(initialSensitivity);

        sliderMouseSensitivity.valueProperty().addListener(
                (observable, oldValue, newValue) -> MouseSensitivity(newValue.doubleValue()));

        fileNameModel.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.getItems().clear();
                removeModelFromTheScene(event);
            }
        });


        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void showAlertWindow(AnchorPane anchorPane, Alert.AlertType alertType, String message, ButtonType buttonType) {
        Stage mainStage = (Stage) anchorPane.getScene().getWindow();
        Alert alert = new Alert(alertType, message, buttonType);
        alert.initOwner(mainStage);
        alert.showAndWait();
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

        String fileName = file.getName();
        Path filePath = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(filePath);
            Model model = ObjReader.read(fileContent);
            meshes.put(fileName, model);
            transformMeshes.put(fileName, GraphicConveyor.rotateScaleTranslate(model, model.getModelCenter()));
            tempFileName.add(fileName);
            fileNameModel.setItems(tempFileName);

            // todo: обработка ошибок

        } catch (IOException exception) {
            showAlertWindow(anchorPane, Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
        } catch (ObjReaderException exception) {
            showAlertWindow(anchorPane, Alert.AlertType.WARNING, exception.getMessage(), ButtonType.CLOSE);
        }
    }

    @FXML
    void save(MouseEvent event) {
        if (!meshes.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Model");


            String selectedModelName = fileNameModel.getSelectionModel().getSelectedItem();
            if (selectedModelName == null || selectedModelName.isEmpty()) {
                showAlertWindow(anchorPane, Alert.AlertType.WARNING, "Выберите модель для сохранения!", ButtonType.CLOSE);
                return;
            }

            // Устанавливаем имя файла по умолчанию
            fileChooser.setInitialFileName(selectedModelName);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("OBJ files (*.obj)", "*.obj"));
            File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());

            if (file == null) {
                return;
            }

            // расширение(формат) файла, если его нет
            String filePath = file.getAbsolutePath();
            if (!filePath.endsWith(".obj")) {
                filePath += ".obj";
            }

            if (checkBoxTransform.isSelected()) {
                objWriter.write(transformMeshes.get(selectedModelName), filePath);
            } else {
                objWriter.write(meshes.get(selectedModelName), filePath);
            }

            showAlertWindow(anchorPane, Alert.AlertType.INFORMATION, "Модель успешно сохранена!", ButtonType.CLOSE);
        } else {
            showAlertWindow(anchorPane, Alert.AlertType.WARNING, "Добавьте модель для сохранения.", ButtonType.CLOSE);
        }
    }

    // Очистка сцены
    @FXML
    public void clearScene() {
        meshes.clear();
        transformMeshes.clear();
        tempFileName.clear();
        fileNameModel.setItems(tempFileName);
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
        transfer.mouseCameraZoom(scrollEvent.getDeltaY());
    }

    @FXML
    public void onMousePressed(MouseEvent mouseEvent) {
        transfer.fixPoint(mouseEvent.getX(), mouseEvent.getY());
    }

    @FXML

    public void onMouseDragged(MouseEvent event) {
        transfer.onMouseDragged(event.getX(), event.getY());
    }


    // Удаление моделей в Active Models по клику на модель
    private void removeModelFromTheScene(MouseEvent event) {
        String selectedItem = fileNameModel.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", selectedItem));

        deleteItem.setOnAction(deleteEvent -> {
            transformMeshes.remove(selectedItem);
            meshes.remove(selectedItem);
            tempFileName.remove(selectedItem);
            fileNameModel.setItems(tempFileName);
        });

        contextMenu.getItems().setAll(deleteItem);
        contextMenu.show(fileNameModel, event.getScreenX(), event.getScreenY() + 10.5);
    }

    private void MouseSensitivity(double newValue) {
        double sensitivity = newValue / 10000.0;
        transfer.setSensitivity(sensitivity);
        labelPercent.setText(String.format("%.0f%%", newValue));
    }
    @FXML
    public void buttonApplyModel() {
        //TODO обработка ошибок null-model
        transferModel.setModel(transformMeshes.get(fileNameModel.getSelectionModel().getSelectedItem()));
        Model model = transferModel.applyModel(rotateX.getText(), rotateY.getText(), rotateZ.getText(), scaleX.getText(), scaleY.getText(), scaleZ.getText(), translateX.getText(), translateY.getText(), translateZ.getText());
        transformMeshes.put(fileNameModel.getSelectionModel().getSelectedItem(), model);
    }

}