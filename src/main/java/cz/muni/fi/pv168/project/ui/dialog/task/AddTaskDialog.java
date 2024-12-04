package cz.muni.fi.pv168.project.ui.dialog.task;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.TaskValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.category.AddCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.AddTimeUnitAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createTwoPartPanel;

public class AddTaskDialog extends EntityDialog<Task> {
    private Task task;
    private final DataManager data;
    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextField assignedToName = new JTextField();
    private JPanel timeUnitPanel;
    private JPanel categoryPanel;
    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitsComboBox;
    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();
    private final DatePicker datePicker = new DatePicker();
    private final JPanel infoPanel = new JPanel();
    private final JPanel descriptionPanel = new JPanel();
    private final JPanel timePanel = new JPanel();

    public AddTaskDialog(Task task, DataManager data) {
        this.task = task;
        this.data = data;

        this.timeUnitsComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnits()));
        this.categoryComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategories()));

        setUpUI();
        if (task != null) {
            setValues();
        }
    }

    private void setUpUI() {
        super.getPanel().setLayout(new BorderLayout());
        super.getPanel().add(infoPanel, BorderLayout.NORTH);
        super.getPanel().add(descriptionPanel, BorderLayout.CENTER);
        super.getPanel().add(timePanel, BorderLayout.SOUTH);

        setupTwoPartPanels();
        setupInfoPanel();
        setupDescriptionPanel();
        setupTimePanel();

        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        descriptionPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    private void setupTwoPartPanels() {
        categoryComboBox.setRenderer(new CategoryComboboxRenderer());

        var addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddCategoryAction(data, categoryComboBox));
        var addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddTimeUnitAction(data, timeUnitsComboBox));

        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        categoryComboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox));

        categoryPanel = createTwoPartPanel(categoryComboBox, addCategoryButton);
        timeUnitPanel = createTwoPartPanel(timeUnitsComboBox, addTimeUnitButton);
    }

    private void setupInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(getLabelPanel());
        infoPanel.add(getComponentPanel());

        datePicker.setDateToToday();
        addInfoFields();
    }

    private void addInfoFields() {
        add("Task name", taskNameField);
        add("Customer", customerField);
        add("Category", categoryPanel);
        add("Assigned to Name", assignedToName);
        add("Status", statusComboBox);
        add("Due date", datePicker);
    }

    private void setupDescriptionPanel() {
        descriptionPanel.setLayout(new BorderLayout());

        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.add(new JLabel("Description:"));

        JPanel textDescriptionPanel = new JPanel(new BorderLayout());
        textDescriptionPanel.add(new JScrollPane(descriptionArea));

        descriptionPanel.add(titleDescriptionPanel, BorderLayout.NORTH);
        descriptionPanel.add(textDescriptionPanel, BorderLayout.CENTER);

        descriptionArea.setPreferredSize(new Dimension(200, 50));
        descriptionArea.setMinimumSize(new Dimension(200, 50));
        descriptionArea.setMaximumSize(new Dimension(200, 50));

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
    }

    private void setupTimePanel() {
        timePanel.setLayout(new BorderLayout());
        timePanel.add(setupTimeTitlesPanel(), BorderLayout.NORTH);
        timePanel.add(setupTimeInfoPanel(), BorderLayout.SOUTH);
    }

    private JPanel setupTimeTitlesPanel() {
        JPanel timeTitlesPanel = new JPanel(new GridLayout(1, 2));

        timeTitlesPanel.add(new JLabel("Allocated time"));
        timeTitlesPanel.add(new JLabel("Time unit"));

        return timeTitlesPanel;
    }

    private JPanel setupTimeInfoPanel() {
        JPanel timeInfoPanel = new JPanel();

        timeInfoPanel.setLayout(new GridLayout(1, 2));
        timeInfoPanel.add(this.allocatedTimeField);
        timeInfoPanel.add(timeUnitPanel);

        return timeInfoPanel;
    }

    private void setValues() {
        taskNameField.setText(task.getName());
        descriptionArea.setText(task.getDescription());
        customerField.setText(task.getCustomer());
        assignedToName.setText(task.getAssignedTo());
        categoryComboBox.setSelectedItem(task.getCategory());
        statusComboBox.setSelectedItem(task.getStatus());
        loggedTimeField.setValue(task.getConvertedLoggedTime());
        allocatedTimeField.setValue(task.getConvertedAllocatedTime());
        datePicker.setDate(task.getDueDate());
        timeUnitsComboBox.setSelectedItem(task.getTimeUnit());
    }

    private boolean validateFields() {
        if ((taskNameField.getText().trim().isEmpty())
                || (customerField.getText().trim().isEmpty())
                || (assignedToName.getText().trim().isEmpty())
                || (categoryComboBox.getSelectedItem() == null)
                || (statusComboBox.getSelectedItem() == null)
                || (timeUnitsComboBox.getSelectedItem() == null)
                || (allocatedTimeField.getText().trim().isEmpty())
                || (loggedTimeField.getText().trim().isEmpty())
        ) {
            PopUp.infoDialog(
                    "Please fill all information",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    @Override
    public Task getEntity() {
        if (!validateFields()) {
            return null;
        }
        Validator<Task> taskValidator = new TaskValidator();
        var newTask = new Task(
                null, (Status) statusComboBox.getSelectedItem(),
                this.descriptionArea.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                customerField.getText(),
                taskNameField.getText(),
                assignedToName.getText(),
                loggedTimeField.getValue(),
                allocatedTimeField.getValue(),
                (TimeUnit) Objects.requireNonNull(timeUnitsComboBox.getSelectedItem()),
                datePicker.getDate());
        var validation = taskValidator.validate(newTask);

        if (!validateFields()) {
            return null;
        }
        if (!validation.isValid()) {
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return newTask;
    }


}
