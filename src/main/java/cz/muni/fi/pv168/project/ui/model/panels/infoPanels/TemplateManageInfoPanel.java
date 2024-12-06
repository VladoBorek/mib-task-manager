package cz.muni.fi.pv168.project.ui.model.panels.infoPanels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class TemplateManageInfoPanel extends JPanel {
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();

    public TemplateManageInfoPanel(JTextField taskNameField, JTextField templateNameField,
                                   JTextField assignedToField, JPanel categoryPanel) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        labelPanel.setLayout(new GridLayout(0, 1, 0, 8));
        componentPanel.setLayout(new GridLayout(0, 1, 0, 8));

        this.add(labelPanel);
        this.add(componentPanel);
        addInfoFields(taskNameField, templateNameField, assignedToField, categoryPanel);
    }

    private void addInfoFields(JTextField taskNameField, JTextField templateNameField,
                               JTextField assignedToField, JPanel categoryPanel) {
        add("Template name", templateNameField);
        add("Task name", taskNameField);
        add("Category", categoryPanel);
        add("Assigned to", assignedToField);
    }

    private void add(String labelText, JComponent component) {
        labelPanel.add(new JLabel(labelText));
        componentPanel.add(component, "wmin 250lp, grow");
    }
}
