package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.validation.LogTimeInfoValidator;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.LogTimeAction;
import cz.muni.fi.pv168.project.ui.model.CellPanel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class InspectTaskDialog extends EntityDialog<Task> {

    private static final Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Task task;
    private final JLabel taskName = new JLabel();
    private final JTextArea description = new JTextArea();
    private final JLabel customer = new JLabel();
    private final JLabel assignedTo = new JLabel();
    private final JLabel status = new JLabel();
    private final JTable timeLogTable;
    private final JLabel category = new JLabel();
    private final JLabel loggedTime = new JLabel();
    private final JLabel allocatedTime = new JLabel();
    private final JLabel date = new JLabel();
    private final DataManager data;
    private final JPanel leftPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();

    private LogTimeInfoTableModel model = null;
    private JTable logTimeTable;

    public InspectTaskDialog(Task task, DataManager data) {
        super(550, 250);

        this.data = data;
        this.task = task;
        this.timeLogTable = createLogTimeInfoTable();

        setValues();
        FormatFields();
        SetupPanels();
    }
    public JTable createLogTimeInfoTable(){
        var val = new LogTimeInfoValidator();
        var repo = new InMemoryRepository<LogTimeInfo>(new ArrayList<>());
        var newCrud = new BaseCrudService<LogTimeInfo>(repo, val);
        this.model = new LogTimeInfoTableModel(newCrud);

        for (var log:data.getLogTimeInfoCrudService().findAll()) {
            if (log.getTaskID() == task.getId()){
                model.addRow(log);
            }
        }
        this.logTimeTable = new JTable(model);



        this.logTimeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        this.logTimeTable.setAutoCreateRowSorter(true);

        var idColumn = this.logTimeTable.getColumnModel().getColumn(0);
        var nameColumn = this.logTimeTable.getColumnModel().getColumn(1);
        var timeColumn = this.logTimeTable.getColumnModel().getColumn(2);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        idColumn.setCellRenderer(centerRenderer);
        nameColumn.setCellRenderer(centerRenderer);
        timeColumn.setCellRenderer(centerRenderer);

        return this.logTimeTable;
    }



    private void SetupPanels() {
        super.getPanel().setLayout(new GridLayout(1, 2));
        leftPanel.setLayout(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());

        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 15));
        rightPanel.setBorder(new EmptyBorder(0, 15, 0, 0));

        super.getLabelPanel().setLayout(new GridLayout(0, 1));
        super.getComponentPanel().setLayout(new GridLayout(0, 1));

        super.getPanel().add(leftPanel);
        super.getPanel().add(rightPanel);

        leftPanel.add(setupInfoPanel());
        leftPanel.add(setupDescriptionPanel(), BorderLayout.SOUTH);

        rightPanel.add(setupLogTablePanel(), BorderLayout.CENTER);
        rightPanel.add(setupBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel setupBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(setupTimeTitlesPanel(), BorderLayout.NORTH);
        bottomPanel.add(setupTimeInfoPanel(), BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JPanel setupTimeTitlesPanel() {
        JPanel timeTitlesPanel = new JPanel(new GridLayout(1, 3));

        timeTitlesPanel.add(new JLabel("Allocated time", SwingConstants.CENTER));
        timeTitlesPanel.add(new JLabel("Total logged", SwingConstants.CENTER));
        timeTitlesPanel.add(new JLabel("Log time", SwingConstants.CENTER));

        return timeTitlesPanel;
    }

    private JPanel setupTimeInfoPanel() {
        JPanel timeInfoPanel = new JPanel();

        timeInfoPanel.setLayout(new GridLayout(1, 3));
        JButton addLogTimeButton = MainWindow.createButton("", Icons.ADD_ICON,
                new LogTimeAction(data, this, task));
        //allocatedTime.setPreferredSize(new Dimension(200, 80));
        //loggedTime.setPreferredSize(new Dimension(200, 80));
        // addLogTimeButton.setPreferredSize(new Dimension(200, 80));

        timeInfoPanel.add(this.allocatedTime);
        timeInfoPanel.add(this.loggedTime);
        timeInfoPanel.add(addLogTimeButton);

        return timeInfoPanel;
    }

    private JPanel setupLogTablePanel() {
        JPanel logTablePanel = new JPanel();
        logTablePanel.setBackground(new Color(211, 211, 211));
        //logTablePanel.setPreferredSize(new Dimension(300, 250));
        logTablePanel.add(new JLabel("LOG TIME TABLE"));

        JScrollPane scrollPane = new JScrollPane(timeLogTable);
        //scrollPane.setPreferredSize(new Dimension(300, 250));
        scrollPane.setPreferredSize(new Dimension(230, 160));
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        componentWrapper.add(scrollPane);

        logTablePanel.add(scrollPane);
        return logTablePanel;
    }

    private JPanel setupInfoPanel() {
        JPanel infoLabelsPanel = new JPanel(new GridLayout(3, 2));

        infoLabelsPanel.add(new CellPanel("Task-name:", taskName));
        infoLabelsPanel.add(new CellPanel("Customer:", customer));
        infoLabelsPanel.add(new CellPanel("Category:", category));
        infoLabelsPanel.add(new CellPanel("Assigned to:", assignedTo));
        infoLabelsPanel.add(new CellPanel("Status:", status));
        infoLabelsPanel.add(new CellPanel("Due date:", date));

        return infoLabelsPanel;
    }

    private JPanel setupDescriptionPanel() {
        JPanel descriptionLabelPanel = new JPanel(new BorderLayout());

        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.add(new JLabel("Description:"));

        JPanel textDescriptionPanel = new JPanel(new BorderLayout());
        textDescriptionPanel.add(description);

        descriptionLabelPanel.add(titleDescriptionPanel, BorderLayout.NORTH);
        descriptionLabelPanel.add(textDescriptionPanel, BorderLayout.CENTER);

        description.setPreferredSize(new Dimension(200, 100));
        description.setMinimumSize(new Dimension(200, 100));
        description.setMaximumSize(new Dimension(200, 100));

        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setOpaque(false);

        return descriptionLabelPanel;
    }

    private void FormatFields() {
        centerOutText();
        setBorders();
    }

    private void setBorders() {
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
        status.setText(task.getStatus().toString());
        loggedTime.setText(task.getConvertedLoggedTimeString());
        allocatedTime.setText(task.getConvertedAllocatedTimeString());
        date.setText(task.getDueDate().toString());
    }

    public void updateLoggedTime() {
        loggedTime.setText(task.getConvertedLoggedTimeString());
        model.refresh();
    }

    @Override
    public Task getEntity() {
        return task;
    }
}
