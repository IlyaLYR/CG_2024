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
import com.cgvsu.rasterization.Rasterization;

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
            Color color) {

        graphicsContext.setStroke(color);

        for (Model mesh : meshes.values()) {
            Matrix4D viewMatrix = camera.getViewMatrix();
            Matrix4D projectionMatrix = camera.getProjectionMatrix();

            // Итоговая матрица MVP
            Matrix4D modelViewProjectionMatrix = MatrixUtils.multiplied(projectionMatrix, viewMatrix);


            final int nPolygons = mesh.polygons.size();
            for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

                ArrayList<Vector2C> resultPoints = new ArrayList<>();
                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    // Получаем вершину
                    Vector3C vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                    // Преобразуем в координаты экрана
                    Vector2C resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                    resultPoints.add(resultPoint);
                }

                // Отрисовываем рёбра полигона
                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    graphicsContext.strokeLine(
                            resultPoints.get(vertexInPolygonInd - 1).getX(),
                            resultPoints.get(vertexInPolygonInd - 1).getY(),
                            resultPoints.get(vertexInPolygonInd).getX(),
                            resultPoints.get(vertexInPolygonInd).getY());
                }

                // Замыкаем полигон, если это необходимо
                if (nVerticesInPolygon > 0) {
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).getX(),
                            resultPoints.get(nVerticesInPolygon - 1).getY(),
                            resultPoints.get(0).getX(),
                            resultPoints.get(0).getY());
                }

                // Растеризация полигонов
                int[] arrX = {(int) resultPoints.get(0).getX(), (int) resultPoints.get(1).getX(), (int) resultPoints.get(2).getX()};
                int[] arrY = {(int) resultPoints.get(0).getY(), (int) resultPoints.get(1).getY(), (int) resultPoints.get(2).getY()};
                javafx.scene.paint.Color[] colors = {Color.CYAN, Color.CYAN, Color.CYAN};
                Rasterization.fillTriangle(graphicsContext, arrX, arrY, colors);
            }
        }
    }
}
