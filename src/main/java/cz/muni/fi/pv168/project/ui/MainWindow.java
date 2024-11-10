package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.TaskValidator;
import cz.muni.fi.pv168.project.business.service.validation.TemplateValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.actions.menu.ExportAction;
import cz.muni.fi.pv168.project.ui.actions.menu.ImportAction;
import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.actions.menu.*;
import cz.muni.fi.pv168.project.ui.filters.TaskTableFilter;
import cz.muni.fi.pv168.project.ui.filters.TemplateTableFilter;
import cz.muni.fi.pv168.project.ui.filters.components.FilterComboboxBuilder;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.model.storagemodels.CategoryListModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryCellRenderer;

import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;

import cz.muni.fi.pv168.project.ui.model.TaskProgressBar;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCategoryValuesRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.util.Either;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main application window for the MIB Task Manager.
 * Handles the creation and layout of the main frame.
 */
public class MainWindow {

    public static final Color BUTTON_COLOR = new Color(220, 220, 220);
    public static final DemoDataGenerator DEMO_DATA = new DemoDataGenerator();

    private final JFrame frame;
    private final DataManager data;

    private JCheckBox filterToDo;
    private JCheckBox filterComplete;
    private JCheckBox filterInProgress;
    private JCheckBox filterOnHold;

    private DatePicker fromDatePicker;
    private DatePicker toDatePicker;


    /**
     * Constructor for MainWindow.
     * Initializes the main frame, sets the background color, size, and adds the menu bar and filter bar.
     */
    public MainWindow(User loggedUser) {
        data = new DataManager(loggedUser);
        frame = createFrame();

        Validator<Task> taskValidator = new TaskValidator();
        Repository<Task> taskRepository = new InMemoryRepository<>(DEMO_DATA.getTasks());
        CrudService<Task> taskCrudService = new BaseCrudService<>(taskRepository, taskValidator);

        Validator<Template> templateValidator = new TemplateValidator();
        Repository<Template> templateRepository = new InMemoryRepository<>(new ArrayList<>());
        CrudService<Template> templateCrudService = new BaseCrudService<>(templateRepository, templateValidator);

        var taskTable = createTaskTable(taskCrudService);
        taskTable.setComponentPopupMenu(createTaskTablePopupMenu());

        var templateTable = createTemplateTable(templateCrudService);
        templateTable.setComponentPopupMenu(createTemplateTablePopupMenu());


        data.setTaskTable(taskTable);
        data.setTemplateTable(templateTable);

        var statisticsTable = createStatisticsTable();
        data.setStatisticsTable(statisticsTable);



        frame.setJMenuBar(createMenuBar());

        var taskToolBar = createTaskToolBar(taskTable, data);
        var templateToolBar = createTemplateToolBar(templateTable, data);
        frame.add(taskToolBar, BorderLayout.BEFORE_FIRST_LINE);





        var splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerSize(10);
        splitPane.setTopComponent(new JScrollPane(taskTable));
        splitPane.setBottomComponent(new JScrollPane(statisticsTable));
        splitPane.setResizeWeight(0.85);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tasks", splitPane);
        tabbedPane.addTab("Templates", new JScrollPane(templateTable));
        tabbedPane.addChangeListener(e -> {
            updateToolBarForSelectedTab(tabbedPane, taskToolBar, templateToolBar, frame);
        });
        frame.add(tabbedPane, BorderLayout.CENTER);

        setUpTaskInspect(taskTable);

        frame.setLocationRelativeTo(null);
        frame.pack();
        // This has to be here idk why
        frame.setSize(1024, 768);
    }

    private void updateToolBarForSelectedTab(JTabbedPane tabbedPane,
                                             JToolBar taskToolBar,
                                             JToolBar templateToolBar,
                                             JFrame frame) {

        String selectedTabTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());

        if ("Templates".equals(selectedTabTitle)) {
            frame.remove(taskToolBar);
            frame.add(templateToolBar, BorderLayout.BEFORE_FIRST_LINE);
        } else if ("Tasks".equals(selectedTabTitle)) {
            frame.remove(templateToolBar);
            frame.add(taskToolBar, BorderLayout.BEFORE_FIRST_LINE);
        }

        frame.revalidate();
        frame.repaint();
    }

    /**
     * Creates and configures the main JFrame.
     *
     * @return Configured JFrame instance.
     */
    private JFrame createFrame() {
        JFrame frame = new JFrame("MIB Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Icons.APP_ICON.getImage());
        frame.setSize(1024, 768);
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
        menuBar.add(createButton("Help", null,new HelpAction()));

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

    private JTable createTaskTable(CrudService<Task> taskCrudService) {
        var tableModel = new TaskTableModel(taskCrudService);
        var table = new JTable(tableModel);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        var progressColumn = table.getColumnModel().getColumn(7);
        progressColumn.setCellRenderer(new TaskProgressBar());

        var categoryColumn = table.getColumnModel().getColumn(2);
        categoryColumn.setCellRenderer(new CategoryCellRenderer());

        data.setTaskTableModel(tableModel);

        return table;
    }

    /**
     * Creates application Toolbar
     *
     * @return Toolbar with an Add New button and filters
     */
    private JToolBar createTaskToolBar(JTable taskTable, DataManager data) {
        var rowSorter = new TableRowSorter<>((TaskTableModel) taskTable.getModel());
        var taskTableFilter = new TaskTableFilter(rowSorter, data);
        taskTable.setRowSorter(rowSorter);


        var filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        var statusPanel = createStatusCheckboxesPanel(taskTableFilter);
        var categoryComboBox = createTaskCategoryFilter(taskTableFilter, data.getCategories());
        JPanel categoryPanel = createCategoryPanel(categoryComboBox);
        var filterDatePanel  = createDateFilterPanel(taskTableFilter);

        Map<Boolean, List<JCheckBox>> resetValuesCheckboxes = Map.of(
                true, List.of(filterToDo, filterInProgress, filterComplete, filterOnHold),
                false, List.of());

        JButton newButton = createButton("New ", Icons.ADD_ICON,
                new ChooseTemplateAction(data, frame));
        JButton resetFiltersButton = createButton("Reset Filters ", Icons.RESET_ICON,
                new ResetFilterAction(resetValuesCheckboxes, categoryComboBox, List.of(fromDatePicker, toDatePicker)));

        filterBar.add(newButton);
        filterBar.addSeparator();
        filterBar.add(statusPanel);
        filterBar.addSeparator();
        filterBar.add(filterDatePanel);
        filterBar.addSeparator();
        filterBar.add(categoryPanel);
        filterBar.addSeparator();
        filterBar.add(resetFiltersButton);

        filterBar.setPreferredSize(new Dimension(filterBar.getPreferredSize().width, 50));

        return filterBar;
    }

    private JToolBar createTemplateToolBar(JTable templateTable, DataManager data) {
        var rowSorter = new TableRowSorter<>((TemplateTableModel) templateTable.getModel());
        var templateTableFilter = new TemplateTableFilter(rowSorter);
        templateTable.setRowSorter(rowSorter);

        JToolBar filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        var categoryComboBox = createTemplateCategoryFilter(templateTableFilter, data.getCategories());
        JPanel categoryPanel = createCategoryPanel(categoryComboBox);

        JButton newButton = createButton("New ", Icons.ADD_ICON,
                    new AddAction(ActionType.TEMPLATE, data, null));
        JButton resetFiltersButton = createButton("Reset Filters ", Icons.RESET_ICON,
                new ResetFilterAction(new HashMap<>(), categoryComboBox, List.of(fromDatePicker, toDatePicker)));

        filterBar.add(newButton);
        filterBar.addSeparator();
        filterBar.add(categoryPanel);
        filterBar.addSeparator();
        filterBar.add(resetFiltersButton);

        filterBar.setPreferredSize(new Dimension(filterBar.getPreferredSize().width, 50));

        return filterBar;
    }

    private JPanel createCategoryPanel(Component bottomComponent) {
        JPanel categoryPanel = new JPanel(new GridLayout(2, 1));
        JLabel categoryLabel = new JLabel("Category", SwingConstants.CENTER);
        categoryPanel.add(categoryLabel);
        categoryPanel.add(bottomComponent);
        return categoryPanel;
    }

    private JPanel createDateFilterPanel(TaskTableFilter taskTableFilter) {
        JPanel filterDatePanel = new JPanel(new GridLayout(2, 1, 0, 2));

        JPanel fromPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        fromDatePicker = new DatePicker();
        fromPanel.add(new JLabel("Due From "));
        fromPanel.add(fromDatePicker);
        filterDatePanel.add(fromPanel);

        JPanel toPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        toDatePicker = new DatePicker();
        toPanel.add(new JLabel("Due To "));
        toPanel.add(toDatePicker);
        filterDatePanel.add(toPanel);

        fromDatePicker.addDateChangeListener(
                e -> updateDateFilter(taskTableFilter, fromDatePicker.getDate(), toDatePicker.getDate()));
        toDatePicker.addDateChangeListener(
                e -> updateDateFilter(taskTableFilter, fromDatePicker.getDate(), toDatePicker.getDate()));

        return filterDatePanel;
    }

    private void updateDateFilter(TaskTableFilter taskTableFilter, LocalDate fromDate, LocalDate toDate) {
        taskTableFilter.filterDueDate(fromDate, toDate);
    }

    private JPanel createStatusCheckboxesPanel(TaskTableFilter taskTableFilter) {
        filterToDo = createFilterCheckbox("To-Do", true);
        filterInProgress = createFilterCheckbox("In-Progress", true);
        filterComplete = createFilterCheckbox("Completed", true);
        filterOnHold = createFilterCheckbox("On-Hold", true);

        filterToDo.addActionListener(e -> applyStatusFilter(taskTableFilter));
        filterInProgress.addActionListener(e -> applyStatusFilter(taskTableFilter));
        filterComplete.addActionListener(e -> applyStatusFilter(taskTableFilter));
        filterOnHold.addActionListener(e -> applyStatusFilter(taskTableFilter));

        JPanel statusPanel = new JPanel(new GridLayout(2, 2));
        statusPanel.add(filterToDo);
        statusPanel.add(filterInProgress);
        statusPanel.add(filterComplete);
        statusPanel.add(filterOnHold);

        return statusPanel;
    }

    private void applyStatusFilter(TaskTableFilter taskTableFilter) {
        taskTableFilter.filterStatus(
                filterToDo.isSelected(),
                filterInProgress.isSelected(),
                filterComplete.isSelected(),
                filterOnHold.isSelected()
        );
    }

    private static JComboBox<Either<SpecialFilterCategoryValues, Category>> createTaskCategoryFilter(
            TaskTableFilter taskTableFilter, CategoryListModel categoryListModel) {
        return FilterComboboxBuilder.create(SpecialFilterCategoryValues.class, categoryListModel)
                .setSelectedItem(SpecialFilterCategoryValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCategoryValuesRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(taskTableFilter::filterCategory)
                .build();
    }

    private static JComboBox<Either<SpecialFilterCategoryValues, Category>> createTemplateCategoryFilter(
            TemplateTableFilter templateTableFilter, CategoryListModel categoryListModel) {
        return FilterComboboxBuilder.create(SpecialFilterCategoryValues.class, categoryListModel)
                .setSelectedItem(SpecialFilterCategoryValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCategoryValuesRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(templateTableFilter::filterCategory)
                .build();
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
        return checkBox;
    }

    private JTable createTemplateTable(CrudService<Template> templateCrudService) {
        var model = new TemplateTableModel(templateCrudService);
        var table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        var categoryColumn = table.getColumnModel().getColumn(2);
        categoryColumn.setCellRenderer(new CategoryCellRenderer());

        return table;
    }

    private JTable createStatisticsTable(){
        var model = new StatisticsTableModel(data);
        var table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return table;
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

    private JPopupMenu createTemplateTablePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new EditAction(ActionType.TEMPLATE, null, data));
        menu.add(new DeleteAction(ActionType.TEMPLATE, null, data));
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
}
