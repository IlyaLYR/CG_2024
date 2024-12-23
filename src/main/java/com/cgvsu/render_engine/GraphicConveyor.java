package com.cgvsu.render_engine;


import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.math.typesMatrix.Matrix4D;
import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.math.typesVectors.Vector4C;

public class GraphicConveyor {


    public static Matrix4D rotateScaleTranslate(Vector3C modelCenter) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
//        ATransformation transformation = builder.translateByCoordinates(0,0,0).build();

        ATransformation transformation = builder
                .translateByVector(modelCenter.multiplied(-1)) // Сдвиг в противоположную сторону центра
                .build();

        return transformation.getTransformationMatrix();
    }

    public static Matrix4D lookAt(Vector3C eye, Vector3C target) {
        return lookAt(eye, target, new Vector3C(0.0, 1.0, 0.0));
    }

    public static Matrix4D lookAt(Vector3C eye, Vector3C target, Vector3C up) {
        Vector3C resultZ = target.subtracted(eye).normalize();
        Vector3C resultX = up.crossProduct(resultZ).normalize();
        Vector3C resultY = resultZ.crossProduct(resultX);

        double[] matrix = new double[]{
                resultX.getX(), resultX.getY(), resultX.getZ(), -resultX.dotProduct(eye),
                resultY.getX(), resultY.getY(), resultY.getZ(), -resultY.dotProduct(eye),
                resultZ.getX(), resultZ.getY(), resultZ.getZ(), -resultZ.dotProduct(eye),
                0, 0, 0, 1
        };

        return new Matrix4D(matrix);
    }

    public static Matrix4D perspective(double fov, double aspectRatio, double nearPlane, double farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        double[] matrix = new double[]{
                tangentMinusOnDegree / aspectRatio, 0, 0, 0,
                0, tangentMinusOnDegree, 0, 0,
                0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), (2 * nearPlane * farPlane) / (nearPlane - farPlane),
                0, 0, 1, 0
        };
        return new Matrix4D(matrix);
    }

    public static Vector3C multiplyMatrix4ByVector3(final Matrix4D matrix, final Vector3C vertex) {
        double[] baseVec4 = new double[]{vertex.getX(), vertex.getY(), vertex.getZ(), 1};

        Vector4C resultVector = matrix.multiplied(new Vector4C(baseVec4));
        double x = resultVector.get(0);
        double y = resultVector.get(1);
        double z = resultVector.get(2);
        double w = resultVector.get(3);

        if (w == 0) {
//            throw new IllegalArgumentException("Invalid transformation: w = 0");
            return new Vector3C();
        }

        return new Vector3C(x / w, y / w, z / w);
    }

    public static Vector2C vertexToPoint(final Vector3C vertex, final int width, final int height) {
        return new Vector2C((vertex.getX() * width + width / 2.0F), (-vertex.getY() * height + height / 2.0F));
    }
}
