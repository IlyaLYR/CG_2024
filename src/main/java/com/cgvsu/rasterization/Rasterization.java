package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void drawRectangle(final GraphicsContext graphicsContext, final int x, final int y, final int width, final int height, final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }

    public static void drawLine(final GraphicsContext graphicsContext, int x1, int y1, int x2, int y2, final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();


        int dx = x2 - x1;
        int dy = y2 - y1;

        if (Math.abs(dx) >= Math.abs(dy)) {
            double k = (double) dy / (double) dx;
            for (int i = 0; dx > 0 ? i < dx : i >= dx; i += (dx > 0 ? 1 : -1)) {
                int x = i + x1;
                int y = (int) (i * k + y1);
                pixelWriter.setColor(x, y, color);
            }
        } else {
            double k = (double) dx / (double) dy;
            for (int i = 0; dy > 0 ? i <= dy : i >= dy; i += (dy > 0 ? 1 : -1)) {
                int x = (int) (i * k + x1);
                int y = i + y1;
                pixelWriter.setColor(x, y, color);
            }
        }
    }

    public static void drawLinePreBresenham(final GraphicsContext graphicsContext, int x1, int y1, int x2, int y2, final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int dx = x2 - x1;
        int dy = y2 - y1;
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        float accretion = 0;

        if (absDx > absDy) {
            int y = y1;
            int direction = dy != 0 ? (dy > 0 ? 1 : -1) : 0;
            for (int x = x1; dx > 0 ? x <= x2 : x >= x2; x += (dx > 0 ? 1 : -1)) {
                pixelWriter.setColor(x, y, color);
                accretion += (float) absDy / absDx;

                if (accretion >= 1.0f) {
                    accretion -= 1.0f;
                    y += direction;
                }
            }
        } else {
            int x = x1;
            int direction = dx != 0 ? (dx > 0 ? 1 : -1) : 0;
            for (int y = y1; dy > 0 ? y <= y2 : y >= y2; y += (dy > 0 ? 1 : -1)) {
                pixelWriter.setColor(x, y, color);
                accretion += (float) absDx / absDy;

                if (accretion >= 1.0f) {
                    accretion -= 1.0f;
                    x += direction;
                }
            }
        }
    }

    public static void BresenhamAlgorithm(final GraphicsContext graphicsContext, int x1, int y1, int x2, int y2, final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int dx = x2 - x1;
        int dy = y2 - y1;
        int absDx = Math.abs(dx);
        int absDy = Math.abs(dy);

        int accretion = 0;

        if (absDx > absDy) {
            int y = y1;
            int direction = dy != 0 ? (dy > 0 ? 1 : -1) : 0;
            for (int x = x1; dx > 0 ? x <= x2 : x >= x2; x += (dx > 0 ? 1 : -1)) {
                pixelWriter.setColor(x, y, color);
                accretion += absDy;

                if (accretion >= absDx) {
                    accretion -= absDx;
                    y += direction;
                }
            }
        } else {
            int x = x1;
            int direction = dx != 0 ? (dx > 0 ? 1 : -1) : 0;
            for (int y = y1; dy > 0 ? y <= y2 : y >= y2; y += (dy > 0 ? 1 : -1)) {
                pixelWriter.setColor(x, y, color);
                accretion += (float) absDx / absDy;

                if (accretion >= absDy) {
                    accretion -= absDy;
                    x += direction;
                }
            }
        }
    }

    public static void BresenhamAlgorithmV2(final GraphicsContext graphicsContext, int x1, int y1, int x2, int y2, final Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int error = dx - dy;
        pixelWriter.setColor(x2, y2, color);

        while (x1 != x2 || y1 != y2) {
            pixelWriter.setColor(x1, y1, color);
            int error2 = error * 2;
            if (error2 > -dy) {
                error -= dy;
                x1 += sx;
            }
            if (error2 < dx) {
                error += dx;
                y1 += sy;
            }
        }
    }
}
