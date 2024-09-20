package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1;

import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Mountain;
import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Sky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {
    Timer timer;

    private final Sky sky;
    private final Mountain mountain;
    //Поля

    public DrawPanel(int timerDelay) {
        timer = new Timer(timerDelay, this);
        timer.start();
        sky = new Sky(0,0,600,300,5, Color.WHITE);
        mountain = new Mountain(-150, 800, 850, 500); //new Montain(-150, 800, 850, 500);
    }
    @Override
    public void paint(Graphics g) {
        setBackground(Color.decode("#E38756"));
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        sky.setWidth(getWidth());
        sky.draw(g2d);
        mountain.draw(g2d);
    }
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }

}
