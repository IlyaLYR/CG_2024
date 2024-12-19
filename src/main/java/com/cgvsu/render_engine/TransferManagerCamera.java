package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.ATransformation;
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

        // Инициализируем углы на основе позиции камеры
        Vector3C position = camera.getPosition();
        Vector3C target = camera.getTarget();
        Vector3C direction = target.subtracted(position).normalize();

        this.ANGLEForXZ = Math.toDegrees(Math.atan2(direction.getZ(), direction.getX())); // Угол в плоскости XZ
        this.ANGLEForY = Math.toDegrees(Math.asin(direction.getY())); // Угол по высоте (Y)
    }

    public void mouseCameraZoom(double deltaY, double smoothFactor) {
        double delta = deltaY * smoothFactor;
        double minDistance = 2.0;

        Vector3C direction = camera.getTarget().subtracted(camera.getPosition()).normalize();

        // Создаем матрицу трансформации для зумирования
        ATransformation transformation = new ATransformation.ATBuilder()
                .translateByVector(direction.multiplied(delta))
                .build();

        Vector3C newPosition = transformation.applyTransformationToVector(camera.getPosition());

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
        ANGLEForY = Math.max(-90, Math.min(90, ANGLEForY + deltaY));


// Построение матрицы аффинного преобразования
        ATransformation transformation = new ATransformation.ATBuilder()
                .rotateByY(Math.toRadians(-ANGLEForXZ))                  // Вращение вокруг оси Y
                .rotateByX(Math.toRadians(-ANGLEForY))                   // Вращение вокруг оси X
                .build();

// Применяем трансформацию к позиции камеры
        Vector3C newPosition = transformation.applyTransformationToVector(new Vector3C(0, 0, -RADIUS));
        camera.setPosition(newPosition.added(camera.getTarget()));

        // Обновляем текущие координаты мыши
        mouseX = x;
        mouseY = y;
    }
}