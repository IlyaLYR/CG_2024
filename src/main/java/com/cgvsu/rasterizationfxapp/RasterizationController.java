package com.cgvsu.rasterizationfxapp;

import io.github.shimeoki.jfx.rasterization.geom.Point2f;
import io.github.shimeoki.jfx.rasterization.triangle.DDATriangler;
import io.github.shimeoki.jfx.rasterization.triangle.Triangler;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Polygon3;
import io.github.shimeoki.jfx.rasterization.triangle.geom.Triangle;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        test1(canvas.getGraphicsContext2D(), 60, 60, 20, 20);

        Rasterization.drawCircle(canvas.getGraphicsContext2D(), 60, 200, 40, Color.AQUA);

        Rasterization.drawCircleMitcher(canvas.getGraphicsContext2D(), 160, 200, 40, Color.RED);

        Rasterization.fillCircleMitcher(canvas.getGraphicsContext2D(), 260, 200, 40, Color.RED);

        Rasterization.drawOval(canvas.getGraphicsContext2D(), 400, 200, 30, 40, Color.RED);

        Rasterization.fillOval(canvas.getGraphicsContext2D(), 500, 200, 50, 40, Color.RED);


        Rasterization.fillOval(canvas.getGraphicsContext2D(), 700, 200, 50, 30,
                new Color[]{Color.RED, Color.BLUE, Color.YELLOW},
                new float[]{0.1f,0.5f, 0.9f});
    }

    public void test1(final GraphicsContext graphicsContext, int x, int y, int n, int l) {
        double da = 2 * Math.PI / n;
        for (int i = 0; i < n; i++) {
            double angle = i * da;
            double x1 = l * Math.cos(angle);
            double y1 = l * Math.sin(angle);
            double x2 = x1 + (l) * Math.cos(angle);
            double y2 = y1 + (l) * Math.sin(angle);
            Rasterization.drawLine(graphicsContext, (int) x1 + x, (int) y1 + y, (int) x2 + x, (int) y2 + y, Color.BLUE);
            Rasterization.drawLinePreBresenham(graphicsContext, (int) x1 + x + 100, (int) y1 + y, (int) x2 + x + 100, (int) y2 + y, Color.RED);
            Rasterization.BresenhamAlgorithmV2(graphicsContext, (int) x1 + x + 200, (int) y1 + y, (int) x2 + x + 200, (int) y2 + y, Color.BLACK);
        }
    }
}