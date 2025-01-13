package cz.muni.fi.pv168.project.ui.model.panels.timePanels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class ManageTaskTemplateTimePanel extends JPanel {
    private final JFormattedTextField allocatedTimeField;
    private final JPanel timeUnitPanel;

    public ManageTaskTemplateTimePanel(JFormattedTextField allocatedTimeField, JPanel timeUnitPanel) {
        this.allocatedTimeField = allocatedTimeField;
        this.timeUnitPanel = timeUnitPanel;

        this.setLayout(new BorderLayout());
        this.add(setupTimeTitlesPanel(), BorderLayout.NORTH);
        this.add(setupTimeInfoPanel(), BorderLayout.SOUTH);

    }

    private JPanel setupTimeTitlesPanel() {
        JPanel timeTitlesPanel = new JPanel(new GridLayout(1, 2));

        timeTitlesPanel.add(new JLabel("Allocated time"));
        timeTitlesPanel.add(new JLabel("Time unit"));

        return timeTitlesPanel;
    }

    private JPanel setupTimeInfoPanel() {
        JPanel timeInfoPanel = new JPanel();

        timeInfoPanel.setLayout(new GridLayout(1, 2));
        timeInfoPanel.add(this.allocatedTimeField);
        timeInfoPanel.add(timeUnitPanel);

        return timeInfoPanel;
    }
}
