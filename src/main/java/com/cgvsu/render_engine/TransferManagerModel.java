package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;

public class TransferManagerModel {
    private Model model;
    private double mouseX = 0;
    private double mouseY = 0;

    public TransferManagerModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;

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

    public void fixPoint(double detX, double detY) {
        mouseX = detX;
        mouseY = detY;
    }

    public Model rotateAroundCentralPoint(double x, double y) {
        double deltaX = (x - mouseX) * 0.02;
        double deltaY = (y - mouseY) * 0.02;
        // Получение центра модели
        Vector3C modelCenter = model.getModelCenter();

        // Создание трансформаций
        ATransformation toStart = new ATransformation.ATBuilder()
                .translateByVector(modelCenter.multiplied(-1)) // Перемещение в начало
                .build();

        ATransformation toEnd = new ATransformation.ATBuilder()
                .translateByVector(modelCenter) // Возврат в исходное положение
                .build();

        // Создание новой модели для хранения преобразованных данных
        Model transformedModel = new Model();

        // Преобразование вершин
        for (Vector3C vertex : model.vertices) {
            // Применяем трансформации
            Vector3C transformedVertex = toStart.applyTransformationToVector(vertex);
            transformedVertex = STransformation.rotateTwoAngles(transformedVertex, -deltaX, -deltaY);
            transformedVertex = toEnd.applyTransformationToVector(transformedVertex);

            transformedModel.vertices.add(transformedVertex);
        }

        // Копируем остальные свойства модели
        transformedModel.textureVertices.addAll(model.textureVertices);
        transformedModel.normals.addAll(model.normals);
        transformedModel.polygons.addAll(model.polygons);

        return transformedModel;
    }

}
