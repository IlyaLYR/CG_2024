package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Sun {
    private int x;
    private int y;
    private int radius;
    private int rays;
    private int lengthRay;
    Color color;

    public Sun(int x, int y, int radius, int rays, int lengthRay, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.rays = rays;
        this.lengthRay = lengthRay;
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

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRays() {
        return rays;
    }

    public void setRays(int rays) {
        this.rays = rays;
    }

    public int getLengthRay() {
        return lengthRay;
    }

    public void setLengthRay(int lengthRay) {
        this.lengthRay = lengthRay;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics2D g) {
        Paint p =new RadialGradientPaint(x,y,radius, new float[]{0, 0.9f,1}, new Color[]{Color.decode("#8E2F2F"), color, Color.decode("#E38756")});
        g.setPaint(p);
        //Круг
        g.fillOval(x-radius, y-radius, 2*radius, 2*radius);

        //Лучики
        g.setPaint(new RadialGradientPaint(x,y, radius+lengthRay, new float[]{0, ((float) radius /(radius+lengthRay)) ,  1}, new Color[]{Color.decode("#8E2F2F"), Color.decode("#8E2F2F"),color}));
        double dealtAngle = 2 * Math.PI / rays;
        for (int i = 0; i < rays; i++) {
            double angle = i * dealtAngle;
            double x1 = radius * Math.cos(angle);
            double y1 = radius * Math.sin(angle);
            double x2 = x1 + (radius + lengthRay) * Math.cos(angle);
            double y2 = y1 + (radius + lengthRay) * Math.sin(angle);
            g.setStroke(new BasicStroke(2));
            g.drawLine((int) x1 + x, (int) y1 + y, (int) x2 + x, (int) y2 + y);
        }
    }
}
