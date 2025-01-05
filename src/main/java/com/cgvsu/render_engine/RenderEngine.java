package com.cgvsu.render_engine;


import com.cgvsu.Camera.Camera;
import com.cgvsu.math.core.MatrixUtils;
import com.cgvsu.math.typesMatrix.Matrix4D;
import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.rasterization.Rasterization;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cgvsu.render_engine.GraphicConveyor.multiplyMatrix4ByVector3;
import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final HashMap<String, Model> meshes,
            final int width,
            final int height,
            Color color,
            double[][] zBuffer) {

        graphicsContext.setStroke(color);

        for (Model mesh : meshes.values()) {
            Matrix4D viewMatrix = camera.getViewMatrix();
            Matrix4D projectionMatrix = camera.getProjectionMatrix();

            // Итоговая матрица MVP
            Matrix4D modelViewProjectionMatrix = MatrixUtils.multiplied(projectionMatrix, viewMatrix);


            final int nPolygons = mesh.polygons.size();
            for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();
                Vector3C[] normals = new Vector3C[3];
                ArrayList<Double> arrayZ = new ArrayList<>();
                ArrayList<Vector2C> resultPoints = new ArrayList<>();
                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    // Получаем вершину
                    Vector3C vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                    normals[vertexInPolygonInd] = (mesh.normals.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd)));
                    Vector3C transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);
                    arrayZ.add(transformedVertex.getZ());

                    // Преобразуем в координаты экрана
                    Vector2C resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                    resultPoints.add(resultPoint);
                }

                // Растеризация полигонов
                int[] arrX = {(int) resultPoints.get(0).getX(), (int) resultPoints.get(1).getX(), (int) resultPoints.get(2).getX()};
                int[] arrY = {(int) resultPoints.get(0).getY(), (int) resultPoints.get(1).getY(), (int) resultPoints.get(2).getY()};
                double[] arrZ = {arrayZ.get(0), arrayZ.get(1), arrayZ.get(2)};
                javafx.scene.paint.Color[] colors = {color, color, color};
                double[] light = new double[]{viewMatrix.get(0, 2), viewMatrix.get(1, 2), viewMatrix.get(2, 2)};
                Rasterization.fillTriangle(graphicsContext, arrX, arrY, arrZ, mesh, colors, zBuffer, light, normals);
            }
        }
    }
}
