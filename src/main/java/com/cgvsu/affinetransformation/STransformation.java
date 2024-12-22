package com.cgvsu.affinetransformation;

import com.cgvsu.math.typesVectors.Vector3C;

import java.util.ArrayList;

public class STransformation {

    // Преобразование из декартовых в сферические координаты
    public static Vector3C toSpherical(Vector3C vector) {
        //Инверсия оси Y и Z
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        double r = Math.sqrt(x * x + y * y + z * z);
        double phi = Math.acos(y / r);
        double theta = Math.atan2(z, x);

        return new Vector3C(r, theta, phi);
    }

    // Преобразование из сферических в декартовы координаты
    public static Vector3C toCartesian(double r, double theta, double phi) {
        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.cos(phi);
        double z = r * Math.sin(phi) * Math.sin(theta);

        return new Vector3C(new double[]{x, y, z});
    }

    // Преобразование списка векторов из декартовых в сферические координаты
    public static ArrayList<Vector3C> toSphericalList(ArrayList<Vector3C> vectorList) {
        ArrayList<Vector3C> sphericalList = new ArrayList<>();
        for (Vector3C vector : vectorList) {
            sphericalList.add(toSpherical(vector));
        }
        return sphericalList;
    }

    // Преобразование списка векторов из сферических в декартовы координаты
    public static ArrayList<Vector3C> toCartesianList(ArrayList<Vector3C> sphericalList) {
        ArrayList<Vector3C> cartesianList = new ArrayList<>();
        for (Vector3C spherical : sphericalList) {
            cartesianList.add(toCartesian(spherical.getX(), spherical.getY(), spherical.getZ()));
        }
        return cartesianList;
    }

    // Вращение по азимутальному углу (theta)
    public static Vector3C rotateAzimuth(Vector3C vector, double angle) {
        Vector3C spherical = toSpherical(vector);
        return toCartesian(spherical.getX(), spherical.getY() + angle, spherical.getZ());
    }

    // Вращение по зенитному углу (phi)
    public static Vector3C rotatePhi(Vector3C vector, double angle) {
        Vector3C spherical = toSpherical(vector);
        return toCartesian(spherical.getX(), spherical.getY(), spherical.getZ() + angle);
    }

    // Метод для изменения обоих углов (azimuth и zenith) в одном методе
    public static Vector3C rotateTwoAngles(Vector3C vector, double deltaTheta, double deltaPhi) {
        Vector3C spherical = toSpherical(vector);
        double newTheta = spherical.getY() + deltaTheta;
        double newPhi = spherical.getZ() + deltaPhi;

        return toCartesian(spherical.getX(), newTheta, newPhi);
    }
}