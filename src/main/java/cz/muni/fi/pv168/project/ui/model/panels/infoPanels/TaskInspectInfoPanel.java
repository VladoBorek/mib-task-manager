package cz.muni.fi.pv168.project.ui.model.panels.infoPanels;

import cz.muni.fi.pv168.project.ui.model.panels.CellPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class TaskInspectInfoPanel extends JPanel {

    public TaskInspectInfoPanel(JLabel taskName, JLabel customer, JLabel category, JLabel assignedTo,
                                JLabel status, JLabel date) {
        this.setLayout(new GridLayout(3, 2));
        addInfoFields(taskName, customer, category, assignedTo, status, date);
    }

    private void addInfoFields(JLabel taskName, JLabel customer, JLabel category, JLabel assignedTo,
                               JLabel status, JLabel date) {
        this.add(new CellPanel("Task-name:", taskName));
        this.add(new CellPanel("Customer:", customer));
        this.add(new CellPanel("Category:", category));
        this.add(new CellPanel("Assigned to:", assignedTo));
        this.add(new CellPanel("Status:", status));
        this.add(new CellPanel("Due date:", date));
    }
}
