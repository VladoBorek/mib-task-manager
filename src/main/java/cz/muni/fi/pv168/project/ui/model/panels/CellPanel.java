package cz.muni.fi.pv168.project.ui.model.panels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Vladimir Borek
 */
public class CellPanel extends JPanel {
    public CellPanel(String text, JLabel label) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));

        JPanel textLabelPanel = new JPanel(new BorderLayout());
        textLabelPanel.add(new JLabel(text));

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label);

        add(textLabelPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.CENTER);
    }
}
