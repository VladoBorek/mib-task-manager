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
        frame.setJMenuBar(createMenuBar());
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar(){

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));

        menuBar.add(createJMenu("File"));
        menuBar.add(createJMenu("Template"));
        menuBar.add((createJMenu("Categories")));
        menuBar.add((createJMenu("Time Units")));
        menuBar.add(createJMenu("Help"));

        return  menuBar;

    }
    private JMenu createJMenu(String name, Action ... actionList)
    {
        JMenu menu = new JMenu(name);
        menu.add("PLACEHOLDER_ACTION");
        for (Action a: actionList) {
            menu.add(a.toString());
        }

        return menu;
    }
    Priva
}
