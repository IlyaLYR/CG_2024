package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Cloud {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int speed;

    /**
     * Конструктор облака
     * @param x абсцисса левого верхнего угла
     * @param y ордината левого верхнего угла
     * @param width ширина облака
     * @param height высота облака
     * @param speed скорость движения облака
     * @param color цвет облака
     */
    public Cloud(int x, int y, int width, int height, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Нарисовать облако
     * @param g ГРАФИКА
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, width, height);
        g2d.drawOval(x + width / 4, y - height / 4, width, height);
        g2d.drawOval(x + width / 2, y, width, height);
        g2d.drawOval(x + width / 4, y + height / 4, width, height);
        g2d.setColor(color);
        g2d.fillOval(x, y, width, height);
        g2d.fillOval(x + width / 4, y - height / 4, width, height);
        g2d.fillOval(x + width / 2, y, width, height);
        g2d.fillOval(x + width / 4, y + height / 4, width, height);
    }
}
