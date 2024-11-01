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
        // panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setLayout(new GridLayout(1, 2));

//        labelPanel.setLayout(new GridLayout(0, 1));
//        componentPanel.setLayout(new GridLayout(0, 1));
//
//        //buttonPanel.setLayout(new GridLayout(0, 1));
//
//        panel.add(labelPanel);
//        panel.add(componentPanel);

        //panel.add(buttonPanel);

        panel.setPreferredSize(new Dimension(width, height));
    }

    void add(String labelText, JComponent component) {
        var label = new JLabel(labelText);

        labelPanel.add(label);
        componentPanel.add(component, "wmin 250lp, grow");

    }

    void addCentered(String labelText, JComponent component) {
        addCentered(labelText, component, null);
    }

    void addCentered(String labelText, JComponent component, JButton  button) {
        var label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        labelPanel.add(label);

        JPanel componentWrapper = new JPanel();
        componentWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        componentWrapper.add(component);
        componentPanel.add(component, "wmin 250lp, grow");

        if (button != null) {
            buttonPanel.add(button);
        }
        else {
            buttonPanel.add(new JLabel(""));
        }

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
        // panel.add(buttonPanel);
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