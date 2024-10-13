package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Nature {
    final private Mountain mountain;
    final private Sun sun;
    final private Sky sky;
    final private City city;
    private int x;
    private int y;
    private int radius;

    public Nature(Mountain mountain, Sun sun, Sky sky, City city, int x, int y, int radius) {
        this.mountain = mountain;
        this.sun = sun;
        this.sky = sky;
        this.city = city;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Mountain getMountain() {
        return mountain;
    }

    public Sun getSun() {
        return sun;
    }

    public Sky getSky() {
        return sky;
    }

    public City getCity() {
        return city;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.decode("#E38756"));
        g2d.fillOval(x - radius,y - radius,2*radius,2*radius);
        sky.draw(g2d);
        sun.draw(g2d);
        mountain.draw(g2d);
        city.draw(g2d);
    }
}