package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class ManageCategoriesDialog extends JDialog {

    public ManageCategoriesDialog(JFrame parent, DataManager data) {
        super(parent, "Manage categories", true);
        setLayout(new BorderLayout());


        var comboBox = new JComboBox<>(new DefaultComboBoxModel<>(data.getCategories().toArray()));
        var comboPanel = new JPanel();
        comboPanel.add(new JLabel("Select a category:"));
        comboPanel.add(comboBox);

        JButton editButton = createButton("Edit", new EditAction(ActionType.CATEGORY,
                null , comboBox, data));
        JButton deleteButton = createButton("Delete", new DeleteAction(ActionType.CATEGORY,
                null ,comboBox , data));

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