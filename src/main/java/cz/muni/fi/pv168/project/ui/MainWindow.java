package cz.muni.fi.pv168.project.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.actions.menu.ChooseTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.ResetFilterAction;
import cz.muni.fi.pv168.project.ui.actions.menu.category.AddCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.category.ManageCategoriesAction;
import cz.muni.fi.pv168.project.ui.actions.menu.export.ExportAction;
import cz.muni.fi.pv168.project.ui.actions.menu.export.ImportAction;
import cz.muni.fi.pv168.project.ui.actions.menu.task.DeleteTaskAction;
import cz.muni.fi.pv168.project.ui.actions.menu.task.EditTaskAction;
import cz.muni.fi.pv168.project.ui.actions.menu.task.InspectTaskAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.AddTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.DeleteTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.EditTemplateAction;
import cz.muni.fi.pv168.project.ui.actions.menu.template.ManageTemplatesAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.AddTimeUnitAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.ManageTimeUnitsAction;
import cz.muni.fi.pv168.project.ui.filters.TaskTableFilter;
import cz.muni.fi.pv168.project.ui.filters.TemplateTableFilter;
import cz.muni.fi.pv168.project.ui.filters.components.FilterComboboxBuilder;
import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;
import cz.muni.fi.pv168.project.ui.model.TaskProgressBar;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryCellRenderer;
import cz.muni.fi.pv168.project.ui.renderers.CategoryRenderer;
import cz.muni.fi.pv168.project.ui.renderers.SpecialFilterCategoryValuesRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.util.Either;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createButton;

/**
 * Main application window for the MIB Task Manager.
 * Handles the creation and layout of the main frame.
 */
public class MainWindow {
    public static final Color BUTTON_COLOR = new Color(220, 220, 220);

    private final JFrame frame;
    private final UIDataManager data;
    private final DependencyProvider dependencyProvider;

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
    public MainWindow(User loggedUser, DependencyProvider dependencyProvider) {
        frame = createFrame();
        this.dependencyProvider = dependencyProvider;
        this.data = new UIDataManager(loggedUser, dependencyProvider);

        var taskTable = createTaskTable(dependencyProvider.getTaskCrudService());
        taskTable.setComponentPopupMenu(createTaskTablePopupMenu());

        var templateTable = createTemplateTable(dependencyProvider.getTemplateCrudService());
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
        splitPane.setResizeWeight(0.82);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tasks", splitPane);
        tabbedPane.addTab("Templates", new JScrollPane(templateTable));
        tabbedPane.addChangeListener(e -> updateToolBarForSelectedTab(tabbedPane, taskToolBar, templateToolBar, frame));
        frame.add(tabbedPane, BorderLayout.CENTER);

        setUpTaskInspect();

        frame.setLocationRelativeTo(null);
        frame.pack();
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

        menuBar.add(createJMenu("File", new ImportAction(dependencyProvider.getImportService(), this::refresh)
                , new ExportAction(dependencyProvider.getExportService())));
        menuBar.add(createJMenu("Template",
                new AddTemplateAction(data),
                new ManageTemplatesAction(data, frame)));
        menuBar.add((createJMenu("Categories",
                new AddCategoryAction(data),
                new ManageCategoriesAction(data, frame))));
        menuBar.add((createJMenu("Time Units",
                new AddTimeUnitAction(data),
                new ManageTimeUnitsAction(data, frame))));

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
        return table;
    }

    /**
     * Creates application Toolbar
     *
     * @return Toolbar with an Add New button and filters
     */
    private JToolBar createTaskToolBar(JTable taskTable, UIDataManager data) {
        var rowSorter = new TableRowSorter<>((TaskTableModel) taskTable.getModel());
        var taskTableFilter = new TaskTableFilter(rowSorter, data);
        taskTable.setRowSorter(rowSorter);


        var filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        var statusPanel = createStatusCheckboxesPanel(taskTableFilter);
        var categoryComboBox = createTaskCategoryFilter(taskTableFilter, data.getCategoryListModel());
        JPanel categoryPanel = createCategoryPanel(categoryComboBox);
        var filterDatePanel = createDateFilterPanel(taskTableFilter);

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

    private JToolBar createTemplateToolBar(JTable templateTable, UIDataManager data) {
        var rowSorter = new TableRowSorter<>((TemplateTableModel) templateTable.getModel());
        var templateTableFilter = new TemplateTableFilter(rowSorter);
        templateTable.setRowSorter(rowSorter);

        JToolBar filterBar = new JToolBar();
        filterBar.setFloatable(false);
        filterBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterBar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        var categoryComboBox = createTemplateCategoryFilter(templateTableFilter, data.getCategoryListModel());
        JPanel categoryPanel = createCategoryPanel(categoryComboBox);

        JButton newButton = createButton("New ", Icons.ADD_ICON,
                new AddTemplateAction(data));
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
        filterToDo = createFilterCheckbox("To-Do");
        filterInProgress = createFilterCheckbox("In-Progress");
        filterComplete = createFilterCheckbox("Completed");
        filterOnHold = createFilterCheckbox("On-Hold");

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
            TaskTableFilter taskTableFilter, BaseListModel<Category> categoryListModel) {
        return FilterComboboxBuilder.create(SpecialFilterCategoryValues.class, categoryListModel)
                .setSelectedItem(SpecialFilterCategoryValues.ALL)
                .setSpecialValuesRenderer(new SpecialFilterCategoryValuesRenderer())
                .setValuesRenderer(new CategoryRenderer())
                .setFilter(taskTableFilter::filterCategory)
                .build();
    }

    private static JComboBox<Either<SpecialFilterCategoryValues, Category>> createTemplateCategoryFilter(
            TemplateTableFilter templateTableFilter, BaseListModel<Category> categoryListModel) {
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
     * @return checkBox
     */
    private JCheckBox createFilterCheckbox(String checkBoxText) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText(checkBoxText);
        checkBox.setSelected(true);
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

    private JTable createStatisticsTable() {
        var model = new StatisticsTableModel(data);
        var table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return table;
    }

    /**
     * Creates pop up menu for the task table
     *
     * @return created menu
     */
    private JPopupMenu createTaskTablePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new EditTaskAction(data));
        menu.add(new DeleteTaskAction(data));
        menu.add(new InspectTaskAction(data));

        return menu;
    }

    private JPopupMenu createTemplateTablePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(new EditTemplateAction(data));
        menu.add(new DeleteTemplateAction(data));
        return menu;
    }

    /**
     * Sets up mouse listener to open task inspect window when double-clicking on task
     */

    private void setUpTaskInspect() {
        data.getTaskTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && Arrays.stream(data.getTaskTable().getSelectedRows()).count() == 1) {
                    InspectTaskAction inspectAction = new InspectTaskAction(data);
                    inspectAction.actionPerformed(null);
                }
            }
        });

    }

    private void refresh() {
        data.getTaskTableModel().refresh();
        data.getCategoryListModel().refresh();
        data.getTimeUnitListModel().refresh();
        data.getTemplateTableModel().refresh();
        data.getLogTimeInfoTableModel().refresh();
    }
}
