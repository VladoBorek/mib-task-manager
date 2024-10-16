package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();

    EntityDialog() {
        var layout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(layout);

        labelPanel.setLayout(new GridLayout(0, 1));
        componentPanel.setLayout(new GridLayout(0, 1));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);

        labelPanel.add(label);
        componentPanel.add(component, "wmin 250lp, grow");

    }
    void setPanel(){
        panel.add(labelPanel);
        panel.add(componentPanel);
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }
}