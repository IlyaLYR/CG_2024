package ru.vsu.cs.cg2024.gr12.odinaev_i_d.task1;

import javax.swing.*;

public class MainFrame extends JDialog {
    private JPanel contentPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel navigatBar;
    private JPanel panelArea;
    DrawPanel dp = new DrawPanel();

    public MainFrame()  {
        setContentPane(contentPanel);
        setTitle("Task 1");
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        panelArea.add(dp);
    }

    public static void main(String[] args) {
        MainFrame dialog = new MainFrame();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
