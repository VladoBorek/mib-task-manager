package cz.muni.fi.pv168.project.ui.model.panels.timePanels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class InspectTaskTimePanel extends JPanel {

    private final JLabel allocatedTime;
    private final JLabel loggedTime;
    private final JButton addLogTimeButton;

    public InspectTaskTimePanel(JLabel allocatedTime, JLabel loggedTime, JButton addLogTimeButton) {
        this.allocatedTime = allocatedTime;
        this.loggedTime = loggedTime;
        this.addLogTimeButton = addLogTimeButton;

        this.setLayout(new BorderLayout());
        this.add(setupTimeTitlesPanel(), BorderLayout.NORTH);
        this.add(setupTimeInfoPanel(), BorderLayout.SOUTH);
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

        timeInfoPanel.add(this.allocatedTime);
        timeInfoPanel.add(this.loggedTime);
        timeInfoPanel.add(addLogTimeButton);

        return timeInfoPanel;
    }
}
