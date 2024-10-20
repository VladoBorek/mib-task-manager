package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.Status;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TaskDialog extends EntityDialog<Task>{

    private final JTextField taskNameField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextField customerField = new JTextField();

    private final Task task;

    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());

    private final Object[] categories;
    private final JComboBox<Object> categoryComboBox;

    private final JIntegerTextField loggedTimeField = new JIntegerTextField();
    private final JIntegerTextField allocatedTimeField = new JIntegerTextField();

    private final DatePicker datePicker = new DatePicker();

    private final JComboBox<Object> timeUnitsCombobox;
    private final Object[] timeUnits;
    private final TimeUnit timeUnit = new CustomTimeUnit();

    public TaskDialog(Task task, Object[] categories, Object[] timeUnits) {
        super(500, 600);
        this.task = task;
        this.categories = categories;
        this.timeUnits = timeUnits;
        this.categoryComboBox = new JComboBox<>(this.categories);
        this.timeUnitsCombobox = new JComboBox<>(this.timeUnits);

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new Dimension(200, 100));

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        statusComboBox.setRenderer(listRenderer);
        categoryComboBox.setRenderer(listRenderer);
        timeUnitsCombobox.setRenderer(listRenderer);

        taskNameField.setHorizontalAlignment(SwingConstants.CENTER);
        customerField.setHorizontalAlignment(SwingConstants.CENTER);
        loggedTimeField.setHorizontalAlignment(SwingConstants.CENTER);
        allocatedTimeField.setHorizontalAlignment(SwingConstants.CENTER);


        setValues();
        addFields();
        setPanel();
    }

    private void setValues()
    {
        taskNameField.setText(task.getNameOfTask());
        descriptionArea.setText(task.getDescription());
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
        addCentered("Task name", taskNameField);
        addCentered("Description", new JScrollPane(descriptionArea));
        addCentered("Customer", customerField);
        addCentered("Category", categoryComboBox);
        addCentered("Status", statusComboBox);
        addCentered("Logged time", loggedTimeField);
        addCentered("Allocated time", allocatedTimeField);

//        var timeUnitField = new JLabel(task.getTimeUnit().getName());
//        timeUnitField.setHorizontalAlignment(SwingConstants.CENTER);

        //addCentered("Time unit", timeUnitField);
        addCentered("Time unit", timeUnitsCombobox);

        datePicker.setAlignmentX(SwingConstants.CENTER);
        addCentered("Due date", datePicker);
    }

    @Override
    Task getEntity() {
        task.setNameOfTask(taskNameField.getText());
        task.setDescription(descriptionArea.getText());
        task.setCustomer(customerField.getText());
        task.setCategory((Category) categoryComboBox.getSelectedItem());
        task.setStatus((Status) statusComboBox.getSelectedItem());
        task.setLoggedTime(loggedTimeField.getValue());
        task.setAllocatedTime(allocatedTimeField.getValue());
        task.setDueDate(datePicker.getDate());
        return task;
    }
}
