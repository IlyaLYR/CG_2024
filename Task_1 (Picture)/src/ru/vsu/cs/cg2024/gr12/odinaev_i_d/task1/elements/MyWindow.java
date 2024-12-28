package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import javax.swing.*;
import java.awt.*;

public class MyWindow {
    private int x;
    private int y;
    private int width;
    private int height;
    private final int radius;

    public MyWindow(int x, int y, int width, int height, int radius) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.radius = radius;
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

        g2d.setStroke(new BasicStroke(200));
        g2d.setColor(Color.decode("#65594B"));
        g2d.drawOval(x-radius-100,y-radius-100,radius*2+200,radius*2+200);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(x-radius,y-radius,radius*2,radius*2);
        g2d.drawLine(0,800, 1900, 800);


        g2d.setColor(Color.decode("#C74848"));
        g2d.fillRect(0, 800, width, height);

        g2d.setColor(Color.decode("#A99e81"));
        g2d.fillPolygon(new int[]{x-radius,(int)(x-0.8*radius), (int)(x+0.8*radius),x+ radius}, new int[]{1000,800,800,1000}, 4);

        Image img1 = new ImageIcon("./electro.png").getImage();
        Image img2 = new ImageIcon("./Inadzuma.png").getImage();
        g.drawImage(img1, 25, 25, 75, 75, null);
        g.drawImage(img2, 125, 25, 100, 100, null);


    }
}
