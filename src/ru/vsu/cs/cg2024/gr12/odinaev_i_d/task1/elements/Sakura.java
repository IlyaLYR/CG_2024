package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sakura {
    private int x;
    private int y;
    private int width;
    private int height;
    private int branchCount;
    private List<SakuraBranch> branchList = new ArrayList<SakuraBranch>();
    private final Random random = new Random();

    public Sakura(int x, int y, int width, int height, int branchCount) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.branchCount = branchCount;
        for (int i = 0; i < branchCount; i++) {

            int X = i % 2 == 0 ? x + 50 : x + width - 50;
            int Y = random.nextInt(y, height);
            int branchLength = random.nextInt(80, 145);
            Color color = Color.PINK;
            int rotationAngle = i % 2 == 0 ? -random.nextInt(30, 45) : 90+random.nextInt(30, 45);
            int branchAngle = random.nextInt(10, 25);
            branchList.add(new SakuraBranch(X, Y, branchLength, color, rotationAngle, branchAngle));
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

    public int getBranchCount() {
        return branchCount;
    }

    public void setBranchCount(int branchCount) {
        this.branchCount = branchCount;
    }

    public List<SakuraBranch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SakuraBranch> branchList) {
        this.branchList = branchList;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (SakuraBranch branch : branchList) {
            branch.draw(g2d);
        }
    }
}
