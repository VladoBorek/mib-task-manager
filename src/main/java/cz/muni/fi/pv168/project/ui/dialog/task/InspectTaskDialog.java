package cz.muni.fi.pv168.project.ui.dialog.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.task.LogTimeAction;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.InfoPanelFactory;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.TimePanelFactory;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.UIElements;
import cz.muni.fi.pv168.project.util.ColorService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createButton;
import static cz.muni.fi.pv168.project.ui.utils.UIElements.createLogTimeInfoTable;

public class InspectTaskDialog extends EntityDialog<Task> {

    private static final Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Task task;
    private final JLabel taskName = new JLabel();
    private final JTextArea description = new JTextArea();
    private final JLabel customer = new JLabel();
    private final JLabel assignedTo = new JLabel();
    private final JLabel status = new JLabel();
    private final JLabel category = new JLabel();
    private final JLabel loggedTime = new JLabel();
    private final JLabel allocatedTime = new JLabel();
    private final JLabel date = new JLabel();
    private final UIDataManager data;
    private final LogTimeInfoTableModel logTimeInfoTableModel;
    private final JTable logTimeTable;

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public InspectTaskDialog(Task task, UIDataManager data) {
        super(550, 250);

        this.data = data;
        this.task = task;
        this.logTimeInfoTableModel = data.getLogTimeInfoTableModel();
        this.logTimeTable = createLogTimeInfoTable(this.logTimeInfoTableModel, this.task);

        setValues();
        FormatFields();
        SetupPanels();
    }

    private void SetupPanels() {
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

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
        leftPanel.add(createDescriptionPanel(), BorderLayout.SOUTH);

        rightPanel.add(setupLogTablePanel(), BorderLayout.CENTER);
        rightPanel.add(setupTimePanel(), BorderLayout.SOUTH);
    }

    private JPanel setupTimePanel() {
        return TimePanelFactory.createPanel(
                allocatedTime,
                loggedTime,
                createButton("", Icons.ADD_ICON, new LogTimeAction(data, this, task)));
    }

    private JPanel setupInfoPanel() {
        return InfoPanelFactory.createPanel(taskName, customer, category, assignedTo, status, date);
    }

    private JPanel createDescriptionPanel() {
        description.setEditable(false);
        description.setOpaque(false);
        return UIElements.createDescriptionPanel(description, 200, 100);
    }

    private JPanel setupLogTablePanel() {
        JPanel logTablePanel = new JPanel();
        logTablePanel.setBackground(new Color(211, 211, 211));
        logTablePanel.add(new JLabel("LOG TIME TABLE"));

        JScrollPane scrollPane = new JScrollPane(logTimeTable);
        scrollPane.setPreferredSize(new Dimension(230, 160));
        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        componentWrapper.add(scrollPane);

        logTablePanel.add(scrollPane);
        return logTablePanel;
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
        category.setBorder(BorderFactory.createLineBorder(ColorService.customColorToAWTColor(task.getCategory().getColor()), 5));
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
        taskName.setText(task.getName());
        taskName.setBorder(taskName.getBorder());
        description.setText(task.getDescription());
        customer.setText(task.getCustomer());
        assignedTo.setText(task.getAssignedTo());
        category.setText(task.getCategory().getName());
        status.setText(task.getStatus().toString());

        allocatedTime.setText(DECIMAL_FORMAT.format(task.getConvertedAllocatedTime()) + " " + task.getTimeUnit().getShortName());
        loggedTime.setText(DECIMAL_FORMAT.format(task.getConvertedLoggedTime()) + " " + task.getTimeUnit().getShortName());


        date.setText(task.getDueDate().toString());
    }

    public void updateLoggedTime() {
        loggedTime.setText(DECIMAL_FORMAT.format(task.getConvertedLoggedTime()) + " " + task.getTimeUnit().getShortName());
        logTimeInfoTableModel.refresh();
    }

    @Override
    public Task getEntity() {
        return task;
    }
}
