package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Employee;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;
import cz.muni.fi.pv168.project.ui.model.EmployeeComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AddTaskDialog extends EntityDialog<Task>{

    private final JTextField taskNameField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextField customerField = new JTextField();

    private final Task task;

    private final JComboBox<Employee> assignedToComboBox;
    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

    private final JComboBox<Object> categoryComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();

    private final JComboBox<TimeUnit> timeUnitsComboBox;
    private final TimeUnit timeUnit = new CustomTimeUnit();

    private final DataManager data;

    public AddTaskDialog(Task task, DataManager data){
        super(500, 600);

        this.data = data;
        this.task = task;

        this.assignedToComboBox = new JComboBox<>(data.getEmployees().toArray());
        this.categoryComboBox = new JComboBox<>(data.getCategories().toArray());
        this.timeUnitsComboBox = new JComboBox<>(data.getTimeUnits().toArray());

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        descriptionArea.setPreferredSize(new Dimension(200, 100));
        descriptionArea.setMinimumSize(new Dimension(200, 100));
        descriptionArea.setMaximumSize(new Dimension(200, 100));

        if (task != null) {
            setValues();
        }
        addFields();
        setPanel();

        centerOutText();
    }

    private void centerOutText(){
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        EmployeeComboboxRenderer employeeComboboxRenderer = new EmployeeComboboxRenderer();

        employeeComboboxRenderer.setHorizontalAlignment(EmployeeComboboxRenderer.CENTER);
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);

        statusComboBox.setRenderer(listRenderer);
        assignedToComboBox.setRenderer(employeeComboboxRenderer);
        categoryComboBox.setRenderer(listRenderer);
        timeUnitsComboBox.setRenderer(listRenderer);
        //assignedToComboBox.setRenderer(listRenderer);

        taskNameField.setHorizontalAlignment(SwingConstants.CENTER);
        customerField.setHorizontalAlignment(SwingConstants.CENTER);
        loggedTimeField.setHorizontalAlignment(SwingConstants.CENTER);
        allocatedTimeField.setHorizontalAlignment(SwingConstants.CENTER);

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

        timeUnit.setName(task.getTimeUnit().getName());
        timeUnit.setRate(task.getTimeUnit().getRate());
        timeUnitsComboBox.setSelectedItem(task.getTimeUnit());

    }

    private void addFields(){
        JButton addLogTimeButton = null;
        JButton addTimeUnitButton = null;
        JButton addCategoryButton = null;

        addTimeUnitButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.TIME_UNIT, data, null,
                        timeUnitsComboBox, categoryComboBox));

        addCategoryButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.CATEGORY, data, null,
                        timeUnitsComboBox, categoryComboBox));

        addCentered("Task name", taskNameField);
        addCentered("Description", new JScrollPane(descriptionArea));
        addCentered("Customer", customerField);
        addCentered("Category", categoryComboBox, addCategoryButton);
        addCentered("Assigned to", assignedToComboBox);
        addCentered("Status", statusComboBox);
        addCentered("Logged time", loggedTimeField, addLogTimeButton);
        addCentered("Allocated time", allocatedTimeField);
        addCentered("Time unit", timeUnitsComboBox, addTimeUnitButton);
        addCentered("Due date", datePicker);
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
        var task = this.task;

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
        } else {
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
