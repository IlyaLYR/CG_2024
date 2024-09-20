package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Mountain {
    private int x;
    private int y;
    private int width;
    private int height;
    private final double[] l1;
    private final double[] l2;
    private final double[] l3;

    /**
     * Конструктор горы Фудзияма
     * @param x абсцисса правого нижнего угол
     * @param y ордината правого нижнего угла
     * @param width ширина горы
     * @param height высота горы
     */
    public Mountain(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        l1 = linEquation(x, y, x + width / 2, y - height);
        l2 = linEquation(x + width / 2, y - height, x + width, y);
        l3 = linEquation(x + width, y, x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double[] getL1() {
        return l1;
    }

    public double[] getL2() {
        return l2;
    }

    public double[] getL3() {
        return l3;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.setColor(Color.GRAY);

//        int[] xPoints = {x, x + width / 2, x + width};
//        int[] yPoints = {y, y - height, y};

        int upBoard = (int)(y - height*0.9);
        int[] yPoints = {y, upBoard, upBoard, y};
        int[] xPoints = {x, (int) ((upBoard - l1[1]) / l1[0]), (int) ((upBoard - l2[1]) / l2[0]), x + width};
        //Контур горы
        g2d.fillPolygon(xPoints, yPoints, 4);

        int startX = (int) (((y - height / 3.0) - l1[1]) / l1[0]);
        int endX = (int) (((y - height / 3.0) - l2[1]) / l2[0]);
        int SIZE = (endX - startX) / 6;
        int[] xCord = new int[SIZE + 2];
        int[] yCord = new int[SIZE + 2];
        for (int i = 1; i < SIZE + 1; i++) {
            xCord[i] = i * 6 + startX;
            yCord[i] = (int) (height * 0.03 * Math.sin(5 * i) * Math.cos(6 * i)) + y - height / 3;
        }
        xCord[SIZE + 1] = x + width;
        yCord[SIZE + 1] = y;
        xCord[0] = x;
        yCord[0] = y;
        g2d.setColor(Color.decode("#3B4582"));
        g2d.fillPolygon(xCord, yCord, SIZE + 2);

        //TODO Снег


    }

    protected double[] linEquation(int x1, int y1, int x2, int y2) {
        double k = (double) (y1 - y2) / (x1 - x2);
        double b = y1 - k * x1;
        return new double[]{k, b};
    }
}
