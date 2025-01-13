package cz.muni.fi.pv168.project.ui.dialog.abstracts;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

public abstract class EntityDialog<E> {
    private final JPanel panel = new JPanel();
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();

    public EntityDialog() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        labelPanel.setLayout(new GridLayout(0, 1, 0, 8));
        componentPanel.setLayout(new GridLayout(0, 1, 0, 8));
    }

    public EntityDialog(int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
    }

    public void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);

        labelPanel.add(label);
        componentPanel.add(component, "wmin 250lp, grow");
    }

    public JPanel getPanel() {
        return panel;
    }

    public JPanel getLabelPanel() {
        return this.labelPanel;
    }

    public JPanel getComponentPanel() {
        return this.componentPanel;
    }


    public void setPanel() {
        panel.add(labelPanel);
        panel.add(componentPanel);
    }

    public abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        while (true) {
            int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                    OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);
            if (result == OK_OPTION) {
                var entity = getEntity();
                if (entity != null) {
                    return Optional.of(entity);
                }
            } else {
                return Optional.empty();
            }
        }
    }
}