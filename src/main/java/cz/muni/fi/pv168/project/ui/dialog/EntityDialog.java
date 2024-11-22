package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();
    private final JPanel labelPanel = new JPanel();
    private final JPanel componentPanel = new JPanel();

    EntityDialog() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        labelPanel.setLayout(new GridLayout(0, 1, 0, 8));
        componentPanel.setLayout(new GridLayout(0, 1, 0, 8));
    }

    EntityDialog(int width, int height){
        panel.setPreferredSize(new Dimension(width, height));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);

        labelPanel.add(label);
        componentPanel.add(component, "wmin 250lp, grow");
    }

    public JPanel getPanel() {
        return panel;
    }
    public JPanel getLabelPanel(){
        return this.labelPanel;
    }

    public JPanel getComponentPanel(){
        return this.componentPanel;
    }


    void setPanel(){
        panel.add(labelPanel);
        panel.add(componentPanel);
    }

    public static JPanel createTwoPartPanel(JComponent comboBox, JComponent button) {
        var newPanel = new JPanel(new GridBagLayout());
        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(comboBox, constraints);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 1.0;
        newPanel.add(button, constraints);

        return newPanel;
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        while (true){
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