package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements;

import java.awt.*;

public class Home {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    public Home(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x1;
        int y1;
        int xCurr;
        int yCurr;

        g2d.setColor(Color.black);
        g2d.drawLine(x + width / 2, y, x + width / 2, (int) (y + height * 0.07));

        xCurr = x + width / 2;
        yCurr = (int) (y + height * 0.07);

        g2d.setColor(color);
        g2d.fillPolygon(new int[]{(int) (xCurr - width * 0.26), xCurr, (int) (xCurr + width * 0.26),}, new int[]{(int) (yCurr + height * 0.27), yCurr, (int) (yCurr + height * 0.27)}, 3);

        int length = (int) (width * 0.52) / 7;
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < 8; i++) {
            g2d.drawLine(xCurr, yCurr, (int) (xCurr - width * 0.26) + i * length, (int) (yCurr + height * 0.27));
        }
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((int) (xCurr - width * 0.26), (int) (yCurr + height * 0.27), (int) (xCurr + width * 0.26), (int) (yCurr + height * 0.27));
        yCurr = (int) (yCurr + height * 0.27);

        g2d.fillRect((int) (xCurr - width * 0.17), yCurr, (int) (width * 0.34), (int) (height * 0.1));
        yCurr = (int) (yCurr + height * 0.1);


        g2d.fillRect((int) (xCurr - width * 0.26), yCurr, (int) (width * 0.52), (int) (height * 0.04));
        drawFence(g, (int) (xCurr - width * 0.26), yCurr - (int) (height * 0.04), (int) (xCurr + width * 0.26), yCurr - (int) (height * 0.04), 11, 4);
        g2d.setColor(Color.WHITE);
        g2d.drawLine((int) (xCurr - width * 0.26), yCurr + 4, (int) (xCurr + width * 0.26), yCurr + 4);

        yCurr = (int) (yCurr + height * 0.04);

        g2d.setColor(color);
        g2d.fillPolygon(
                new int[]{(int) (xCurr - width * 0.26), (int) (xCurr + width * 0.26), (xCurr + width / 2), (xCurr - width / 2)},
                new int[]{yCurr, yCurr, (int) (yCurr + height * 0.15), (int) (yCurr + height * 0.15)},
                4);

        yCurr = (int) (yCurr + height * 0.15);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine((xCurr - width / 2), yCurr, (xCurr + width / 2), yCurr);

        g2d.fillRect((int) (xCurr - width * 0.28), yCurr, (int) (width * 0.56), (int) (height * 0.26));
//////////////////////////////////////
        drawDoor(g, (int) (xCurr - width * 0.115), (int) (yCurr + height * 0.085), (int) (width * 0.23), (int) (height * 0.17));


        yCurr = (int) (yCurr + height * 0.26);

        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.WHITE);
        g2d.drawLine((int) (xCurr - width * 0.28), yCurr, (int) (xCurr + width * 0.28), yCurr);

        drawFence(g2d, (int) (xCurr - width * 0.36), yCurr - 2, (int) (xCurr - width * 0.15), yCurr - 2, 3, (int) (0.1 * height));
        drawFence(g2d, (int) (xCurr + width * 0.15), yCurr - 2, (int) (xCurr + width * 0.36), yCurr - 2, 3, (int) (0.1 * height));

        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.black);
        g2d.drawLine((int) (xCurr - width * 0.36), yCurr + 2, (int) (xCurr + width * 0.36), yCurr + 2);

    }

    private void drawFence(Graphics g, int startX, int startY, int endX, int endY, int n, int l) {
        Graphics2D g2d = (Graphics2D) g;
        int widthStroke = (int) (width * 0.02);
        int widthStrokeSmall = (int) (width * 0.02 - 1);


        int length = (int) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) / (n - 1);
        for (int i = 0; i < n; i++) {
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(widthStroke != 0 ? widthStroke + 2 : 3));
            int curX = startX + i * length;
            g2d.drawLine(curX, startY, curX, startY - l + widthStroke);
            g2d.fillOval(curX - widthStroke, startY - l, 2 * widthStroke, 2 * widthStroke);

            if (i < n - 1) {
                g2d.setStroke(new BasicStroke(widthStrokeSmall != 0 ? widthStrokeSmall : 1));
                g2d.drawLine(curX, startY - l + widthStroke, curX + length, startY - l + widthStroke);
            }
            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(Color.WHITE);
            g2d.drawOval(curX - widthStroke, startY - l, 2 * widthStroke, 2 * widthStroke);


        }
        g2d.setStroke(new BasicStroke(widthStroke != 0 ? 1 : widthStrokeSmall));
    }

    private void drawDoor(Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, width, height);
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                g2d.drawRect(x + i * width / 2, y + j * height / 3, width / 2, height / 3);
            }
        }
    }
}
