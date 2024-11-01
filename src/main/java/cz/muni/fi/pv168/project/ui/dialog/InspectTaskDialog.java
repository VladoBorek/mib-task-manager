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
import cz.muni.fi.pv168.project.ui.actions.menu.LogTimeAction;
import cz.muni.fi.pv168.project.ui.model.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.model.EmployeeComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InspectTaskDialog extends EntityDialog<Task>{

    private static final Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Task task;
    private final JLabel taskName = new JLabel();
    private final JTextArea  description = new JTextArea();
    private final JLabel customer = new JLabel();
    private final JLabel assignedTo = new JLabel();
    private final JLabel status = new JLabel();
    private JTable timeLogTable;
    private final JLabel category = new JLabel();
    private final JLabel loggedTime = new JLabel();
    private final JLabel allocatedTime = new JLabel();
    private final JLabel date = new JLabel();

    private final DataManager data;

    private final JPanel leftPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel logTablePanel = new JPanel();
    private final JPanel logBottomInfoPanel = new JPanel();




    public InspectTaskDialog(Task task, DataManager data){
        super(700, 650);
        this.data = data;
        this.task = task;
        this.timeLogTable = task.getTimeLogTable();

        SetupPanelLayouts();

        description.setPreferredSize(new Dimension(200, 100));
        description.setMinimumSize(new Dimension(200, 100));
        description.setMaximumSize(new Dimension(200, 100));

        setValues();
        addFields();
        //setPanel();
        FormatFields();
    }

    private void SetupPanelLayouts(){
        leftPanel.setLayout(new GridLayout(0, 2));

        rightPanel.setLayout(new BorderLayout());

        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 15));
        rightPanel.setBorder(new EmptyBorder(0, 15, 0, 0));

        super.getLabelPanel().setLayout(new GridLayout(0, 1));
        super.getComponentPanel().setLayout(new GridLayout(0, 1));

        super.getPanel().add(leftPanel);
        super.getPanel().add(rightPanel);

        leftPanel.add(super.getLabelPanel());
        leftPanel.add(super.getComponentPanel());

        logTablePanel.setBackground(Color.DARK_GRAY);
        logTablePanel.setPreferredSize(new Dimension(300, 400));
        logTablePanel.add(new JLabel("LOG TIME TABLE"));


        JScrollPane scrollPane = new JScrollPane(timeLogTable);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        componentWrapper.add(scrollPane);

        logTablePanel.add(scrollPane);



        JPanel timeLogtextLabelsPanel = new JPanel(new GridLayout(1, 3));

        JLabel allocatedTimeLabel = new JLabel("Allocated time", SwingConstants.CENTER);
        JLabel totalTimeLabel = new JLabel("Total logged", SwingConstants.CENTER);
        JLabel logTimeLabel = new JLabel("Log time", SwingConstants.CENTER);

        timeLogtextLabelsPanel.add(allocatedTimeLabel);
        timeLogtextLabelsPanel.add(totalTimeLabel);
        timeLogtextLabelsPanel.add(logTimeLabel);

        logBottomInfoPanel.setLayout(new GridLayout(1, 3));

        //JButton logButton = new JButton("Log 10h");
        // JButton allocatedButton = new JButton("Al 5h");
        JButton addLogTimeButton = MainWindow.createButton("", Icons.ADD_ICON,
                new LogTimeAction(data, this, task));

        allocatedTime.setPreferredSize(new Dimension(200, 80));
        loggedTime.setPreferredSize(new Dimension(200, 80));
        addLogTimeButton.setPreferredSize(new Dimension(200, 80));

        //this.allocatedTime.setPreferredSize(new Dimension(200, 80));

        logBottomInfoPanel.add(this.allocatedTime);
        logBottomInfoPanel.add(this.loggedTime);
        logBottomInfoPanel.add(addLogTimeButton);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(timeLogtextLabelsPanel, BorderLayout.NORTH);
        bottomPanel.add(logBottomInfoPanel, BorderLayout.SOUTH);

        rightPanel.add(logTablePanel, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

    }
    private void FormatFields(){
        centerOutText();
        setBorders();

        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);
    }

    private void setBorders(){
        taskName.setBorder(labelBorder);
        customer.setBorder(labelBorder);
        assignedTo.setBorder(labelBorder);
        status.setBorder(labelBorder);
        category.setBorder(BorderFactory.createLineBorder(task.getCategory().getColor(), 5));
        loggedTime.setBorder(labelBorder);
        allocatedTime.setBorder(labelBorder);
        date.setBorder(labelBorder);
        description.setBorder(labelBorder);
    }

    private void centerOutText() {
        taskName.setHorizontalAlignment(SwingConstants.CENTER);
        customer.setHorizontalAlignment(SwingConstants.CENTER);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        assignedTo.setHorizontalAlignment(SwingConstants.CENTER);
        status.setHorizontalAlignment(SwingConstants.CENTER);
        loggedTime.setHorizontalAlignment(SwingConstants.CENTER);
        allocatedTime.setHorizontalAlignment(SwingConstants.CENTER);
        date.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setValues() {
        taskName.setText(task.getNameOfTask());
        taskName.setBorder(taskName.getBorder());
        description.setText(task.getDescription());
        customer.setText(task.getCustomer());
        assignedTo.setText(task.getAssignedTo().toString());
        category.setText(task.getCategory().getName());

//        category.setBackground(task.getCategory().getColor());  \\TODO does not work
//        category.setForeground(CategoryComboboxRenderer.getRightTextColor(task.getCategory().getColor()));
//        System.out.println(task.getCategory().getColor().toString());

        status.setText(task.getStatus().toString());
        loggedTime.setText(task.getConvertedLoggedTimeString());
        allocatedTime.setText(task.getConvertedAllocatedTimeString());
        date.setText(task.getDueDate().toString());
    }

    private void addFields(){
        JButton addLogTimeButton = MainWindow.createButton("", Icons.ADD_ICON,
                new LogTimeAction(data, this, task));

        addCentered("Task name", taskName);
        addCentered("Description", description);
        addCentered("Customer", customer);
        addCentered("Category", category);
        addCentered("Assigned to", assignedTo);
        addCentered("Status", status);

//        JScrollPane scrollPane = new JScrollPane(timeLogTable);
//        scrollPane.setPreferredSize(new Dimension(200, 100));
//        addCentered("Time log table", scrollPane);

        //addCentered("Total logged time", loggedTime, addLogTimeButton);
        //addCentered("Allocated time", allocatedTime);
        addCentered("Due date", date);




    }

    public void updateLoggedTime(){
        loggedTime.setText(task.getConvertedLoggedTimeString());
    }
    @Override
    Task getEntity() {
        return task;
    }


}
