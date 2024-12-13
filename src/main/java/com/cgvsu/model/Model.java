package com.cgvsu.model;



import com.cgvsu.matrix.typesVectors.Vector2C;
import com.cgvsu.matrix.typesVectors.Vector3C;

import java.util.*;

public class Model {

    public ArrayList<Vector3C> vertices = new ArrayList<Vector3C>();
    public ArrayList<Vector2C> textureVertices = new ArrayList<Vector2C>();
    public ArrayList<Vector3C> normals = new ArrayList<Vector3C>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public ArrayList<Vector3f> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }
}
