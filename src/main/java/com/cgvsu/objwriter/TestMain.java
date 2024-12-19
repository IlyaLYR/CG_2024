package com.cgvsu.objwriter;

import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.ObjReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMain {
    public static void main(String[] args) throws IOException {
        Model model1 = new Model();
        model1.vertices = new ArrayList<>(Arrays.asList(
                new Vector3C(1.0f, 2.0f, 3.0f),
                new Vector3C(4.0f, 5.0f, 6.0f),
                new Vector3C(7.0f, 8.0f, 9.0f)
        ));
        model1.textureVertices = new ArrayList<>(Arrays.asList(
                new Vector2C(0.1f, 0.2f),
                new Vector2C(0.3f, 0.4f),
                new Vector2C(0.5f, 0.6f)
        ));
        model1.normals = new ArrayList<>(Arrays.asList(
                new Vector3C(-1.0f, -2.0f, -3.0f),
                new Vector3C(-4.0f, -5.0f, -6.0f),
                new Vector3C(-7.0f, -8.0f, -6.0f)
        ));
        model1.polygons = new ArrayList<>(List.of(
                new Polygon() {{
                    setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
                    setTextureVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
                    setNormalIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
                }}
        ));

        String filename = "ObjWriterTest1.obj";

        ObjWriterClass objWriter = new ObjWriterClass();
        objWriter.write(model1, filename);

        Path fileName = Path.of("ObjWriterTest1.obj");
        String fileContent = Files.readString(fileName);

        System.out.println("Model " + filename + ":");
        Model model = ObjReader.read(fileContent);

        System.out.println("Vertices: " + model.getVertices().size());
        System.out.println("Texture vertices: " + model.getTextureVertices().size());
        System.out.println("Normals: " + model.getNormals().size());
        System.out.println("Polygons: " + model.getPolygons().size());
    }
}