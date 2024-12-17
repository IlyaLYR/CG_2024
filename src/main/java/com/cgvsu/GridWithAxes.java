//package com.cgvsu;
//
//import com.cgvsu.render_engine.Camera;
//import com.cgvsu.math.typesMatrix.Matrix4D;
//import com.cgvsu.math.typesVectors.Vector3C;
//import com.cgvsu.math.core.MatrixUtils;
//import com.cgvsu.render_engine.GraphicConveyor;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//
//import javax.vecmath.Point2f;
//
//import static com.cgvsu.render_engine.GraphicConveyor.vertexToPoint;
//
//public class GridWithAxes {
//
//    private double width;
//    private double height;
//    private double cellSize;
//    private Camera camera;
//
//    public GridWithAxes(double width, double height, double cellSize, Camera camera) {
//        this.width = width;
//        this.height = height;
//        this.cellSize = cellSize;
//        this.camera = camera;
//    }
//
//    public void draw(Canvas canvas) {
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        // Clear the canvas
//        gc.clearRect(0, 0, width, height);
//
//        // Set canvas dimensions
//        canvas.setWidth(width);
//        canvas.setHeight(height);
//
//        // Get camera matrices
//        Matrix4D viewMatrix = camera.getViewMatrix();
//        Matrix4D projectionMatrix = camera.getProjectionMatrix();
//        Matrix4D modelViewProjectionMatrix = MatrixUtils.multiplied(projectionMatrix, viewMatrix);
//
//        // Draw grid
//        gc.setStroke(Color.LIGHTGRAY);
//        for (double x = -width / 2; x <= width / 2; x += cellSize) {
//            for (double z = -width / 2; z <= width / 2; z += cellSize) {
//                drawLine3D(gc, new Vector3C(x, 0, z), new Vector3C(x + cellSize, 0, z), modelViewProjectionMatrix);
//                drawLine3D(gc, new Vector3C(x, 0, z), new Vector3C(x, 0, z + cellSize), modelViewProjectionMatrix);
//            }
//        }
//
//        // Draw X axis
//        gc.setStroke(Color.RED);
//        drawLine3D(gc, new Vector3C(-width / 2, 0, 0), new Vector3C(width / 2, 0, 0), modelViewProjectionMatrix);
//
//        // Draw Y axis
//        gc.setStroke(Color.GREEN);
//        drawLine3D(gc, new Vector3C(0, -height / 2, 0), new Vector3C(0, height / 2, 0), modelViewProjectionMatrix);
//
//        // Draw Z axis
//        gc.setStroke(Color.BLUE);
//        drawLine3D(gc, new Vector3C(0, 0, -width / 2), new Vector3C(0, 0, width / 2), modelViewProjectionMatrix);
//    }
//
//    private void drawLine3D(GraphicsContext gc, Vector3C p1, Vector3C p2, Matrix4D mvpMatrix) {
//        Vector3C projectedP1 = GraphicConveyor.multiplyMatrix4ByVector3(mvpMatrix, p1);
//        Vector3C projectedP2 = GraphicConveyor.multiplyMatrix4ByVector3(mvpMatrix, p2);
//
//        Point2f screenPoint1 = vertexToPoint(projectedP1, (int) width, (int) height);
//        Point2f screenPoint2 = vertexToPoint(projectedP2, (int) width, (int) height);
//
//        gc.strokeLine(screenPoint1.x, screenPoint1.y, screenPoint2.x, screenPoint2.y);
//    }
//
//    public void setWidth(double width) {
//        this.width = width;
//    }
//
//    public void setHeight(double height) {
//        this.height = height;
//    }
//
//    public void setCellSize(double cellSize) {
//        this.cellSize = cellSize;
//    }
//
//    public void setCamera(Camera camera) {
//        this.camera = camera;
//    }
//
//    public void update(Canvas canvas) {
//        draw(canvas);
//    }
//}
