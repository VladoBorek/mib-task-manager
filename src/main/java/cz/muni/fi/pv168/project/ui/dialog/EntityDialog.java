package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();

    EntityDialog() {
        var layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layout);

        labelPanel.setLayout(new GridLayout(0, 1));
        componentPanel.setLayout(new GridLayout(0, 1));
    }
    EntityDialog(int width, int height) {
//        var layout = new BoxLayout(panel, BoxLayout.X_AXIS);
//        panel.setLayout(layout);
//
//        labelPanel.setLayout(new GridLayout(0, 1));
//        componentPanel.setLayout(new GridLayout(0, 1));
//
//        panel.setPreferredSize(new Dimension(width, height));

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        labelPanel.setLayout(new GridLayout(0, 1));  // Labels in a single column
        componentPanel.setLayout(new GridLayout(0, 1));  // Components in a single column
        //buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));  // Buttons as needed
        buttonPanel.setLayout(new GridLayout(0, 1));  // Buttons as needed

        // Add panels to main panel
        panel.add(labelPanel);         // First column (labels)
        panel.add(componentPanel);     // Second column (components)
        panel.add(buttonPanel);        // Third column (buttons)

        panel.setPreferredSize(new Dimension(width, height));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);

        labelPanel.add(label);
        componentPanel.add(component, "wmin 250lp, grow");

    }

    void addCentered(String labelText, JComponent component) {
        addCentered(labelText, component, null);
//        var label = new JLabel(labelText);
//        label.setHorizontalAlignment(SwingConstants.CENTER);
//        labelPanel.add(label);
//
//        JPanel componentWrapper = new JPanel();
//        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
//        componentWrapper.add(component);
//        componentPanel.add(component, "wmin 250lp, grow");

        //componentPanel.add(Box.createHorizontalStrut(0));

    }

    void addCentered(String labelText, JComponent component, JButton  button) {
        var label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        labelPanel.add(label);

        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        componentWrapper.add(component);
        componentPanel.add(component, "wmin 250lp, grow");
        //componentPanel.add(button);

        if (button != null) {
            buttonPanel.add(button); // Add the button to the buttonPanel
        }
        else {
            buttonPanel.add(new JLabel(""));
        }

    }

    protected JPanel getPanel() {
        return panel;
    }

    void setPanel(){
        panel.add(labelPanel);
        panel.add(componentPanel);
        panel.add(buttonPanel);
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);
        if (result == OK_OPTION) {
            var entity = getEntity();
            if (entity == null) {
                return Optional.empty();
            }
            return Optional.of(entity);
        } else {
            return Optional.empty();
        }
    }
}