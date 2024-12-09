package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import ru.cs.vsu.cg2024.matrix.typesVectors.Vector2C;
import ru.cs.vsu.cg2024.matrix.typesVectors.Vector3C;

import java.util.*;

public class Model {

    public ArrayList<Vector3C> vertices = new ArrayList<Vector3C>();
    public ArrayList<Vector2C> textureVertices = new ArrayList<Vector2C>();
    public ArrayList<Vector3C> normals = new ArrayList<Vector3C>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();
}
