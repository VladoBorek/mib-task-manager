package cz.muni.fi.pv168.project.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maroš Pavlík
 */
public class MainWindow {

    private final JFrame frame;
    public MainWindow() {
        frame = createFrame();

        frame.getContentPane().setBackground(new Color(180, 180, 180));

        frame.setJMenuBar(createMenuBar());
        frame.add(createFilterBar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.pack();
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("MIB Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

    /**
     * @return menuBar for the application
     */
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

    /**
     * @param name Name of the item for the JMenuBar
     * @param actionList Actions for the JMenu
     * @return JMenu with the name and actions
     */
    private JMenu createJMenu(String name, Action ... actionList)
    {
        JMenu menu = new JMenu(name);
        menu.add("PLACEHOLDER_ACTION");
        for (Action a: actionList) {
            menu.add(a);
        }

        return menu;
    }

    /**
     * @return Bar with filters for the application
     */
    private JToolBar createFilterBar(){
        JToolBar filterBar = new JToolBar();
        //Refactor for Checkbox Actions
        var filterToDo = new JCheckBox();
        filterToDo.setText("Show To-Do");
        filterToDo.setSelected(true);

        var filterInProgress = new JCheckBox();
        filterInProgress.setText("Show In-Progress");
        filterInProgress.setSelected(true);

        var filterComplete = new JCheckBox();
        filterComplete.setText("Show Complete");
        filterComplete.setSelected(true);

        var filterOnHold = new JCheckBox();
        filterOnHold.setText("Show On-Hold");
        filterOnHold.setSelected(true);

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
