package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1;

import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Sky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {
    Timer timer;
    private int ticksFromStart = 0;

    private Sky sky;
    //Поля

    public DrawPanel(int timerDelay) {
        timer = new Timer(timerDelay, this);
        timer.start();
        sky = new Sky(0,0,600,800,5, Color.BLUE);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        sky.draw(g2d);
    }
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
            ++ticksFromStart;
        }
    }

}
