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
import cz.muni.fi.pv168.project.ui.model.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AddTaskDialog extends EntityDialog<Task>{
    private Task task;

    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();

    private JComboBox<Employee> assignedToComboBox;

    private JPanel timeUnitPanel;
    private JPanel categoryPanel;

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());
    private JComboBox<Category> categoryComboBox;
    private JComboBox<TimeUnit> timeUnitsComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();

    public AddTaskDialog(Task task, DataManager data) {
        this.task = task;

        setUpUI(data);
        if (task != null) {
            setValues();
        }
        addFields();
        setPanel();
    }

    private void setUpUI(DataManager data) {
        categoryComboBox = new JComboBox<>(data.getCategories().toArray());
        timeUnitsComboBox = new JComboBox<>(data.getTimeUnits().toArray());
        var addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.TIME_UNIT, data, null,
                        timeUnitsComboBox, categoryComboBox));
        var addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.CATEGORY, data, null,
                        timeUnitsComboBox, categoryComboBox));

        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        categoryComboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox));

        categoryPanel = createTwoPartPanel(categoryComboBox, addCategoryButton);
        timeUnitPanel = createTwoPartPanel(timeUnitsComboBox, addTimeUnitButton);

        assignedToComboBox = new JComboBox<>(data.getEmployees().toArray());

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        datePicker.setDateToToday();
    }

    private JPanel createTwoPartPanel(JComponent comboBox, JComponent button) {
        var newPanel = new JPanel(new GridBagLayout());
        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(comboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        newPanel.add(button, constraints);

        return newPanel;
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

    private void addFields(){
        add("Task name", taskNameField);
        add("Description", new JScrollPane(descriptionArea));
        add("Customer", customerField);
        add("Category", categoryPanel);
        add("Assigned to", assignedToComboBox);
        add("Status", statusComboBox);
        add("Logged time", loggedTimeField);
        add("Allocated time", allocatedTimeField);
        add("Time unit", timeUnitPanel);
        add("Due date", datePicker);
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
