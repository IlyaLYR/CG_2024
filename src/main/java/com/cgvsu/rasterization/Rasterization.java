package com.cgvsu.rasterization;

import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {
    public static void fillTriangle(
            final GraphicsContext graphicsContext,
            final int[] arrX,
            final int[] arrY,
            final double[] arrZ,
            Model mesh,
            final Color[] colors,
            double[][] zBuffer,
            double[] light,
            Vector3C[] normals,
            Vector2C[] textures) {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        sort(arrX, arrY, arrZ, normals, textures, colors);

        for (int y = arrY[0]; y <= arrY[1]; y++) {

            final int x1 = (arrY[1] - arrY[0] == 0) ? arrX[0] :
                    (y - arrY[0]) * (arrX[1] - arrX[0]) / (arrY[1] - arrY[0]) + arrX[0];

            final int x2 = (arrY[0] - arrY[2] == 0) ? arrX[2] :
                    (y - arrY[2]) * (arrX[0] - arrX[2]) / (arrY[0] - arrY[2]) + arrX[2];

            final int Ax = Math.min(x1, x2);
            final int Bx = Math.max(x1, x2);

            if (y < 0) {
                break;
            }

            for (int x = Ax; x <= Bx; x++) {
                if (x < 0 || x >= zBuffer.length || y >= zBuffer[0].length) {
                    break;
                }

                double[] barizenticCoordinate = barycentricCalculator(x, y, arrX, arrY);

                if (!Double.isNaN(barizenticCoordinate[0]) && !Double.isNaN(barizenticCoordinate[1]) && !Double.isNaN(barizenticCoordinate[2]) &&
                        ((barizenticCoordinate[0] + barizenticCoordinate[1] + barizenticCoordinate[2] - 1) < 1e-7f)) {

                    double z = arrZ[0] * barizenticCoordinate[0] + arrZ[1] * barizenticCoordinate[1] + arrZ[2] * barizenticCoordinate[2];
                    int[] rgb = getGradientCoordinatesRGB(barizenticCoordinate, colors);

                    if (z < zBuffer[x][y]) {
                        zBuffer[x][y] = z;
                        if ((barizenticCoordinate[0] < 0.01 || barizenticCoordinate[1] < 0.01 || barizenticCoordinate[2] < 0.01) & mesh.isActivePolyGrid()) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                            continue;
                        } else if (mesh.isActiveTexture()) {
                            texture(barizenticCoordinate, textures, mesh, rgb);
                        }
                        if (mesh.isActiveLighting()) {
                            light(barizenticCoordinate, normals, light, rgb);
                        }
                        pixelWriter.setColor(x, y, Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                }
            }
        }

        for (int y = arrY[1]; y <= arrY[2]; y++) {

            final int x1 = (arrY[2] - arrY[1] == 0) ? arrX[1] :
                    (y - arrY[1]) * (arrX[2] - arrX[1]) / (arrY[2] - arrY[1]) + arrX[1];

            final int x2 = (arrY[0] - arrY[2] == 0) ? arrX[2] :
                    (y - arrY[2]) * (arrX[0] - arrX[2]) / (arrY[0] - arrY[2]) + arrX[2];

            final int Ax = Math.min(x1, x2);
            final int Bx = Math.max(x1, x2);

            if (y < 0) {
                break;
            }

            for (int x = Ax; x <= Bx; x++) {
                if (x < 0 || x >= zBuffer.length || y >= zBuffer[0].length) {
                    break;
                }

                double[] barycentricCoordinate = barycentricCalculator(x, y, arrX, arrY);
                if (!Double.isNaN(barycentricCoordinate[0]) && !Double.isNaN(barycentricCoordinate[1])
                        && !Double.isNaN(barycentricCoordinate[2]) &&
                        ((barycentricCoordinate[0] + barycentricCoordinate[1] + barycentricCoordinate[2] - 1) < 1e-7f)) {

                    double z = arrZ[0] * barycentricCoordinate[0] + arrZ[1] * barycentricCoordinate[1] + arrZ[2] * barycentricCoordinate[2];
                    int[] rgb = getGradientCoordinatesRGB(barycentricCoordinate, colors);

                    if (z < zBuffer[x][y]) {
                        zBuffer[x][y] = z;
                        if ((barycentricCoordinate[0] < 0.01 || barycentricCoordinate[1] < 0.01 || barycentricCoordinate[2] < 0.01) & mesh.isActivePolyGrid()) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                            continue;
                        } else if (mesh.isActiveTexture()) {
                            texture(barycentricCoordinate, textures, mesh, rgb);
                        }
                        if (mesh.isActiveLighting()) {
                            light(barycentricCoordinate, normals, light, rgb);
                        }
                        pixelWriter.setColor(x, y, Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                }
            }
        }
    }

    private static double determinator(int[][] arr) {
        return arr[0][0] * arr[1][1] * arr[2][2] + arr[1][0] * arr[0][2] * arr[2][1] +
                arr[0][1] * arr[1][2] * arr[2][0] - arr[0][2] * arr[1][1] * arr[2][0] -
                arr[0][0] * arr[1][2] * arr[2][1] - arr[0][1] * arr[1][0] * arr[2][2];
    }

    private static double[] barycentricCalculator(int x, int y, int[] arrX, int[] arrY) {
        final double generalDeterminant = determinator(new int[][]{arrX, arrY, new int[]{1, 1, 1}});
        final double coordinate0 = Math.abs(determinator(
                new int[][]{new int[]{x, arrX[1], arrX[2]}, new int[]{y, arrY[1], arrY[2]}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        final double coordinate1 = Math.abs(determinator(
                new int[][]{new int[]{arrX[0], x, arrX[2]}, new int[]{arrY[0], y, arrY[2]}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        final double coordinate2 = Math.abs(determinator(
                new int[][]{new int[]{arrX[0], arrX[1], x}, new int[]{arrY[0], arrY[1], y}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        return new double[]{coordinate0, coordinate1, coordinate2};
    }

    public static int[] getGradientCoordinatesRGB(final double[] baristicCoords, final Color[] color) {
        int r = Math.min(255, (int) Math.abs(color[0].getRed() * 255 * baristicCoords[0] + color[1].getRed()
                * 255 * baristicCoords[1] + color[2].getRed() * 255 * baristicCoords[2]));
        int g = Math.min(255, (int) Math.abs(color[0].getGreen() * 255 * baristicCoords[0] + color[1].getGreen()
                * 255 * baristicCoords[1] + color[2].getGreen() * 255 * baristicCoords[2]));
        int b = Math.min(255, (int) Math.abs(color[0].getBlue() * 255 * baristicCoords[0] + color[1].getBlue()
                * 255 * baristicCoords[1] + color[2].getBlue() * 255 * baristicCoords[2]));

        return new int[]{r, g, b};
    }

    private static void sort(int[] x, int[] y, double[] z, Vector3C[] n, Vector2C[] t, Color[] c) {
        if (y[0] > y[1]) {
            swap(x, y, z, n, t, c, 0, 1);
        }
        if (y[0] > y[2]) {
            swap(x, y, z, n, t, c, 0, 2);
        }
        if (y[1] > y[2]) {
            swap(x, y, z, n, t, c, 1, 2);
        }
    }

    private static void swap(int[] x, int[] y, double[] z, Vector3C[] n, Vector2C[] t, Color[] c, int i, int j) {
        int tempY = y[i];
        int tempX = x[i];
        double tempZ = z[i];
        Vector3C tempN = n[i];
        Vector2C tempT = t[i];
        Color tempC = c[i];
        x[i] = x[j];
        y[i] = y[j];
        z[i] = z[j];
        n[i] = n[j];
        t[i] = t[j];
        c[i] = c[j];
        x[j] = tempX;
        y[j] = tempY;
        z[j] = tempZ;
        n[j] = tempN;
        t[j] = tempT;
        c[j] = tempC;
    }

    public static Vector3C smoothingNormal(final double[] baristicCoords, final Vector3C[] normals) {
        return new Vector3C((float) (baristicCoords[0] * normals[0].getX() + baristicCoords[1] * normals[1].getX() + baristicCoords[2] * normals[2].getX()),
                (float) (baristicCoords[0] * normals[0].getY() + baristicCoords[1] * normals[1].getY() + baristicCoords[2] * normals[2].getY()),
                (float) (baristicCoords[0] * normals[0].getZ() + baristicCoords[1] * normals[1].getZ() + baristicCoords[2] * normals[2].getZ()));
    }

    public static void calculateLight(int[] rgb, double[] light, Vector3C normal) {
        double k = 0.5;
        double l = -(light[0] * normal.getX() + light[1] * normal.getY() + light[2] * normal.getZ());
        if (l < 0) {
            l = 0;
        }
        rgb[0] = Math.min(255, (int) (rgb[0] * (1 - k) + rgb[0] * k * l));
        rgb[1] = Math.min(255, (int) (rgb[1] * (1 - k) + rgb[1] * k * l));
        rgb[2] = Math.min(255, (int) (rgb[2] * (1 - k) + rgb[2] * k * l));
    }

    public static void light(final double[] barycentric, final Vector3C[] normals, double[] light, int[] rgb) {
        Vector3C smooth = smoothingNormal(barycentric, normals);
        calculateLight(rgb, light, smooth);
    }

    public static void texture(double[] barycentric, Vector2C[] textures, Model mesh, int[] rgb) {
        double[] texture = getGradientCoordinatesTexture(barycentric, textures);
        int u = (int) Math.round(texture[0] * (mesh.texture.width - 1));
        int v = (int) Math.round(texture[1] * (mesh.texture.height - 1));
        if (u < mesh.texture.width && v < mesh.texture.height) {
            rgb[0] = mesh.texture.pixelData[u][v][0];
            rgb[1] = mesh.texture.pixelData[u][v][1];
            rgb[2] = mesh.texture.pixelData[u][v][2];
        }
    }

    public static double[] getGradientCoordinatesTexture(double[] barycentric, Vector2C[] texture) {
        return new double[]{
                (barycentric[0] * texture[0].getX()) + (barycentric[1] * texture[1].getX()) + (barycentric[2] * texture[2].getX()),
                (barycentric[0] * texture[0].getY()) + (barycentric[1] * texture[1].getY()) + (barycentric[2] * texture[2].getY())
        };
    }
}