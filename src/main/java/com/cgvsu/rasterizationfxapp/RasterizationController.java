package com.cgvsu.rasterizationfxapp;

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

//        Rasterization.drawRectangle(canvas.getGraphicsContext2D(), 200, 300, 200, 100, Color.CHOCOLATE);
//        Rasterization.drawRectangle(canvas.getGraphicsContext2D(), 250, 250, 50, 200, Color.AQUA);

//        Rasterization.drawLine(canvas.getGraphicsContext2D(), 23,100,600,0, Color.CHOCOLATE);
//        Rasterization.drawLinePreBresenham(canvas.getGraphicsContext2D(), 23,100,600,0, Color.CHOCOLATE);


//        Rasterization.BresenhamAlgorithmV2(canvas.getGraphicsContext2D(), 0,0,10,10, Color.CHOCOLATE);
//
//        Rasterization.drawCircle(canvas.getGraphicsContext2D(), 50, 50, 50, Color.CHOCOLATE);
//
//
//        Rasterization.drawCircleMitcher(canvas.getGraphicsContext2D(), 100, 100, 100, Color.BLACK);
//
//        Rasterization.fillCircleMitcher(canvas.getGraphicsContext2D(), 100, 100, 100, Color.BLACK);

//       Rasterization.fillOval(canvas.getGraphicsContext2D(), 500,500,400,200, Color.BLACK);

        test1(canvas.getGraphicsContext2D(), 60, 60, 20, 20);

        Rasterization.drawCircle(canvas.getGraphicsContext2D(), 60, 200, 40, Color.AQUA);

        Rasterization.drawCircleMitcher(canvas.getGraphicsContext2D(), 160, 200, 40, Color.RED);

        Rasterization.fillCircleMitcher(canvas.getGraphicsContext2D(), 260, 200, 40, Color.RED);

        Rasterization.drawOval(canvas.getGraphicsContext2D(), 400, 200, 30, 40, Color.RED);

        Rasterization.fillOval(canvas.getGraphicsContext2D(), 500, 200, 30, 40, Color.RED);

        Rasterization.fillOval(canvas.getGraphicsContext2D(), 500, 200, 30, 40, Color.RED, Color.BLUE);

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
            Rasterization.BresenhamAlgorithm(graphicsContext, (int) x1 + x + 200, (int) y1 + y, (int) x2 + x + 200, (int) y2 + y, Color.GREEN);
            Rasterization.BresenhamAlgorithmV2(graphicsContext, (int) x1 + x + 300, (int) y1 + y, (int) x2 + x + 300, (int) y2 + y, Color.BLACK);
        }
    }
}