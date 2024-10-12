package cz.muni.fi.pv168.project.ui;

import javax.swing.*;
import java.awt.*;

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
        frame.add(createFilterBar(), BorderLayout.BEFORE_FIRST_LINE);
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
    private JToolBar createFilterBar(){
        JToolBar filterBar = new JToolBar();

        var filterToDo = new JCheckBox();
        filterToDo.setText("Show To-Do");

        var filterInProgress = new JCheckBox();
        filterInProgress.setText("Show In-Progress");

        var filterComplete = new JCheckBox();
        filterComplete.setText("Show Complete");

        var filterOnHold = new JCheckBox();
        filterOnHold.setText("Show On-Hold");

        var filterOverdue = new JCheckBox();
        filterOverdue.setText("Filter Tasks Overdue");

        var filterOverBudget = new JCheckBox();
        filterOverBudget.setText("Filter Tasks Over Budget");

        filterBar.add(filterToDo);
        filterBar.add(filterInProgress);
        filterBar.add(filterComplete);
        filterBar.add(filterOnHold);

        filterBar.addSeparator();

        filterBar.add(filterOverdue);
        filterBar.add(filterOverBudget);

        filterBar.addSeparator();
        return  filterBar;
    }
}
