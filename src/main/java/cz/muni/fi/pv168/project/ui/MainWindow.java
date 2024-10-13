package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.ui.actions.menu.*;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;

/**
 * @author Maroš Pavlík
 */
public class MainWindow {

    private final JFrame frame;
    private final Color BUTTON_COLOR = new Color(190, 190, 190);
    private final Color BG_COLOR = new Color(180, 180, 180);
    public MainWindow() {
        frame = createFrame();

        frame.getContentPane().setBackground(BG_COLOR);

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

        menuBar.add(createJMenu("File", new ImportAction(), new ExportAction()));
        menuBar.add(createJMenu("Template", new AddAction(Type.TEMPLATE), new ManageAction(Type.TEMPLATE)));
        menuBar.add((createJMenu("Categories",new AddAction(Type.CATEGORY) , new ManageAction(Type.CATEGORY))));
        menuBar.add((createJMenu("Time Units", new AddAction(Type.TIME_UNIT), new ManageAction(Type.TIME_UNIT))));
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
        if (actionList.length == 0) {
            menu.add("PLACEHOLDER_ACTION");
        }
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

        //TODO Refactor for better looking code
        var filterToDo = new JCheckBox();
        filterToDo.setText("Show To-Do");
        filterToDo.setSelected(true);
        filterToDo.setFocusPainted(false);

        var filterInProgress = new JCheckBox();
        filterInProgress.setText("Show In-Progress");
        filterInProgress.setSelected(true);
        filterInProgress.setFocusPainted(false);

        var filterComplete = new JCheckBox();
        filterComplete.setText("Show Complete");
        filterComplete.setSelected(true);
        filterComplete.setFocusPainted(false);

        var filterOnHold = new JCheckBox();
        filterOnHold.setText("Show On-Hold");
        filterOnHold.setSelected(true);
        filterOnHold.setFocusPainted(false);

        var filterOverdue = new JCheckBox();
        filterOverdue.setText("Filter Tasks Overdue");
        filterOverdue.setFocusPainted(false);

        var filterOverBudget = new JCheckBox();
        filterOverBudget.setText("Filter Tasks Over Budget");
        filterOverBudget.setFocusPainted(false);

        var addNewTaskButton = new JButton("Add New Task  ", Icons.ADD_ICON);
        addNewTaskButton.addActionListener(new AddAction(Type.TASK));
        addNewTaskButton.setBackground(BUTTON_COLOR);
        addNewTaskButton.setFocusPainted(false);

        var resetFIlterButton = new JButton("Reset Filters", Icons.DELETE_ICON);
        //resetFIlterButton.addActionListener(somehowResetFilters);
        resetFIlterButton.setBackground(BUTTON_COLOR);
        resetFIlterButton.setFocusPainted(false);

        filterBar.add(addNewTaskButton);
        filterBar.addSeparator();

        filterBar.add(filterToDo);
        filterBar.add(filterInProgress);
        filterBar.add(filterComplete);
        filterBar.add(filterOnHold);

        filterBar.addSeparator();

        filterBar.add(filterOverdue);
        filterBar.add(filterOverBudget);

        filterBar.addSeparator();

        filterBar.add(resetFIlterButton);
        return  filterBar;
    }
}
