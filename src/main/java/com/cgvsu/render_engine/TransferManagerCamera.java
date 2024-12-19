package com.cgvsu.render_engine;

import com.cgvsu.math.typesVectors.Vector3C;

public class TransferManagerCamera {
    private double mouseX;
    private double mouseY;
    private double ANGLEForXZ;
    private double ANGLEForY;
    private double RADIUS;
    private final Camera camera;

    public TransferManagerCamera(Camera camera) {
        this.camera = camera;
        this.mouseX = 0;
        this.mouseY = 0;
        this.RADIUS = camera.getRadius(); // Инициализация радиуса

        // Получаем начальную позицию камеры и её целевую точку
        Vector3C position = camera.getPosition();
        Vector3C target = camera.getTarget();
        Vector3C direction = target.subtracted(position).normalize();

        // Вычисляем углы
        this.ANGLEForXZ = Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())); // Угол в плоскости XZ
        this.ANGLEForY = Math.toDegrees(Math.asin(direction.getY())); // Угол по высоте (Y)
    }

    public void mouseCameraZoom(double deltaY, double smoothFactor) {
        double delta = deltaY * smoothFactor;
        double minDistance = 2.0;

        Vector3C direction = camera.getTarget().subtracted(camera.getPosition()).normalize();
        Vector3C newPosition = camera.getPosition().added(direction.multiplied(delta));

        // Проверяем, чтобы расстояние не было меньше минимального
        if (camera.getTarget().subtracted(newPosition).getLength() > minDistance) {
            camera.setPosition(newPosition);
        }
    }

    public void fixPoint(double detX, double detY) {
        mouseX = detX;
        mouseY = detY;
        RADIUS = camera.getRadius();
    }

    public void onMouseDragged(double x, double y, double smoothFactor) {
        double deltaX = x - mouseX;
        double deltaY = y - mouseY;

        // Обновляем углы, добавляя изменения
        ANGLEForXZ = (ANGLEForXZ - deltaX) % 360;
        ANGLEForY = Math.max(-90, Math.min(90, ANGLEForY + deltaY)); // Ограничение угла

        // Вычисляем новое положение камеры
        double thetaXZ = Math.toRadians(ANGLEForXZ);
        double thetaY = Math.toRadians(ANGLEForY);
        Vector3C target = camera.getTarget();

        double newX = target.getX() + RADIUS * Math.cos(thetaXZ) * Math.cos(thetaY);
        double newY = target.getY() + RADIUS * Math.sin(thetaY);
        double newZ = target.getZ() + RADIUS * Math.sin(thetaXZ) * Math.cos(thetaY);

        // Устанавливаем новую позицию камеры
        camera.setPosition(new Vector3C(newX, newY, newZ));

        // Обновляем текущие координаты мыши
        mouseX = x;
        mouseY = y;
    }
}
