package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;
import cz.muni.fi.pv168.project.ui.actions.menu.TemplateChosenAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import java.awt.*;


public class ChooseTemplateDialog extends JDialog {

    public ChooseTemplateDialog(JFrame parent, DataManager data,
                                JTable contentTable) {
        super(parent, "Choose a template", true);
        setLayout(new BorderLayout());

        var comboBox = new JComboBox<Template>(new DefaultComboBoxModel<>(data.getTemplates().toArray()));
        comboBox.addItem(new Template());
        var comboPanel = new JPanel();
        comboPanel.add(new JLabel("Select a template:"));
        comboPanel.add(comboBox);

        JButton okButton = createButton("OK",
                new AddAction(ActionType.TASK, contentTable, data, comboBox));

        add(comboPanel, BorderLayout.NORTH);
        add(okButton, BorderLayout.SOUTH);

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