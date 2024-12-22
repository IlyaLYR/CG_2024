package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;

public class TransferManagerCamera {
    private double mouseX;
    private double mouseY;
    private final Camera camera;

    // Конструктор для инициализации камеры
    public TransferManagerCamera(Camera camera) {
        this.camera = camera;
        this.mouseX = 0;
        this.mouseY = 0;
    }

    public void mouseCameraZoom(double deltaY, double smoothFactor) {
        double delta = deltaY * smoothFactor;
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

    public void onMouseDragged(double x, double y, double smoothFactor) {
        double deltaX = (x - mouseX) * smoothFactor;
        double deltaY = (y - mouseY) * smoothFactor;

        Vector3C position = STransformation.toSpherical(camera.getPosition());

        double theta = position.getY() - deltaX;
        double phi = Math.max(0.1, Math.min(Math.PI - 0.1, position.getZ() - deltaY)); //ограничение на переворот

        camera.setPosition(STransformation.toCartesian(position.getX(), theta, phi));

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
}
