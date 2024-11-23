package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.AddAction;
import cz.muni.fi.pv168.project.ui.actions.menu.DeleteAction;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Vladimir Borek
 */
public abstract class ManageDialog extends JDialog {
    public ManageDialog(JFrame parent, DataManager data, ActionType actionType, String titleMessage, String textMessage, JComboBox comboBox){
        super(parent, titleMessage, true);
        setLayout(new BorderLayout());
        add(comboPanel(textMessage, comboBox), BorderLayout.CENTER);
        add(buttonsPanel(actionType, comboBox, data), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
    }

    private JPanel comboPanel(String text, JComboBox comboBox){
        var comboPanel = new JPanel();
        comboPanel.add(new JLabel(text));
        comboPanel.add(comboBox);
        return comboPanel;
    }

    private JPanel buttonsPanel(ActionType actionType, JComboBox comboBox, DataManager data){
        var buttonsPanel = new JPanel(new BorderLayout());

        JButton editButton = createButton("Edit", new EditAction(actionType, comboBox, data));
        JButton addButton = createButton("Add", new AddAction(actionType, data, null, comboBox));
        JButton deleteButton = createButton("Delete", new DeleteAction(actionType, comboBox, data));

        buttonsPanel.add(editButton, BorderLayout.NORTH);
        buttonsPanel.add(addButton, BorderLayout.CENTER);
        buttonsPanel.add(deleteButton, BorderLayout.SOUTH);

        return buttonsPanel;
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
