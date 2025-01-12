package cz.muni.fi.pv168.project.ui.model.panels.panelFactories;

import cz.muni.fi.pv168.project.ui.model.panels.timePanels.InspectTaskTimePanel;
import cz.muni.fi.pv168.project.ui.model.panels.timePanels.ManageTaskTemplateTimePanel;

import javax.swing.*;

/**
 * @author Vladimir Borek
 */
public class TimePanelFactory {
    public static ManageTaskTemplateTimePanel createPanel(JFormattedTextField allocatedTimeField, JPanel timeUnitPanel) {
        return new ManageTaskTemplateTimePanel(allocatedTimeField, timeUnitPanel);
    }

    public static InspectTaskTimePanel createPanel(JLabel allocatedTime, JLabel loggedTime, JButton addLogTimeButton) {
        return new InspectTaskTimePanel(allocatedTime, loggedTime, addLogTimeButton);
    }

}
