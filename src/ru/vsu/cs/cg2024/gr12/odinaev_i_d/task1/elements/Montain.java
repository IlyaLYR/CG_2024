package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Montain {
    private int x;
    private int y;
    private int width;
    private int height;

    /**
     * Конструктор горы Фудзияма
     * @param x абсцисса правого нижнего угол
     * @param y ордината правого нижнего угла
     * @param width ширина горы
     * @param height высота горы
     */
    public Montain(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;


        g2d.setColor(Color.GRAY);

        int[] xPoints = {x, x, x + width / 3, x + width};
        int[] yPoints = {y, (y - height / 2), y - height, y};


//        for (int i = 0; i < ; i++) {
//
//        }

        g2d.fillPolygon(xPoints, yPoints, 4);

        g2d.setColor(Color.BLACK); //TODO "Доработать узор"

        int SIZE = 100;
        int[] x = new int[SIZE + 2];
        int[] y = new int[SIZE + 2];
        for (int i = 0; i < SIZE; i++) {
            x[i + 1] = i * 6;
            if (i % 3 == 1) {
                y[i + 1] = (int) (40 * Math.sin(14 * i) * Math.cos(67 * i)) + 635 + (int)(5*Math.sin(i));
            } else {
                y[i + 1] = (int) (40 * Math.sin(14 * i) * Math.cos(67 * i)) + 635;
            }
        }

        x[0] = getX();
        y[0] = getY();
        x[SIZE + 1] = getX() + width;
        y[SIZE + 1] = getY();

        g2d.setColor(Color.decode("#3B4582"));
        g2d.fillPolygon(x, y, SIZE + 2);
    }
}
