package cz.muni.fi.pv168.project.ui.utils;

import cz.muni.fi.pv168.project.ui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marcel Nadzam
 */
public class UIElements {
    public static <E> JPanel createComboPanel(String text, JComboBox<E> comboBox) {
        var comboPanel = new JPanel();
        comboPanel.add(new JLabel(text));
        comboPanel.add(comboBox);
        return comboPanel;
    }

    public static JPanel createActionsButtonPanel(Action addAction,
                                                  Action editAction,
                                                  Action deleteAction) {
        var buttonsPanel = new JPanel(new BorderLayout());

        JButton addButton = createButton("Add", addAction);
        JButton editButton = createButton("Edit", editAction);
        JButton deleteButton = createButton("Delete", deleteAction);

        buttonsPanel.add(addButton, BorderLayout.CENTER);
        buttonsPanel.add(editButton, BorderLayout.NORTH);
        buttonsPanel.add(deleteButton, BorderLayout.SOUTH);

        return buttonsPanel;
    }

    public static JButton createButton(String buttonText, Action a) {
        var button = new JButton(buttonText);
        button.addActionListener(a);
        button.setBackground(MainWindow.BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }

    public static JButton createDialogClosingButton(String buttonText, Action a, Window parentWindow) {
        var button = new JButton(buttonText);
        button.addActionListener(e -> {
            a.actionPerformed(e);
            if (parentWindow != null) {
                parentWindow.dispose();
            }
        });
        button.setBackground(MainWindow.BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }
}
