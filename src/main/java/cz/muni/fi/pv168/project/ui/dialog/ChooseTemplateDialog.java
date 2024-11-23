package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;

import javax.swing.*;
import java.awt.*;


public class ChooseTemplateDialog extends JDialog {

    public ChooseTemplateDialog(JFrame parent, DataManager data) {
        super(parent, "Choose a template", true);
        setLayout(new BorderLayout());

        var comboBox = new JComboBox<>(new DefaultComboBoxModel<>(data.getTemplates().toArray(new Template[0])));
        var emptyTemplate = new Template();
        comboBox.addItem(emptyTemplate);
        comboBox.setSelectedItem(emptyTemplate);
        var comboPanel = new JPanel();

        comboPanel.add(new JLabel("Select a template:"));
        comboPanel.add(comboBox);

        JButton okButton = createButton("OK",
                new AddAction(ActionType.TASK, data, comboBox));

        add(comboPanel, BorderLayout.NORTH);
        add(okButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createButton(String buttonText, Action a) {
        var button = new JButton(buttonText);
        button.addActionListener(e -> {
            a.actionPerformed(e);
            dispose();
        });
        button.setBackground(MainWindow.BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }
}