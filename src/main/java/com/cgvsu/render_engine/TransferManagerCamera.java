package com.cgvsu.render_engine;

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

        // Получаем текущую позицию камеры
        double xCamera = camera.getPosition().getX();
        double yCamera = camera.getPosition().getY();
        double zCamera = camera.getPosition().getZ();


        double radius = camera.getRadius();
        double phi = Math.acos(yCamera / radius); // Широта
        double theta = Math.atan2(zCamera, xCamera); // Долгота

        theta -= deltaX;
        phi -= deltaY;

        // Ограничиваем phi, чтобы избежать переворота
        phi = Math.max(0.1, Math.min(Math.PI - 0.1, phi));

        xCamera = radius * Math.sin(phi) * Math.cos(theta);
        yCamera = radius * Math.cos(phi);
        zCamera = radius * Math.sin(phi) * Math.sin(theta);


        camera.setPosition(new Vector3C(xCamera, yCamera, zCamera));

        mouseX = x;
        mouseY = y;
    }
}
