package cz.muni.fi.pv168.project.ui;

import javax.swing.*;
import java.awt.Color;

/**
 * @author Maroš Pavlík
 */
public class MainWindow {

    private final JFrame frame;

    public MainWindow() {
        frame = this.createFrame();
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("MIB Task Manager");

        frame.setResizable(false);
        frame.setSize(1000, 600);
        frame.getContentPane().setBackground(new Color(180, 180, 180));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

}
