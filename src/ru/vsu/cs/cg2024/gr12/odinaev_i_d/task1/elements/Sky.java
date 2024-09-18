package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Sky {
    private int x;
    private int y;
    private int width;
    private int height;
    private int countCloud;
    private Color color;

    public Sky(int x, int y, int width, int height, int countCloud, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.countCloud = countCloud;
        this.color = color;
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

    public int getCountCloud() {
        return countCloud;
    }

    public void setCountCloud(int countCloud) {
        this.countCloud = countCloud;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

    }
}
