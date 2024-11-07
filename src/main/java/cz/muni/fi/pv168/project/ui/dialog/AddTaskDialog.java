package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class AddTaskDialog extends EntityDialog<Task>{
    private Task task;

    private final DataManager data;
    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();

    private final JComboBox<Employee> assignedToComboBox;

    private JPanel timeUnitPanel;
    private JPanel categoryPanel;

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());
    private JComboBox<Category> categoryComboBox;
    private JComboBox<TimeUnit> timeUnitsComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();

    private final JPanel infoPanel = new JPanel();
    private final JPanel descriptionPanel = new JPanel();
    private final JPanel timePanel = new JPanel();

    public AddTaskDialog(Task task, DataManager data) {
        this.task = task;
        this.data = data;
        assignedToComboBox = new JComboBox<>(data.getEmployees().toArray());

        setUpUI();
        if (task != null) {
            setValues();
        }
    }

    private void setUpUI() {
        super.getPanel().setLayout(new BorderLayout());

        setupTwoPartPanels();

        setupInfoPanel();
        super.getPanel().add(infoPanel, BorderLayout.NORTH);

        setupDescriptionPanel();
        super.getPanel().add(descriptionPanel, BorderLayout.CENTER);

        setupTimePanel();
        super.getPanel().add(timePanel, BorderLayout.SOUTH);

        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        descriptionPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    private void setupTwoPartPanels(){
        categoryComboBox = new JComboBox<>(data.getCategories().toArray());
        timeUnitsComboBox = new JComboBox<>(data.getTimeUnits().toArray());

        var addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.CATEGORY, data, null,
                        timeUnitsComboBox, categoryComboBox));
        var addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.TIME_UNIT, data, null,
                        timeUnitsComboBox, categoryComboBox));

        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        categoryComboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox));

        categoryPanel = createTwoPartPanel(categoryComboBox, addCategoryButton);
        timeUnitPanel = createTwoPartPanel(timeUnitsComboBox, addTimeUnitButton);
    }

    private void setupInfoPanel(){
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.add(getLabelPanel());
        infoPanel.add(getComponentPanel());

        datePicker.setDateToToday();

        add("Task name", taskNameField);
        add("Customer", customerField);
        add("Category", categoryPanel);
        add("Assigned to", assignedToComboBox);
        add("Status", statusComboBox);
        add("Due date", datePicker);
    }

    private void setupDescriptionPanel(){
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

    private void setupTimePanel(){
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

    private void setValues()
    {
        taskNameField.setText(task.getNameOfTask());
        descriptionArea.setText(task.getDescription());
        customerField.setText(task.getCustomer());
        assignedToComboBox.setSelectedItem(task.getAssignedTo());
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
                || (assignedToComboBox.getSelectedItem() == null)
                || (categoryComboBox.getSelectedItem() == null)
                || (statusComboBox.getSelectedItem() == null)
                || (timeUnitsComboBox.getSelectedItem() == null)
                || (allocatedTimeField.getText().trim().isEmpty())
                || (loggedTimeField.getText().trim().isEmpty())
        ) {
            JOptionPane.showMessageDialog(null, "Please fill all information", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    @Override
    Task getEntity() {
        if (!validateFields()) {
            return null;
        }
        if (task != null) {
            task.setNameOfTask(taskNameField.getText());
            task.setCustomer(customerField.getText());
            task.setAssignedTo((Employee) assignedToComboBox.getSelectedItem());
            task.setCategory((Category) categoryComboBox.getSelectedItem());
            task.setStatus((Status) statusComboBox.getSelectedItem());
            task.setConvertedLoggedTime(loggedTimeField.getValue());
            task.setConvertedAllocatedTime(allocatedTimeField.getValue());
            task.setDueDate(datePicker.getDate());
            task.setTimeUnit((TimeUnit) timeUnitsComboBox.getSelectedItem());

        }
        else {
            task = new Task(null, (Status) statusComboBox.getSelectedItem(),
                    this.descriptionArea.getText(),
                    (Category) categoryComboBox.getSelectedItem(),
                    customerField.getText(),
                    taskNameField.getText(),
                    (Employee) assignedToComboBox.getSelectedItem(),
                    loggedTimeField.getValue(),
                    allocatedTimeField.getValue(),
                    (TimeUnit) Objects.requireNonNull(timeUnitsComboBox.getSelectedItem()),
                    datePicker.getDate());
        }
        return task;
    }


}
