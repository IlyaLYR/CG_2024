package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1;

import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Home;
import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Mountain;
import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Sky;
import ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1.elements.Sun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawPanel extends JPanel implements ActionListener {
    Timer timer;
    //Поля - объекты на рисунке
    private final Sky sky;
    private final Sun sun;
    private final Mountain mountain;
    private final Home home;


    public DrawPanel(int timerDelay) {
        timer = new Timer(timerDelay, this);
        home = new Home(950,10,500,500, Color.RED);
        timer.start();
        sky = new Sky(0, 0, 600, 300, 5, Color.WHITE);
        mountain = new Mountain(500, 950, 1500, 500);//new Montain(-150, 800, 850, 500);
        sun = new Sun(270, 325, 135, 0, 1, Color.decode("#BA3E3E"));
    }

    @Override
    public void paint(Graphics g) {
        setBackground(Color.decode("#E38756"));
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        sky.setWidth(getWidth());
        sky.draw(g2d);
        sun.draw(g2d);
        mountain.draw(g2d);
        home.draw(g2d);

    }

    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == timer) {
            repaint();
        }
    }
}
