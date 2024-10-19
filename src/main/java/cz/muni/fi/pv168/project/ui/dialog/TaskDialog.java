package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;

public class TaskDialog extends EntityDialog<Task>{

    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();

    private final Task task;

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

    private final Object[] categories;
    private final JComboBox<Object> categoryComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();
    //TODO add combobox for time units
    private final TimeUnit timeUnit = new CustomTimeUnit("Hour", 1);

    public TaskDialog(Task task, Object[] categories) {
        this.task = task;
        this.categories = categories;
        this.categoryComboBox = new JComboBox<>(categories);
        setValues();
        addFields();
        setPanel();
    }

    private void setValues()
    {
        taskNameField.setText(task.getNameOfTask());
        customerField.setText(task.getCustomer());
        categoryComboBox.setSelectedItem(task.getCategory());
        statusComboBox.setSelectedItem(task.getCategory());
        loggedTimeField.setValue(task.getLoggedTime());
        allocatedTimeField.setValue(task.getAllocatedTime());
        datePicker.setDate(task.getDueDate());
        timeUnit.setName(task.getTimeUnit().getName());
        timeUnit.setRate(task.getTimeUnit().getRate());
    }

    private void addFields(){
        add("Task name", taskNameField);
        add("Customer", customerField);
        add("Category", new JComboBox<>(categories));
        add("Status", statusComboBox);
        add("Logged time", loggedTimeField);
        add("Allocated time", allocatedTimeField);
        add("Time unit", new JLabel(task.getTimeUnit().getName()));
        add("Due date", datePicker);

    }

    @Override
    Task getEntity() {
        task.setNameOfTask(taskNameField.getText());
        task.setCustomer(customerField.getText());
        task.setCategory((Category) categoryComboBox.getSelectedItem());
        task.setStatus((Status) statusComboBox.getSelectedItem());
        task.setLoggedTime(loggedTimeField.getValue());
        task.setAllocatedTime(allocatedTimeField.getValue());
        task.setDueDate(datePicker.getDate());
        return task;
    }
}
