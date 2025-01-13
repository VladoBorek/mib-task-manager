package cz.muni.fi.pv168.project.ui.dialog.task;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.InfoPanelFactory;
import cz.muni.fi.pv168.project.ui.model.panels.panelFactories.TimePanelFactory;
import cz.muni.fi.pv168.project.ui.utils.UIElements;
import cz.muni.fi.pv168.project.util.Constants;
import org.tinylog.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.*;

/**
 * Dialog for adding and editing tasks
 */
public class AddTaskDialog extends EntityDialog<Task> {
    private final Task task;
    private final UIDataManager data;
    private final JTextField taskNameField = new JTextField();
    private final JTextField customerField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();
    private final JTextField assignedToName = new JTextField();
    private final JComboBox<Status> statusComboBox = new JComboBox<>(Status.values());
    private final JComboBox<Category> categoryComboBox;
    private final JComboBox<TimeUnit> timeUnitsComboBox;
    private final JFormattedTextField allocatedTimeField = UIElements.createDecimalFormattedTextField();
    private final DatePicker datePicker = new DatePicker();

    public AddTaskDialog(Task task, UIDataManager data) {
        this.task = task;
        this.data = data;

        this.timeUnitsComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getTimeUnitListModel()));
        this.categoryComboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategoryListModel()));

        datePicker.setDateToToday();

        setUpUI();

        if (task != null) {
            setValues();
        } else {
            timeUnitsComboBox.setSelectedItem(Constants.BASE_TIME_UNIT);
            allocatedTimeField.setText("0");
        }
    }

    private void setUpUI() {
        JPanel descriptionPanel = createDescriptionPanel(descriptionArea, 200, 50);
        JPanel timePanel = TimePanelFactory.createPanel(allocatedTimeField,
                setupTimeUnitTwoPartPanel(timeUnitsComboBox, data));
        JPanel infoPanel = InfoPanelFactory.createPanel(taskNameField, customerField,
                setupCategoryTwoPartPanel(categoryComboBox, data), assignedToName, statusComboBox, datePicker);

        super.getPanel().setLayout(new BorderLayout());
        super.getPanel().add(infoPanel, BorderLayout.NORTH);
        super.getPanel().add(descriptionPanel, BorderLayout.CENTER);
        super.getPanel().add(timePanel, BorderLayout.SOUTH);

        infoPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        descriptionPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        timePanel.setBorder(new EmptyBorder(5, 0, 0, 0));
    }

    private void setValues() {
        taskNameField.setText(task.getName());
        descriptionArea.setText(task.getDescription());
        customerField.setText(task.getCustomer());
        assignedToName.setText(task.getAssignedTo());
        categoryComboBox.setSelectedItem(task.getCategory());
        statusComboBox.setSelectedItem(task.getStatus());
        allocatedTimeField.setText(String.valueOf(task.getConvertedAllocatedTime()));
        datePicker.setDate(task.getDueDate());
        timeUnitsComboBox.setSelectedItem(task.getTimeUnit());
    }

    @Override
    public Task getEntity() {
        var timeunit = (TimeUnit) Objects.requireNonNull(timeUnitsComboBox.getSelectedItem());
        double allocatedTime = Double.parseDouble(allocatedTimeField.getText());
        Validator<Task> taskValidator = data.getDependencyProvider().getTaskValidator();

        var newTask = new Task(
                null, (Status) statusComboBox.getSelectedItem(),
                this.descriptionArea.getText(),
                (Category) categoryComboBox.getSelectedItem(),
                customerField.getText(),
                taskNameField.getText(),
                assignedToName.getText(),
                0d,
                allocatedTime * timeunit.getRate(),
                timeunit,
                datePicker.getDate());

        var validation = taskValidator.validate(newTask);

        if (!validation.isValid()) {
            Logger.error("Task failed Validation " + validation.getValidationErrors());
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return newTask;
    }


}
