package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;

public class TransferManagerCamera {
    private double mouseX;
    private double mouseY;
    private final Camera camera;
    private double sensitivity; // чувствительность

    // Конструктор для инициализации камеры
    public TransferManagerCamera(Camera camera) {
        this.camera = camera;
        this.mouseX = 0;
        this.mouseY = 0;
    }

    public void mouseCameraZoom(double deltaY) {
        double delta = deltaY * sensitivity * 10;
        double minDistance = 2.0;

        Vector3C direction = camera.getTarget().subtracted(camera.getPosition()).normalize();
        Vector3C newPosition = camera.getPosition().added(direction.multiplied(delta));

        if (camera.getTarget().subtracted(newPosition).getLength() > minDistance) {
            camera.setPosition(newPosition);
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

        camera.setPosition(STransformation.rotateTwoAngles(camera.getPosition(), -deltaX, -deltaY));
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

    public Camera getCamera() {
        return camera;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }
}
