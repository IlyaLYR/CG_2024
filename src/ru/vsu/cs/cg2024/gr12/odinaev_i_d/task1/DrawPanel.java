package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1;

import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DrawPanel extends JPanel implements ActionListener {
    Timer timer;
    //Поля - объекты на рисунке
    MyWindow window;
    Nature nature;
    SakuraBranch sakura;


    public DrawPanel(int timerDelay) {
        Nature nature;
        timer = new Timer(timerDelay, this);
        timer.start();
        this.nature = new Nature(new Mountain(550, 800, 800, 400),
                new Sun(950, 300, 135, 0, 1, Color.decode("#BA3E3E")),
                new Sky(600, 200, 1350, 300, 5, Color.WHITE),
                new Sakura(500,300,900,500,10),
                new City(650,650,600,100,10,new ArrayList<>(Arrays.asList(new Color[]{Color.blue, Color.CYAN, Color.RED}))),
                950,500,400);
        window = new MyWindow(950,500,1900,500,400);

    }

    @Override
    public void paint(Graphics g) {
        setBackground(Color.decode("#65594B"));
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        nature.draw(g2d);
        window.draw(g2d);

    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }
}
