package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;

public class TaskDialog extends EntityDialog<Task>{

    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();

    private final Task task;

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

    private final JComboBox<Object> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();
    //TODO add combobox for time units
    private final TimeUnit timeUnit = new CustomTimeUnit();

    public TaskDialog(Task task, CategoryListModel categories, TimeUnitListModel timeUnits) {
        this.task = task;
        this.categoryComboBox = new JComboBox<>(categories.toArray());
        this.timeUnitComboBox = new JComboBox<>(timeUnits.toArray());
        if (task != null) {
            setValues(task);
        }
        addFields();
        setPanel();
    }

    private void setValues(Task task)
    {
        taskNameField.setText(this.task.getNameOfTask());
        customerField.setText(this.task.getCustomer());
        categoryComboBox.setSelectedItem(this.task.getCategory());
        statusComboBox.setSelectedItem(this.task.getCategory());
        loggedTimeField.setValue(this.task.getLoggedTime());
        allocatedTimeField.setValue(this.task.getAllocatedTime());
        datePicker.setDate(this.task.getDueDate());
        timeUnit.setName(this.task.getTimeUnit().getName());
        timeUnit.setRate(this.task.getTimeUnit().getRate());
    }

    private void addFields(){
        add("Task name", taskNameField);
        add("Customer", customerField);
        add("Category", categoryComboBox);
        add("Status", statusComboBox);
        add("Logged time", loggedTimeField);
        add("Allocated time", allocatedTimeField);
        add("Time unit", timeUnitComboBox);
        add("Due date", datePicker);

    }

    @Override
    Task getEntity() {
        var task = this.task;
        if (task != null) {
            task.setNameOfTask(taskNameField.getText());
            task.setCustomer(customerField.getText());
            task.setCategory((Category) categoryComboBox.getSelectedItem());
            task.setStatus((Status) statusComboBox.getSelectedItem());
            task.setLoggedTime(loggedTimeField.getValue());
            task.setAllocatedTime(allocatedTimeField.getValue());
            task.setDueDate(datePicker.getDate());
            task.setTimeUnit((TimeUnit) timeUnitComboBox.getSelectedItem());
        } else {
            task = new Task((Status) statusComboBox.getSelectedItem(),
                    (Category) categoryComboBox.getSelectedItem(),
                    customerField.getText(),
                    taskNameField.getText(),
                    "TODO",
                    loggedTimeField.getValue(),
                    allocatedTimeField.getValue(),
                    (TimeUnit) timeUnitComboBox.getSelectedItem(),
                    datePicker.getDate());
        }
        return task;
    }
}
