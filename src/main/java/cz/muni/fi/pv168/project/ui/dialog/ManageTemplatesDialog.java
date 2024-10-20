package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import java.awt.*;


public class ManageTemplatesDialog extends JDialog {
    public ManageTemplatesDialog(JFrame parent, TemplateListModel templates,
                                 CategoryListModel categories,
                                 TimeUnitListModel timeUnits) {
        super(parent, "Manage templates", true);
        setLayout(new BorderLayout());

        var comboBox = new JComboBox<>(new DefaultComboBoxModel<>(templates.toArray()));
        var comboPanel = new JPanel();
        comboPanel.add(new JLabel("Select a template:"));
        comboPanel.add(comboBox);

        JButton editButton = createButton("Edit", new EditAction(ActionType.TEMPLATE,
                null, categories, timeUnits, comboBox));
        JButton deleteButton = createButton("Delete", new DeleteAction(ActionType.TEMPLATE,
                null, categories, timeUnits, templates, comboBox));

        add(comboPanel, BorderLayout.NORTH);
        add(editButton, BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    private JButton createButton(String buttonText, Action a)
    {
        var button = new JButton(buttonText);
        button.addActionListener(a);
        button.setBackground(MainWindow.BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }
}