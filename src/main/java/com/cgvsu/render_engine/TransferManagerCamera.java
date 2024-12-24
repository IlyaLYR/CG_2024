package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.ATransformation;
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

        //Получаем позицию камеры и целевую точку
        Vector3C position = camera.getPosition();
        Vector3C target = camera.getTarget();

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
        camera.setPosition(position);

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

    public Camera getCamera() {
        return camera;
    }
}
