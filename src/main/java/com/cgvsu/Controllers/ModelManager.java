package com.cgvsu.Controllers;

import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.render_engine.GraphicConveyor;

import java.util.HashMap;

public class ModelManager {
    private final HashMap<String, Model> meshes = new HashMap<>();
    private final HashMap<String, Model> transformMeshes = new HashMap<>();
    private Model model;
    private double mouseX;
    private double mouseY;

    public ModelManager() {
        mouseX = 0;
        mouseY = 0;
    }

    public Model applyModel(String rotateX,
                            String rotateY,
                            String rotateZ,
                            String scaleX,
                            String scaleY,
                            String scaleZ,
                            String translateX,
                            String translateY,
                            String translateZ
    ) {
        double doubleRotateX = Math.toRadians(Float.parseFloat(rotateX));
        double doubleRotateY = Math.toRadians(Float.parseFloat(rotateY));
        double doubleRotateZ = Math.toRadians(Float.parseFloat(rotateZ));
        double doubleScaleX = Float.parseFloat(scaleX);
        double doubleScaleY = Float.parseFloat(scaleY);
        double doubleScaleZ = Float.parseFloat(scaleZ);
        double doubleTranslateX = Float.parseFloat(translateX);
        double doubleTranslateY = Float.parseFloat(translateY);
        double doubleTranslateZ = Float.parseFloat(translateZ);

        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .translateByCoordinates(doubleTranslateX, doubleTranslateY, doubleTranslateZ)
                .rotateByX(doubleRotateX)
                .rotateByY(doubleRotateY)
                .rotateByZ(doubleRotateZ)
                .scaleByCoordinates(doubleScaleX, doubleScaleY, doubleScaleZ)
                .build();

        return transformation.applyTransformationToModel(model);
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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
        Vector3C target = model.getModelCenter();

        //Перенос камеры в локальные координаты относительно target
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformationToStart = builder.translateByVector(target.multiplied(-1)).build();
        model = transformationToStart.applyTransformationToModel(model);

        //Вращаем камеру вокруг
        model.vertices.replaceAll(vector -> STransformation.rotateTwoAngles(vector, -deltaX, -deltaY));
        //Возвращаем камеру обратно
        ATransformation transformationBack = new ATransformation.ATBuilder().translateByVector(target).build();
        model = transformationBack.applyTransformationToModel(model);

        mouseX = x;
        mouseY = y;
    }

    public void addModel(String name, Model model) {
        meshes.put(name, model);
        transformMeshes.put(name, GraphicConveyor.rotateScaleTranslate(model, model.getModelCenter()));
    }

    public void removeModel(String name) {
        meshes.remove(name);
        transformMeshes.remove(name);
    }

    public Model getModel(String name) {
        return meshes.get(name);
    }

    public Model getTransformedModel(String name) {
        return transformMeshes.get(name);
    }

    public void clear() {
        meshes.clear();
        transformMeshes.clear();
    }

    public HashMap<String, Model> getMeshes() {
        return meshes;
    }

    public HashMap<String, Model> getTransformMeshes() {
        return transformMeshes;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMesh(String name, Model model) {
        transformMeshes.put(name, model);
    }
}