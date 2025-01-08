package com.cgvsu.model;


import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.texture.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    public final ArrayList<Vector3C> vertices = new ArrayList<>();
    public final ArrayList<Vector2C> textureVertices = new ArrayList<>();
    public String nameOfModel;
    public ArrayList<Vector3C> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
    public String pathTexture = null;
    public Texture texture = null;
    private boolean isActiveTexture = false;
    private boolean isActiveLighting = false;
    private boolean isActivePolyGrid = false;

    public ArrayList<Vector3C> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2C> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3C> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public String getNameOfModel() {
        return nameOfModel;
    }

    public void setNameOfModel(String nameOfModel) {
        this.nameOfModel = nameOfModel;
    }

    public Vector3C getModelCenter() {
        double xSum = 0, ySum = 0, zSum = 0;
        int vertexCount = vertices.size();

        for (Vector3C vertex : vertices) {
            xSum += vertex.getX();
            ySum += vertex.getY();
            zSum += vertex.getZ();
        }

        // Вычисляем среднее значение по каждой координате
        return new Vector3C(xSum / vertexCount, ySum / vertexCount, zSum / vertexCount);
    }

    public void triangulate() {
        ArrayList<Polygon> triangulatedPolygons = new ArrayList<>(); // Новый список для хранения триангулированных полигонов

        for (Polygon polygon : polygons) { // Проходим по всем полигонам модели
            List<Integer> vertexIndices = polygon.getVertexIndices(); // Получаем индексы вершин полигона
            List<Integer> textureVertexIndices = polygon.getTextureVertexIndices(); // Получаем индексы текстурных координат
            List<Integer> normalIndices = polygon.getNormalIndices(); // Получаем индексы нормалей

            if (vertexIndices.size() <= 3) { // Если полигон уже треугольник, добавляем его без изменений
                triangulatedPolygons.add(polygon);
            } else {
                // Выполняем триангуляцию с помощью "веерного" метода
                for (int i = 1; i < vertexIndices.size() - 1; i++) {
                    Polygon triangle = new Polygon(); // Создаём новый треугольник

                    // Добавляем индексы вершин треугольника
                    triangle.addVertexIndex(vertexIndices.get(0)); // Первая вершина веера
                    triangle.addVertexIndex(vertexIndices.get(i)); // Текущая вершина
                    triangle.addVertexIndex(vertexIndices.get(i + 1)); // Следующая вершина

                    // Если есть текстурные координаты, добавляем их индексы
                    if (!textureVertexIndices.isEmpty()) {
                        triangle.addTextureVertexIndex(textureVertexIndices.get(0)); // Первая текстурная координата веера
                        triangle.addTextureVertexIndex(textureVertexIndices.get(i)); // Текущая текстурная координата
                        triangle.addTextureVertexIndex(textureVertexIndices.get(i + 1)); // Следующая текстурная координата
                    }

                    // Если есть нормали, добавляем их индексы
                    if (!normalIndices.isEmpty()) {
                        triangle.addNormalIndex(normalIndices.get(0)); // Первая нормаль веера
                        triangle.addNormalIndex(normalIndices.get(i)); // Текущая нормаль
                        triangle.addNormalIndex(normalIndices.get(i + 1)); // Следующая нормаль
                    }

                    triangulatedPolygons.add(triangle); // Добавляем треугольник в список триангулированных полигонов
                }
            }
        }

        // Заменяем исходные полигоны на триангулированные
        this.polygons = triangulatedPolygons;
    }


    //  Расчет нормалей
    public void computeNormals() {
        Map<Integer, Vector3C> vertexNormals = new HashMap<>();
        Map<Integer, Integer> vertexNormalsCount = new HashMap<>();


        for (Polygon polygon : polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            if (vertexIndices.size() < 3) {
                continue;
            }

            Vector3C v0 = vertices.get(vertexIndices.get(0));
            Vector3C v1 = vertices.get(vertexIndices.get(1));
            Vector3C v2 = vertices.get(vertexIndices.get(2));

            Vector3C edge1 = v1.subtracted(v0);
            Vector3C edge2 = v2.subtracted(v0);
            Vector3C faceNormal = edge1.crossProduct(edge2).normalize();

            for (int index : vertexIndices) {
                vertexNormals.compute(index, (k, v) -> {
                    if (v == null) {
                        return faceNormal;
                    } else {
                        return v.added(faceNormal);
                    }
                });
            }

            for (int index : vertexIndices) {

                if (vertexNormalsCount.containsKey(index)) {
                    vertexNormalsCount.put(index, vertexNormalsCount.get(index) + 1);
                } else {
                    vertexNormalsCount.put(index, 1);
                }

            }
        }


        vertexNormals.keySet().forEach(index -> vertexNormals.put(index, vertexNormals.get(index).divided(vertexNormalsCount.get(index))));


        normals = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            normals.add(vertexNormals.getOrDefault(i, new Vector3C(0, 0, 0)));
        }
    }

    public boolean isActiveTexture() {
        return isActiveTexture;
    }

    public void setActiveTexture(boolean activeTexture) {
        isActiveTexture = activeTexture;
    }

    public boolean isActiveLighting() {
        return isActiveLighting;
    }

    public void setActiveLighting(boolean activeLighting) {
        isActiveLighting = activeLighting;
    }

    public boolean isActivePolyGrid() {
        return isActivePolyGrid;
    }

    public void setActivePolyGrid(boolean activePolyGrid) {
        isActivePolyGrid = activePolyGrid;
    }

    public String getPathTexture() {
        return pathTexture;
    }

    public void setPathTexture(String pathTexture) {
        this.pathTexture = pathTexture;
    }
}
