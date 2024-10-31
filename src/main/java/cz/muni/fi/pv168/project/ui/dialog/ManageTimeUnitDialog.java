package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;


import javax.swing.*;
import java.awt.*;


/**
 * Dialog that will open when clicking on manage Time units button.
 * It offers the user to select which time unit to edit or delete.
 */
public class ManageTimeUnitDialog extends JDialog {

    private TimeUnit selectedTimeUnit;

    public ManageTimeUnitDialog(JFrame parent, DataManager data) {
        super(parent, "Manage time units", true);
        setLayout(new BorderLayout());

        var timeUnitComboBox = new JComboBox<>(new DefaultComboBoxModel<>(data.getTimeUnits().toArray()));
        JPanel comboPanel = new JPanel();
        comboPanel.add(new JLabel("Select time unit:"));
        comboPanel.add(timeUnitComboBox);

        JButton editButton = createButton("Edit",
                new EditAction(ActionType.TIME_UNIT, timeUnitComboBox, data, this));

        JButton deleteButton = createButton("Delete",
                new DeleteAction(ActionType.TIME_UNIT, timeUnitComboBox, data));
        add(comboPanel, BorderLayout.NORTH);
        add(editButton, BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public TimeUnit getSelectedTimeUnit() {
        return selectedTimeUnit;
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