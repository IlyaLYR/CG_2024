package com.cgvsu.Controllers;

import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.Camera.Camera;

import java.util.HashMap;

public class CameraManager {
    private final HashMap<String, Camera> cameras = new HashMap<>();
    private double mouseX;
    private double mouseY;
    private Camera activeCamera;
    private double sensitivity; // чувствительность

    // Конструктор для инициализации камеры
    public CameraManager() {
        this.activeCamera = new Camera(
                new Vector3C(0, 0, 100),
                new Vector3C(0, 0, 0), 1.0F, 1, 0.01F, 100);
        this.mouseX = 0;
        this.mouseY = 0;
    }

    public void mouseCameraZoom(double deltaY) {
        double delta = deltaY * sensitivity * 10;
        double minDistance = 2.0;

        Vector3C direction = activeCamera.getTarget().subtracted(activeCamera.getPosition()).normalize();
        Vector3C newPosition = activeCamera.getPosition().added(direction.multiplied(delta));

        if (activeCamera.getTarget().subtracted(newPosition).getLength() > minDistance) {
            activeCamera.setPosition(newPosition);
        }
    }

    // Метод для фиксации положения мыши (используется при старте перетаскивания)
    public void fixPoint(double detX, double detY) {
        mouseX = detX;
        mouseY = detY;
    }

    public void onMouseDragged(double x, double y) {
        double deltaX = (x - mouseX) * sensitivity;
        double deltaY = (y - mouseY) * sensitivity;

        //Получаем позицию камеры и целевую точку
        Vector3C position = activeCamera.getPosition();
        Vector3C target = activeCamera.getTarget();

        //Перенос камеры в локальные координаты относительно target
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformationToTarget = builder.translateByVector(target.multiplied(-1)).build();
        position = transformationToTarget.applyTransformationToVector(position);

        //Вращаем камеру вокруг
        position = STransformation.rotateTwoAngles(position, -deltaX, -deltaY);

        //Возвращаем камеру обратно
        ATransformation transformationBack = new ATransformation.ATBuilder().translateByVector(target).build();
        position = transformationBack.applyTransformationToVector(position);

        // Шаг 5. Устанавливаем новое положение камеры
        activeCamera.setPosition(position);

        // Сохраняем новое положение мыши
        mouseX = x;
        mouseY = y;
    }


    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void setActiveCamera(String cameraName) {
        Camera camera = cameras.get(cameraName);
        if (camera != null) {
            activeCamera = camera;
        }
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public HashMap<String, Camera> getCameras() {
        return cameras;
    }

    public void addCamera(String name, Camera camera) {
        cameras.put(name, camera);
    }

}
