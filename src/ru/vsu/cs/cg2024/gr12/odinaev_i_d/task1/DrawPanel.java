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


    public DrawPanel(int timerDelay) {
        Nature nature;
        timer = new Timer(timerDelay, this);
        timer.start();
        this.nature = new Nature(new Mountain(550, 800, 800, 400),
                new Sun(950, 300, 135, 0, 1, Color.decode("#BA3E3E")),
                new Sky(600, 200, 1300, 300, 5, Color.WHITE),
                new City(600,650,600,100,5,new ArrayList<>(Arrays.asList(new Color[]{Color.BLACK,Color.blue, Color.CYAN, Color.RED, Color.RED}))),
                950,500,400);
//        home = new Home(950,10,500,500, Color.RED);
//        sky = new Sky(0, 0, 600, 300, 5, Color.WHITE);
//        mountain = new Mountain(500, 950, 1500, 500);//new Montain(-150, 800, 850, 500);
//        sun = new Sun(270, 325, 135, 0, 1, Color.decode("#BA3E3E"));
        window = new MyWindow(950,500,1900,500,400);
//        city = new City(60,60,500,500,6,new ArrayList<>(Arrays.asList(new Color[]{Color.BLACK,Color.blue, Color.CYAN, Color.RED, Color.RED})));


    }

    @Override
    public void paint(Graphics g) {
        setBackground(Color.decode("#65594B"));
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
//        sky.setWidth(getWidth());
//        sky.draw(g2d);
//        sun.draw(g2d);
//        mountain.draw(g2d);
//        home.draw(g2d);
        nature.draw(g2d);
        window.draw(g2d);
    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }
}
