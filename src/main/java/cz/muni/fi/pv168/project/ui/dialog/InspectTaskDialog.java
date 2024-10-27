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
import javax.swing.border.Border;
import java.awt.*;

public class InspectTaskDialog extends EntityDialog<Task>{

    private static final Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

    private final Task task;

    private final JLabel taskName = new JLabel();
    private final JLabel description = new JLabel();
    private final JLabel customer = new JLabel();
    private final JLabel assignedTo = new JLabel();
    private final JLabel status = new JLabel();
    private final JLabel category = new JLabel();
    private final JLabel loggedTime = new JLabel();
    private final JLabel allocatedTime = new JLabel();
    private final JLabel date = new JLabel();

    private final DataManager data;


    public InspectTaskDialog(Task task, DataManager data){
        super(500, 600);

        this.data = data;
        this.task = task;

        description.setPreferredSize(new Dimension(200, 100));
        description.setMinimumSize(new Dimension(200, 100));
        description.setMaximumSize(new Dimension(200, 100));

        setValues();
        addFields();
        setPanel();

//        centerOutText();
    }

//    private void centerOutText(){
//        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
//        EmployeeComboboxRenderer employeeComboboxRenderer = new EmployeeComboboxRenderer();
//
//        employeeComboboxRenderer.setHorizontalAlignment(EmployeeComboboxRenderer.CENTER);
//        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
//
//        statusComboBox.setRenderer(listRenderer);
//        assignedToComboBox.setRenderer(employeeComboboxRenderer);
//        categoryComboBox.setRenderer(listRenderer);
//        timeUnitsComboBox.setRenderer(listRenderer);
//        //assignedToComboBox.setRenderer(listRenderer);
//
//        taskNameField.setHorizontalAlignment(SwingConstants.CENTER);
//        customerField.setHorizontalAlignment(SwingConstants.CENTER);
//        loggedTimeField.setHorizontalAlignment(SwingConstants.CENTER);
//        allocatedTimeField.setHorizontalAlignment(SwingConstants.CENTER);
//

    private void setValues()
    {
        taskName.setText(task.getNameOfTask());
        taskName.setBorder(taskName.getBorder());
        description.setText(task.getDescription());
        customer.setText(task.getCustomer());
        assignedTo.setText(task.getAssignedTo().toString());
        category.setText(task.getCategory().getName());
        status.setText(task.getStatus().toString());
        loggedTime.setText(task.getConvertedLoggedTimeString());
        loggedTime.setText(task.getConvertedAllocatedTimeString());
        date.setText(task.getDueDate().toString());

        taskName.setBorder(labelBorder);
        description.setBorder(labelBorder);
        customer.setBorder(labelBorder);
        assignedTo.setBorder(labelBorder);
        status.setBorder(labelBorder);
        category.setBorder(labelBorder);
        loggedTime.setBorder(labelBorder);
        allocatedTime.setBorder(labelBorder);
        date.setBorder(labelBorder);
    }

    private void addFields(){
        JButton addLogTimeButton = null;

        addLogTimeButton = MainWindow.createButton("", Icons.ADD_ICON,
                new AddAction(ActionType.TIME_UNIT, data.getTaskTable(), data, null));

        addCentered("Task name", taskName);
        addCentered("Description", description);
        addCentered("Customer", customer);
        addCentered("Category", category);
        addCentered("Assigned to", assignedTo);
        addCentered("Status", status);
        addCentered("Logged time", loggedTime, addLogTimeButton);
        addCentered("Allocated time", allocatedTime);
        addCentered("Due date", date);
    }

    @Override
    Task getEntity() {
        return task;
    }
}
