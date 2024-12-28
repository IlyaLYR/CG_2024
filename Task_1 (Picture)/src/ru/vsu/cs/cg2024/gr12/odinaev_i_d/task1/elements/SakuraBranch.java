package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class SakuraBranch {
    private int x;
    private int y;
    private int branchLength;
    private Color flowerColor;
    private int rotationAngle;
    private int branchAngle; // Параметр для управления наклоном ветки


    public SakuraBranch(int x, int y, int branchLength, Color flowerColor, int rotationAngle, int branchAngle) {
        this.x = x;
        this.y = y;
        this.branchLength = branchLength;
        this.flowerColor = flowerColor;
        this.rotationAngle = rotationAngle;
        this.branchAngle = branchAngle; // Инициализируем параметр наклона
    }

    private void drawBranch(Graphics2D g2d, int x1, int y1, int length, double angle) {
        if (length < 10) {
            return; // Базовый случай, когда длина ветки слишком мала для продолжения
        }

        // Координаты конца основной ветки
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * length);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * length);

        // Рисуем основную ветку
        g2d.setStroke(new BasicStroke(3)); // ширина ветки
        g2d.setColor(new Color(101, 67, 33)); // Коричневый цвет для ветки
        g2d.drawLine(x1, y1, x2, y2);


        // Создаем маленькие веточки с цветами
        drawSmallBranchWithFlowers(g2d, x2, y2, length / 3, angle - 30); // Маленькая веточка слева
        drawSmallBranchWithFlowers(g2d, x2, y2, length / 3, angle + 30); // Маленькая веточка справа
        // цветок
        // Рекурсивное создание основного ответвления
        drawBranch(g2d, x2, y2, length - 15, angle + branchAngle);
        drawFlower(g2d, x2, y2, 12); // Правое ответвление
    }

    private void drawSmallBranchWithFlowers(Graphics2D g2d, int x1, int y1, int length, double angle) {
        // Координаты конца маленькой веточки
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * length);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * length);

        // Рисуем маленькую веточку
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(101, 67, 33)); // Коричневый цвет для веточки
        g2d.drawLine(x1, y1, x2, y2);

        // Рисуем цветок на конце маленькой веточки
        drawFlower(g2d, x2, y2, 12);
    }

    private void drawFlower(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(flowerColor);
        g2d.fillOval(x - size, y - size, size, size);
        g2d.fillOval(x, y - size, size, size);
        g2d.fillOval(x - size, y, size, size);
        g2d.fillOval(x, y, size, size);
        g2d.setColor(Color.YELLOW); // Цвет сердцевины цветка
        g2d.fillOval(x - 3, y - 3, 6, 6);
    }

    public void draw(Graphics2D g2d) {
        drawBranch(g2d, x, y, branchLength, rotationAngle);
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

    public int getBranchLength() {
        return branchLength;
    }

    public void setBranchLength(int branchLength) {
        this.branchLength = branchLength;
    }

    public Color getFlowerColor() {
        return flowerColor;
    }

    public void setFlowerColor(Color flowerColor) {
        this.flowerColor = flowerColor;
    }

    public int getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(int rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public int getBranchAngle() {
        return branchAngle;
    }

    public void setBranchAngle(int branchAngle) {
        this.branchAngle = branchAngle;
    }
}