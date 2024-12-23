package com.cgvsu.model;



import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;

import java.util.*;

public class Model {

    public String nameOfModel;
    public ArrayList<Vector3C> vertices = new ArrayList<Vector3C>();
    public ArrayList<Vector3C> textureVertices = new ArrayList<Vector3C>();
    public ArrayList<Vector3C> normals = new ArrayList<Vector3C>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public ArrayList<Vector3C> getVertices() {
        return vertices;
    }

    public ArrayList<Vector3C> getTextureVertices() {
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
}
