package com.cgvsu.model;



import com.cgvsu.matrix.typesVectors.Vector2C;
import com.cgvsu.matrix.typesVectors.Vector3C;

import java.util.*;

public class Model {

    public ArrayList<Vector3C> vertices = new ArrayList<Vector3C>();
    public ArrayList<Vector2C> textureVertices = new ArrayList<Vector2C>();
    public ArrayList<Vector3C> normals = new ArrayList<Vector3C>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
}
