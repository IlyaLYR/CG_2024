package com.cgvsu.render_engine;

import ru.cs.vsu.cg2024.matrix.typesMatrix.Matrix4D;
import ru.cs.vsu.cg2024.matrix.typesVectors.Vector3C;
import ru.cs.vsu.cg2024.matrix.typesVectors.Vector4C;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4D rotateScaleTranslate() {
//        double[] matrix = new double[]{
//                1, 0, 0, 0,
//                0, 1, 0, 0,
//                0, 0, 1, 0,
//                0, 0, 0, 1
//        };
        return new Matrix4D(true);
    }

    public static Matrix4D lookAt(Vector3C eye, Vector3C target) {
        return lookAt(eye, target, new Vector3C(0.0, 1.0, 0.0));
    }

    public static Matrix4D lookAt(Vector3C eye, Vector3C target, Vector3C up) {
        Vector3C resultZ = eye.subtracted(target).normalize();
        Vector3C resultX = up.crossProduct(resultZ).normalize();
        Vector3C resultY = resultZ.crossProduct(resultX);

        double[] matrix = new double[]{
                resultX.get(0), resultX.get(1), resultX.get(2), -resultX.dotProduct(eye),
                resultY.get(0), resultY.get(1), resultY.get(2), -resultY.dotProduct(eye),
                resultZ.get(0), resultZ.get(1), resultZ.get(2), -resultZ.dotProduct(eye),
                0, 0, 0, 1
        };

        return new Matrix4D(matrix);
    }

    public static Matrix4D perspective(double fov, double aspectRatio, double nearPlane, double farPlane) {
        double tangent = 1.0 / Math.tan(Math.toRadians(fov / 2));
        double[] matrix = new double[]{
                tangent / aspectRatio, 0, 0, 0,
                0, tangent, 0, 0,
                0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), (2 * nearPlane * farPlane) / (nearPlane - farPlane),
                0, 0, 1, 0
        };
        return new Matrix4D(matrix);
    }

    public static Vector3C multiplyMatrix4ByVector3(final Matrix4D matrix, final Vector3C vertex) {
        double[] baseVec4 = new double[]{vertex.get(0), vertex.get(1), vertex.get(2), 1};

        Vector4C resultVector = matrix.multiplied(new Vector4C(baseVec4));
        double x = resultVector.get(0);
        double y = resultVector.get(1);
        double z = resultVector.get(2);
        double w = resultVector.get(3);

        if (w == 0) {
            throw new IllegalArgumentException("Invalid transformation: w = 0");
        }

        return new Vector3C(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3C vertex, final int width, final int height) {
        float x = (float) ((vertex.get(0) + 1) * 0.5 * width);
        float y = (float) ((1 - vertex.get(1)) * 0.5 * height); // Y-координата перевёрнута
        return new Point2f(x, y);
    }
}
