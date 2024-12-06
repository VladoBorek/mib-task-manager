package cz.muni.fi.pv168.project.ui.model.panels.panelFactories;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.ui.model.panels.infoPanels.TaskInspectInfoPanel;
import cz.muni.fi.pv168.project.ui.model.panels.infoPanels.TaskManageInfoPanel;
import cz.muni.fi.pv168.project.ui.model.panels.infoPanels.TemplateManageInfoPanel;

import javax.swing.*;

/**
 * @author Vladimir Borek
 */
public class InfoPanelFactory {
    public static TaskManageInfoPanel createPanel(JTextField taskNameField, JTextField customerField, JPanel categoryPanel,
                                                  JTextField assignedToName, JComboBox<Status> statusComboBox, DatePicker datePicker) {
        return new TaskManageInfoPanel(taskNameField, customerField, categoryPanel, assignedToName, statusComboBox, datePicker);
    }

    public static TaskInspectInfoPanel createPanel(JLabel taskName, JLabel customer, JLabel category, JLabel assignedTo,
                                                   JLabel status, JLabel date) {
        return new TaskInspectInfoPanel(taskName, customer, category, assignedTo, status, date);
    }

    public static TemplateManageInfoPanel createPanel(JTextField taskNameField, JTextField templateNameField,
                                                      JTextField assignedToField, JPanel categoryPanel) {
        return new TemplateManageInfoPanel(taskNameField, templateNameField, assignedToField, categoryPanel);
    }


}
