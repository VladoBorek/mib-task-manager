package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.data.DemoDataGenerator;
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
    private final DemoDataGenerator DEMO_DATA = new DemoDataGenerator();
    public MainWindow() {
        frame = createFrame();
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setSize(1500, 800);
        frame.setJMenuBar(createMenuBar());
        frame.add(createFilterBar(), BorderLayout.BEFORE_FIRST_LINE);

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
        filterBar.setFloatable(false);

        JCheckBox filterToDo = createFilterCheckbox("Show To-Do", true);
        JCheckBox filterInProgress = createFilterCheckbox("Show In-Progress", true);
        JCheckBox filterComplete = createFilterCheckbox("Show Completed", true);
        JCheckBox filterOnHold = createFilterCheckbox("Show On-Hold", true);;

        JCheckBox filterOverdue = createFilterCheckbox("Filter Overdue", false);
        JCheckBox filterOverBudget = createFilterCheckbox("Filter Over budget", false);

        JButton addNewTaskButton = createButton("Add New Task ", Icons.ADD_ICON, new AddAction(Type.TASK));
        //TODO create new reset filter action, will probably happen after creation of the table
        JButton resetFiltersButton = createButton("Reset Filters ", Icons.DELETE_ICON, new AddAction(Type.TASK));

        JComboBox<Object> categoryComboBox = createFilterComboBox(DEMO_DATA.getCategories().toArray(), "--Filter by category--");
        JComboBox<Object> assigneeComboBox = createFilterComboBox(DEMO_DATA.getEmployees().toArray(), "--Filter by assignee--");
        JComboBox<Object> customerComboBox = createFilterComboBox(DEMO_DATA.getCustomers().toArray(), "--Filter by customer--");


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

        filterBar.add(categoryComboBox);
        filterBar.add(assigneeComboBox);
        filterBar.add(customerComboBox);

        //TODO Date picker to filter by due date
        filterBar.addSeparator();
        filterBar.add(resetFiltersButton);
        return  filterBar;
    }

    /**
     *
     * @param checkBoxText Text of the checkbox
     * @param setSelected Default state of the checkbox
     * @return checkBox
     */
    private JCheckBox createFilterCheckbox(String checkBoxText, Boolean setSelected){
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText(checkBoxText);
        checkBox.setSelected(setSelected);
        checkBox.setFocusPainted(false);
        //TODO add Action? somehow make the filters work
        return checkBox;
    }

    /**
     *
     * @param buttonText Text to be shown on button
     * @param icon Icon for the button
     * @param a Action to be performed
     * @return Button with input characteristics
     */
    private JButton createButton(String buttonText, Icon icon, Action a)
    {
        var button = new JButton(buttonText, icon);
        button.addActionListener(a);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }

    /**
     *
     * @param items Items for the comboBox
     * @param placeholderText Placeholder text to be shown
     * @return comboBox with input parameters
     */
    private JComboBox<Object> createFilterComboBox(Object [] items, String placeholderText)
    {
        JComboBox<Object> comboBox = new JComboBox<>(items);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(placeholderText);
        comboBox.setEditable(false);
        comboBox.setMinimumSize(new Dimension(150, 400));
        comboBox.setMaximumSize(new Dimension(150, 100));
        return comboBox;
    }
}
