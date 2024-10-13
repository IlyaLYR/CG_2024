package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class City {
    private int x;
    private int y;
    private int width;
    private int height;
    private int houseCount;
    private java.util.List<Color> colorList = new ArrayList<>();
    private java.util.List<Home> homeList = new ArrayList<>();
    private final Random random = new Random();

    public City(int x, int y, int width, int height, int houseCount, List<Color> colorList) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.houseCount = houseCount;
        this.colorList = colorList;

        for (int i = 0; i <houseCount ; i++) {
            int X = x + random.nextInt(width - 50);
            int Y = y + random.nextInt(height - 50);
            int Width = 100 + random.nextInt(10);
            int Height = 100+ random.nextInt(10);
            homeList.add(new Home(X,Y,Width,Height,colorList.get(random.nextInt(colorList.size()))));
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

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public List<Home> getHomeList() {
        return homeList;
    }

    public void setHomeList(List<Home> homeList) {
        this.homeList = homeList;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (Home h : homeList) {
            h.draw(g2d);
        }
    }
}
