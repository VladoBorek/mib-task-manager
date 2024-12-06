package cz.muni.fi.pv168.project.ui.model.panels.infoPanels;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Status;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class TaskManageInfoPanel extends JPanel {
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();

    public TaskManageInfoPanel(JTextField taskNameField, JTextField customerField, JPanel categoryPanel,
                               JTextField assignedToName, JComboBox<Status> statusComboBox, DatePicker datePicker) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        labelPanel.setLayout(new GridLayout(0, 1, 0, 8));
        componentPanel.setLayout(new GridLayout(0, 1, 0, 8));

        this.add(labelPanel);
        this.add(componentPanel);

        addInfoFields(taskNameField, customerField, categoryPanel, assignedToName, statusComboBox, datePicker);
    }

    private void addInfoFields(JTextField taskNameField, JTextField customerField, JPanel categoryPanel,
                               JTextField assignedToName, JComboBox<Status> statusComboBox, DatePicker datePicker) {
        add("Task name", taskNameField);
        add("Customer", customerField);
        add("Category", categoryPanel);
        add("Assigned to Name", assignedToName);
        add("Status", statusComboBox);
        add("Due date", datePicker);
    }

    private void add(String labelText, JComponent component) {
        labelPanel.add(new JLabel(labelText));
        componentPanel.add(component, "wmin 250lp, grow");
    }
}
