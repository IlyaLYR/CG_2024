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

    public static void drawCircle(final GraphicsContext graphicsContext, int x0, int y0, int radius, Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int x = 0;
        int y = radius;
        int delta = 1 - 2 * radius;
        int error = 0;

        while (y >= 0) {
            pixelWriter.setColor(x0 + x, y0 + y, color);
            pixelWriter.setColor(x0 + x, y0 - y, color);
            pixelWriter.setColor(x0 - x, y0 + y, color);
            pixelWriter.setColor(x0 - x, y0 - y, color);
            error = 2 * (delta + y) - 1;
            if (delta < 0 && error <= 0) {
                ++x;
                delta += 2 * x + 1;
                continue;
            }
            if (delta > 0 && error > 0) {
                --y;
                delta += 1 - 2 * y;
                continue;
            }
            ++x;
            delta += 2 * (x - y);
            --y;
        }
    }

    public static void drawCircleMitcher(final GraphicsContext graphicsContext, int x0, int y0, int radius, Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int x = 0;
        int y = radius;
        int error = 3 - 2 * y;
        while (x <= y) {
            pixelWriter.setColor(x0 + x, y0 + y, color);
            pixelWriter.setColor(x0 + x, y0 - y, color);
            pixelWriter.setColor(x0 - x, y0 - y, color);
            pixelWriter.setColor(x0 - x, y0 + y, color);
            pixelWriter.setColor(x0 + y, y0 + x, color);
            pixelWriter.setColor(x0 + y, y0 - x, color);
            pixelWriter.setColor(x0 - y, y0 - x, color);
            pixelWriter.setColor(x0 - y, y0 + x, color);

            if (error < 0) {
                error = error + 4 * x + 6;
            } else {
                error = error + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }

    }

    public static void fillCircleMitcher(final GraphicsContext graphicsContext, int x0, int y0, int radius, Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int x = 0;
        int y = radius;
        int error = 3 - 2 * radius;

        while (x <= y) {
            for (int i = x0 - x; i <= x0 + x; i++) {
                pixelWriter.setColor(i, y0 + y, color);
                pixelWriter.setColor(i, y0 - y, color);
            }
            for (int i = x0 - y; i <= x0 + y; i++) {
                pixelWriter.setColor(i, y0 + x, color);
                pixelWriter.setColor(i, y0 - x, color);
            }

            if (error < 0) {
                error = error + 4 * x + 6;
            } else {
                error = error + 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }

    public static void drawOval(final GraphicsContext graphicsContext, int x0, int y0, int a, int b, Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int x = 0;
        int y = b;
        int error = 0;

        while (b * b * x < a * a * y) {
            drawingOval(pixelWriter, x0, y0, x, y, color);
            error = b * b * (x + 1) * (x + 1) + a * a * y * (y - 1) - a * a * b * b;
            x = x + 1;
            if (error >= 0) {
                y = y - 1;
            }
        }
        while (y >= 0) {
            drawingOval(pixelWriter, x0, y0, x, y, color);
            error = b * b * x * (x + 1) + a * a * (y - 1) * (y - 1) - a * a * b * b;
            if (error < 0) {
                x = x + 1;
            }
            y = y - 1;
        }
    }

    private static void drawingOval(PixelWriter pixelWriter, int x0, int y0, int x, int y, Color color) {
        pixelWriter.setColor(x0 + x, y0 + y, color);
        pixelWriter.setColor(x0 - x, y0 + y, color);
        pixelWriter.setColor(x0 + x, y0 - y, color);
        pixelWriter.setColor(x0 - x, y0 - y, color);
    }

    private static void fillingOval(PixelWriter pixelWriter, int x0, int y0, int x, int y, Color color) {
        for (int i = x0 - x; i <= x0 + x; i++) {
            pixelWriter.setColor(i, y0 + y, color);
            pixelWriter.setColor(i, y0 - y, color);
        }
    }

    public static void fillOval(final GraphicsContext graphicsContext, int x0, int y0, int a, int b, Color color) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int x = 0;
        int y = b;
        int error = 0;

        while (b * b * x < a * a * y) {
            fillingOval(pixelWriter, x0, y0, x, y, color);
            error = b * b * (x + 1) * (x + 1) + a * a * y * (y - 1) - a * a * b * b;
            x = x + 1;
            if (error >= 0) {
                y = y - 1;
            }
        }
        while (y >= 0) {
            fillingOval(pixelWriter, x0, y0, x, y, color);
            error = b * b * x * (x + 1) + a * a * (y - 1) * (y - 1) - a * a * b * b;
            if (error < 0) {
                x = x + 1;
            }
            y = y - 1;
        }
    }

    private static void fillingOval2V(PixelWriter pixelWriter, int x0, int y0, int x, int y, int a, int b, Color[] colors, float[] interval) {

        for (int i = x0 - x; i <= x0 + x; i++) {
            double dist = Math.sqrt(Math.pow(i - x0, 2) + Math.pow(y, 2));
            double max = (a * b) / Math.sqrt(a * a * Math.pow(y / dist, 2) + b * b * Math.pow((i - x0) / dist, 2));
            double fraction = dist / max;


            Color color = colors[colors.length - 1];


            for (int j = 1; j < interval.length; j++) {
                if (fraction <= interval[j]) {
                    if (interval[j] == interval[j - 1]) {
                        color = colors[j - 1];
                    } else {
                        // Расчет доли для текущего интервала
                        double distInInterval = fraction - interval[j - 1];
                        double maxInInterval = interval[j] - interval[j - 1];
                        double normalizedFraction = distInInterval / maxInInterval;
                        color = interpolateColor(colors[j - 1], colors[j], normalizedFraction);
                    }
                    break;
                }
            }


            pixelWriter.setColor(i, y0 + y, color);
            pixelWriter.setColor(i, y0 - y, color);
        }
    }


    public static void fillOval(final GraphicsContext graphicsContext, int x0, int y0, int a, int b, Color[] colors, float[] interval) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        int x = 0;
        int y = b;
        int error = 0;

        while (b * b * x < a * a * y) {
            fillingOval2V(pixelWriter, x0, y0, x, y, a, b, colors, interval);
            error = b * b * (x + 1) * (x + 1) + a * a * y * (y - 1) - a * a * b * b;
            x = x + 1;
            if (error >= 0) {
                y = y - 1;
            }
        }
        while (y >= 0) {
            fillingOval2V(pixelWriter, x0, y0, x, y, a, b, colors, interval);
            error = b * b * x * (x + 1) + a * a * (y - 1) * (y - 1) - a * a * b * b;
            if (error < 0) {
                x = x + 1;
            }
            y = y - 1;
        }
        pixelWriter.setColor(x0, y0, colors[0]);
    }

    // Метод для линейной интерполяции между двумя цветами с заданием параметра удаленности
    private static Color interpolateColor(Color startColor, Color endColor, double fraction) {
        double red = startColor.getRed() + (endColor.getRed() - startColor.getRed()) * fraction;
        double green = startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * fraction;
        double blue = startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * fraction;
        double opacity = startColor.getOpacity() + (endColor.getOpacity() - startColor.getOpacity()) * fraction;

        red = Math.max(0, Math.min(1, red));
        green = Math.max(0, Math.min(1, green));
        blue = Math.max(0, Math.min(1, blue));
        return new Color(red, green, blue, opacity);
    }
}