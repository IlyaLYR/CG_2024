package com.cgvsu.model;



import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;

import java.util.*;

public class Model {

    public String nameOfModel;
    public ArrayList<Vector3C> vertices = new ArrayList<>();
    public ArrayList<Vector2C> textureVertices = new ArrayList<>();
    public ArrayList<Vector3C> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();

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

    public  Vector3C calculateModelCenter() {
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

    public void setNameOfModel(String nameOfModel) {
        this.nameOfModel = nameOfModel;
    }
}
