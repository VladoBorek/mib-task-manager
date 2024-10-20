package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.actions.menu.*;
import cz.muni.fi.pv168.project.ui.model.CategoryCellRenderer;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskProgressBar;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.model.Task;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main application window for the MIB Task Manager.
 * Handles the creation and layout of the main frame.
 */
public class MainWindow {

    public static final Color BUTTON_COLOR = new Color(190, 190, 190);
    public static final Color BG_COLOR = new Color(180, 180, 180);

    private static final DemoDataGenerator DEMO_DATA = new DemoDataGenerator();

    private final JFrame frame;
    private final DatePicker datePicker = createDatePicker();
    private final JTable taskTable;
    private final TimeUnitListModel timeUnits = new TimeUnitListModel(new ArrayList<>());
    private final CategoryListModel categories = new CategoryListModel(new ArrayList<>(DEMO_DATA.getCategories()));
    private final TemplateListModel templates = new TemplateListModel(new ArrayList<>());

    /**
     * Constructor for MainWindow.
     * Initializes the main frame, sets the background color, size, and adds the menu bar and filter bar.
     */
    public MainWindow() {
        frame = createFrame();
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setSize(1024, 768);

        taskTable = createTaskTable(DEMO_DATA.getTasks());
        taskTable.setComponentPopupMenu(createTaskTablePopupMenu(taskTable));
        frame.setJMenuBar(createMenuBar());
        frame.add(createFilterBar(), BorderLayout.BEFORE_FIRST_LINE);
        frame.add(new JScrollPane(taskTable), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();

        timeUnits.addUnit(new CustomTimeUnit());
    }

    /**
     * Creates and configures the main JFrame.
     *
     * @return Configured JFrame instance.
     */
    private JFrame createFrame() {
        JFrame frame = new JFrame("MIB Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    /**
     * Makes the frame visible to the user.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * @return menuBar for the application
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));

        menuBar.add(createJMenu("File", new ImportAction(), new ExportAction()));
        //TODO Create TemplateListModel
        menuBar.add(createJMenu("Template",
                new AddAction(ActionType.TEMPLATE, taskTable, categories, timeUnits, templates, null),
                new ManageAction(ActionType.TEMPLATE, timeUnits, categories, templates, frame)));
        menuBar.add((createJMenu("Categories",
                new AddAction(ActionType.CATEGORY, taskTable, categories, timeUnits, templates, null),
                new ManageAction(ActionType.CATEGORY, timeUnits, categories, templates, frame))));
        menuBar.add((createJMenu("Time Units",
                new AddAction(ActionType.TIME_UNIT, taskTable, categories, timeUnits, templates, null),
                new ManageAction(ActionType.TIME_UNIT, timeUnits, categories, templates, frame))));
        menuBar.add(createJMenu("Help"));

        return  menuBar;
    }

    /**
     * Fills the JMenu with the provided Actions
     * @param name Name of the item for the JMenuBar
     * @param actionList Actions for the JMenu
     * @return JMenu with the name and actions
     */
    private JMenu createJMenu(String name, Action ... actionList) {
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
     * Creates application Toolbar
     * @return Toolbar with AddNewTask button and filters for the tasks
     */
    private JToolBar createFilterBar() {
        JToolBar filterBar = new JToolBar();
        filterBar.setFloatable(false);

        JCheckBox filterToDo = createFilterCheckbox("To-Do", true);
        JCheckBox filterInProgress = createFilterCheckbox("In-Progress", true);
        JCheckBox filterComplete = createFilterCheckbox("Completed", true);
        JCheckBox filterOnHold = createFilterCheckbox("On-Hold", true);

        JCheckBox filterOverdue = createFilterCheckbox("Filter Overdue", false);
        JCheckBox filterOverBudget = createFilterCheckbox("Filter Over budget", false);

        JComboBox<Object> categoryComboBox = createFilterComboBox(categories.toArray(),
                "--Category--");
        JComboBox<Object> assigneeComboBox = createFilterComboBox(DEMO_DATA.getEmployees().toArray(),
                "--Assignee--");
        JComboBox<Object> customerComboBox = createFilterComboBox(DEMO_DATA.getCustomers().toArray(),
                "--Customer--");

        Map<Boolean,List<JCheckBox>> resetValuesCheckboxes = Map.of(
                true,List.of(filterToDo, filterInProgress, filterComplete, filterOnHold),
                false, List.of(filterOverdue, filterOverBudget));

        Map<JComboBox<Object>, String> resetValuesComboBoxes = Map.of(
                categoryComboBox,"--Category--",
                assigneeComboBox, "--Assignee--",
                customerComboBox, "--Customer--"
        );
//        JButton addNewTaskButton = createButton("New Task", Icons.ADD_ICON,
//                new AddAction(ActionType.TASK, taskTable, categories, timeUnits, templates));

        JButton addNewTaskButton = createButton("New Task", Icons.ADD_ICON,
                new ChooseTemplateAction(taskTable, categories, timeUnits, templates, frame));


        JButton resetFiltersButton = createButton("Reset Filters", Icons.DELETE_ICON,
                new ResetFilterAction(resetValuesCheckboxes, resetValuesComboBoxes, datePicker));

        filterBar.add(addNewTaskButton);

        filterBar.addSeparator();

        filterBar.add(filterToDo);
        filterBar.add(filterInProgress);
        filterBar.add(filterComplete);
        filterBar.add(filterOnHold);

        filterBar.addSeparator();

        filterBar.add(filterOverdue);
        //TODO filterOverdue will filter overdue tasks date picked by datepicker
        JPanel datePickerPanel = new JPanel(new BorderLayout());
        datePickerPanel.setMaximumSize(new Dimension(150, 25));
        datePickerPanel.setPreferredSize(new Dimension(150, 25));
        datePickerPanel.add(datePicker, BorderLayout.CENTER);
        filterBar.add(datePickerPanel);

        filterBar.add(filterOverBudget);

        filterBar.addSeparator();

        filterBar.add(categoryComboBox);
        filterBar.add(assigneeComboBox);
        filterBar.add(customerComboBox);

        filterBar.addSeparator();
        filterBar.add(resetFiltersButton);
        return  filterBar;
    }

    /**
     * Creates a custom JCheckBox
     * @param checkBoxText Text of the checkbox
     * @param setSelected Default state of the checkbox
     * @return checkBox
     */
    private JCheckBox createFilterCheckbox(String checkBoxText, Boolean setSelected) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText(checkBoxText);
        checkBox.setSelected(setSelected);
        checkBox.setFocusPainted(false);
        //TODO add Action? somehow make the filters work
        return checkBox;
    }

    /**
     *
     * @param tasks Tasks for the table
     * @return Table with tasks
     */
    private JTable createTaskTable(List<Task> tasks) {
        var model = new TaskTableModel(tasks);
        var table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        var progressColumn = table.getColumnModel().getColumn(8);
        progressColumn.setCellRenderer(new TaskProgressBar());
        var categoryColumn = table.getColumnModel().getColumn(1);
        categoryColumn.setCellRenderer(new CategoryCellRenderer());


        return table;
    }

    /**
     *
     * @param buttonText Text to be shown on button
     * @param icon Icon for the button
     * @param a Action to be performed
     * @return Button with input characteristics
     */
    private JButton createButton(String buttonText, Icon icon, Action a) {
        var button = new JButton(buttonText, icon);
        button.addActionListener(a);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Creates a custom JComboBox
     * @param items Items for the comboBox
     * @param placeholderText Placeholder text to be shown
     * @return comboBox with input parameters
     */
    private JComboBox<Object> createFilterComboBox(Object [] items, String placeholderText) {
        JComboBox<Object> comboBox = new JComboBox<>(items);
        comboBox.setEditable(true);
        comboBox.setSelectedItem(placeholderText);
        comboBox.setEditable(false);
        comboBox.setMinimumSize(new Dimension(150, 400));
        comboBox.setMaximumSize(new Dimension(150, 100));
        return comboBox;
    }

    /**
     * Creates a new DatePicker for filtering overdue tasks
     * @return new {@link DatePicker}
     */
    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setPreferredSize(new Dimension(100, 30));
        datePicker.getComponentToggleCalendarButton().setPreferredSize(new Dimension(15, 15));
        datePicker.getComponentDateTextField().setPreferredSize(new Dimension(100, 25));
        datePicker.setDateToToday();
        return datePicker;
    }

    /**
     * Creates pop up menu for the task table
     * @param taskMenu JTable with content for edit
     * @return created menu
     */
    private JPopupMenu createTaskTablePopupMenu(JTable taskMenu) {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new EditAction(ActionType.TASK, taskMenu, categories, timeUnits, null));
        menu.add(new DeleteAction(ActionType.TASK, taskMenu, categories, timeUnits, templates, null));
        return menu;
    }

    public TimeUnitListModel getTimeUnits(){
        return this.timeUnits;
    }
}
