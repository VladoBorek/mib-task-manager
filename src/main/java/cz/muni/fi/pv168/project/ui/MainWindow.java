package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.actions.menu.*;
import cz.muni.fi.pv168.project.ui.model.CategoryCellRenderer;

import cz.muni.fi.pv168.project.ui.model.EmployeeComboboxRenderer;
import cz.muni.fi.pv168.project.ui.model.StatisticsTableModel;

import cz.muni.fi.pv168.project.ui.model.TaskProgressBar;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.business.model.Task;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Main application window for the MIB Task Manager.
 * Handles the creation and layout of the main frame.
 */
public class MainWindow {

    public static final Color BUTTON_COLOR = new Color(220, 220, 220);
    public static final DemoDataGenerator DEMO_DATA = new DemoDataGenerator();

    private final JFrame frame;
    private final DataManager data;

    private JButton newSomethingButton;

    /**
     * Constructor for MainWindow.
     * Initializes the main frame, sets the background color, size, and adds the menu bar and filter bar.
     */
    public MainWindow(User loggedUser) {
        frame = createFrame();
        frame.setIconImage(Icons.APP_ICON.getImage());
        frame.setSize(1024, 768);

        data = new DataManager(loggedUser);

        Repository<Task> taskRepository = new InMemoryRepository<>(DEMO_DATA.getTasks());
        CrudService<Task> taskCrudService = new BaseCrudService<>(taskRepository);

        Repository<Template> templateRepository = new InMemoryRepository<>(new ArrayList<>());
        CrudService<Template> templateCrudService = new BaseCrudService<>(templateRepository);

        var taskTable = createTaskTable(taskCrudService);
        taskTable.setComponentPopupMenu(createTaskTablePopupMenu());


        var templateTable = createTemplateTable(templateCrudService);

        var statisticsTable = createStatisticsTable();

        data.setTaskTable(taskTable);
        data.setTemplateTable(templateTable);

        frame.setJMenuBar(createMenuBar());

        var filterBar = createFilterBar();

        frame.add(filterBar, BorderLayout.BEFORE_FIRST_LINE);

        var splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(10);
        splitPane.setTopComponent(new JScrollPane(taskTable));
        splitPane.setBottomComponent(new JScrollPane(statisticsTable));
        splitPane.setResizeWeight(0.85);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tasks", splitPane);
        tabbedPane.addTab("Templates", new JScrollPane(templateTable));

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                String selectedTabTitle = tabbedPane.getTitleAt(selectedIndex);
                filterBar.remove(newSomethingButton);

                if ("Templates".equals(selectedTabTitle)) {
                    newSomethingButton = createButton("Template ", Icons.ADD_ICON,
                            new AddAction(ActionType.TEMPLATE, data, null));
                }
                if ("Tasks".equals(selectedTabTitle)){
                    newSomethingButton = createButton("New Task ", Icons.ADD_ICON,
                            new ChooseTemplateAction(data, frame));
                }

                filterBar.add(newSomethingButton, 0);
                filterBar.revalidate();
                filterBar.repaint();
            }
        });


        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        setUpTaskInspect(taskTable);
        frame.setSize(1024, 768);


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

        menuBar.add(createJMenu("File", new ImportAction(data), new ExportAction(data)));
        //TODO Create TemplateListModel
        menuBar.add(createJMenu("Template",
                new AddAction(ActionType.TEMPLATE, data, null),
                new ManageAction(ActionType.TEMPLATE, data, frame)));
        menuBar.add((createJMenu("Categories",
                new AddAction(ActionType.CATEGORY, data, null),
                new ManageAction(ActionType.CATEGORY, data, frame))));
        menuBar.add((createJMenu("Time Units",
                new AddAction(ActionType.TIME_UNIT, data, null),
                new ManageAction(ActionType.TIME_UNIT, data, frame))));
        menuBar.add(createJMenu("Help"));

        return menuBar;
    }

    /**
     * Fills the JMenu with the provided Actions
     *
     * @param name       Name of the item for the JMenuBar
     * @param actionList Actions for the JMenu
     * @return JMenu with the name and actions
     */
    private JMenu createJMenu(String name, Action... actionList) {
        JMenu menu = new JMenu(name);
        if (actionList.length == 0) {
            menu.add("PLACEHOLDER_ACTION");
        }
        for (Action a : actionList) {
            menu.add(a);
        }

        return menu;
    }

    /**
     * Creates application Toolbar
     *
     * @return Toolbar with AddNewTask button and filters for the tasks
     */
    private JToolBar createFilterBar() {
        JToolBar filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        JCheckBox filterToDo = createFilterCheckbox("To-Do", true);
        JCheckBox filterInProgress = createFilterCheckbox("In-Progress", true);
        JCheckBox filterComplete = createFilterCheckbox("Completed", true);
        JCheckBox filterOnHold = createFilterCheckbox("On-Hold", true);

        JCheckBox filterOverdue = createFilterCheckbox("Filter Overdue ", false);
        JCheckBox filterOverBudget = createFilterCheckbox("Filter Over budget", false);

        JComboBox<Object> categoryComboBox = createFilterComboBox(data.getCategories().toArray(),
                "--Category--");
        JComboBox<Object> assigneeComboBox = createFilterComboBox(data.getEmployees().toArray(),
                "--Assignee--");
        assigneeComboBox.setRenderer(new EmployeeComboboxRenderer());

        Map<Boolean, List<JCheckBox>> resetValuesCheckboxes = Map.of(
                true, List.of(filterToDo, filterInProgress, filterComplete, filterOnHold),
                false, List.of(filterOverdue, filterOverBudget));

        Map<JComboBox<Object>, String> resetValuesComboBoxes = Map.of(
                categoryComboBox, "--Category--",
                assigneeComboBox, "--Assignee--"
        );

        var datePicker = new DatePicker();


        JButton addNewTaskButton = createButton("New Task ", Icons.ADD_ICON,
                new ChooseTemplateAction(data, frame));

        JButton resetFiltersButton = createButton("Reset Filters ", Icons.RESET_ICON,
                new ResetFilterAction(resetValuesCheckboxes, resetValuesComboBoxes, datePicker));

        newSomethingButton = addNewTaskButton;

        filterBar.add(newSomethingButton);

        filterBar.addSeparator();

        JPanel statusPanel = new JPanel(new GridLayout(2, 2));
        statusPanel.add(filterToDo);
        statusPanel.add(filterInProgress);
        statusPanel.add(filterComplete);
        statusPanel.add(filterOnHold);
        filterBar.add(statusPanel);

        filterBar.addSeparator();

        JPanel filterDatePanel = new JPanel(new GridLayout(2, 2));
        filterDatePanel.add(filterOverdue);
        filterDatePanel.add(datePicker);
        filterDatePanel.add(filterOverBudget);
        filterBar.add(filterDatePanel);

        filterBar.addSeparator();

        JPanel categoryAssigneePanel = new JPanel(new GridLayout(2, 1));
        categoryAssigneePanel.add(categoryComboBox);
        categoryAssigneePanel.add(assigneeComboBox);
        filterBar.add(categoryAssigneePanel);

        filterBar.addSeparator();

        filterBar.add(resetFiltersButton);

        return filterBar;
    }

    /**
     * Creates a custom JCheckBox
     *
     * @param checkBoxText Text of the checkbox
     * @param setSelected  Default state of the checkbox
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
     * @param taskCrudService Tasks for the table
     * @return Table with tasks
     */
    private JTable createTaskTable(CrudService<Task> taskCrudService) {
        var model = new TaskTableModel(taskCrudService);
        var table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        var progressColumn = table.getColumnModel().getColumn(7);
        progressColumn.setCellRenderer(new TaskProgressBar());
        var categoryColumn = table.getColumnModel().getColumn(2);
        categoryColumn.setCellRenderer(new CategoryCellRenderer());
        data.setTaskTableModel(model);

        return table;
    }

    private JTable createTemplateTable(CrudService<Template> templateCrudService) {
        var model = new TemplateTableModel(templateCrudService);
        var table = new JTable(model);
        table.setAutoCreateRowSorter(true);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        var categoryColumn = table.getColumnModel().getColumn(1);
        categoryColumn.setCellRenderer(new CategoryCellRenderer());

        return table;
    }

    private JTable createStatisticsTable(){
        var model = new StatisticsTableModel();
        var table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return table;
    }

    /**
     * @param buttonText Text to be shown on button
     * @param icon       Icon for the button
     * @param a          Action to be performed
     * @return Button with input characteristics
     */
    public static JButton createButton(String buttonText, Icon icon, Action a) {
        var button = new JButton(buttonText, icon);
        button.addActionListener(a);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * Creates a custom JComboBox
     *
     * @param items           Items for the comboBox
     * @param placeholderText Placeholder text to be shown
     * @return comboBox with input parameters
     */
    private JComboBox<Object> createFilterComboBox(Object[] items, String placeholderText) {
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
     *
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
     *
     * @return created menu
     */
    private JPopupMenu createTaskTablePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new EditAction(ActionType.TASK, null, data));
        menu.add(new DeleteAction(ActionType.TASK, null, data));
        menu.add(new InspectAction(ActionType.TASK, frame, data));

        return menu;
    }

    /**
     * Sets up mouse listener to open task inspect window when double-clicking on task
     * @param taskMenu Table with content for inspect
     */

    private void setUpTaskInspect(JTable taskMenu){
        data.getTaskTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(e.getClickCount());
                if (e.getClickCount() == 2 && Arrays.stream(data.getTaskTable().getSelectedRows()).count() == 1) {
                    InspectAction inspectAction = new InspectAction(ActionType.TASK, frame, data);
                    inspectAction.actionPerformed(null);
                }
            }
        });

    }
}
