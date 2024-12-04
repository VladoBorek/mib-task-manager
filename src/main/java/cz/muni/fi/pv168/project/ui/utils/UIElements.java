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

    /**
     * @param buttonText Text to be shown on button
     * @param a          Action to be performed
     * @return Button with input characteristics
     */
    public static JButton createButton(String buttonText, Action a) {
        var button = new JButton(buttonText);
        button.addActionListener(a);
        button.setBackground(MainWindow.BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * @param buttonText Text to be shown on button
     * @param icon       Icon for the button
     * @param a          Action to be performed
     * @return Button with input characteristics
     */
    public static JButton createButton(String buttonText, Icon icon, Action a) {
        var button = new JButton(buttonText, icon);
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

    public static JPanel createDescriptionPanel(JTextArea descriptionArea, int width, int height) {
        JPanel descriptionLabelPanel = new JPanel(new BorderLayout());

        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.add(new JLabel("Description:"));

        JPanel textDescriptionPanel = new JPanel(new BorderLayout());
        textDescriptionPanel.add(descriptionArea);

        descriptionLabelPanel.add(titleDescriptionPanel, BorderLayout.NORTH);
        descriptionLabelPanel.add(textDescriptionPanel, BorderLayout.CENTER);

        descriptionArea.setPreferredSize(new Dimension(width, height));
        descriptionArea.setMinimumSize(new Dimension(width, height));
        descriptionArea.setMaximumSize(new Dimension(width, height));

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        return descriptionLabelPanel;
    }
}
