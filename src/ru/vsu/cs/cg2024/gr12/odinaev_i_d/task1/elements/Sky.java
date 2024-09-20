package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Sky {
    private int x;
    private int y;
    private int width;
    private int height;
    private int countCloud;
    private Color color;
    private java.util.List<Cloud> cloudsList = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Конструктор неба!
     * @param x абсцисса левого верхнего угла
     * @param y ордината левого верхнего угла
     * @param width ширина неба
     * @param height высота неба
     * @param countCloud количество облаков
     * @param color цвет облаков
     */
    public Sky(int x, int y, int width, int height, int countCloud, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.countCloud = countCloud;
        this.color = color;

        for (int i = 0; i < countCloud; i++) {
            int cloudX = x + random.nextInt(width - 50);
            int cloudY = y + random.nextInt(height - 50);
            int cloudWidth = 50 + random.nextInt(50);
            int cloudHeight = 20 + random.nextInt(20);
            int cloudSpeed = random.nextInt(1, 10);

            cloudsList.add(new Cloud(cloudX, cloudY, cloudWidth, cloudHeight, cloudSpeed, color));
        }
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

    public List<Cloud> getCloudsList() {
        return cloudsList;
    }

    public void setCloudsList(List<Cloud> cloudsList) {
        this.cloudsList = cloudsList;
    }

    /**
     * Рандомная генерация параметров облака
     */
    private Cloud recreateCloud() {
        int cloudWidth = 50 + random.nextInt(50);
        int cloudHeight = 20 + random.nextInt(20);
        int cloudSpeed = random.nextInt(1, 10);
        int cloudX = -cloudWidth - 50 + cloudSpeed;
        int cloudY = y + random.nextInt(height - 50);

        return new Cloud(cloudX, cloudY, cloudWidth, cloudHeight, cloudSpeed, color);
    }

    /**
     * Нарисовать небо
     * @param g - переменная ГРАФИК
     */
    public void draw(Graphics2D g) {
        for (int i = 0; i < countCloud; i++) {
            Cloud cloud = cloudsList.get(i);
            cloud.setX(cloud.getX() + cloud.getSpeed());
            if (cloud.getX() >= width) {
                cloudsList.set(i, recreateCloud());
            }
            cloud.draw(g);
        }
    }
}
