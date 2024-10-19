package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.actions.menu.EditAction;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;

import javax.swing.*;
import java.awt.*;


public class ManageCategoriesDialog extends JDialog {


    private JComboBox<Category> timeUnitComboBox;
    private TimeUnit selectedTimeUnit;
    private final CategoryListModel categories;

    public ManageCategoriesDialog(JFrame parent, CategoryListModel categories) {
        super(parent, "Manage categories", true);
        setLayout(new BorderLayout());

        this.categories = categories;
        timeUnitComboBox = new JComboBox<>(new DefaultComboBoxModel<>(categories.toArray()));
        JPanel comboPanel = new JPanel();
        comboPanel.add(new JLabel("Select time unit:"));
        comboPanel.add(timeUnitComboBox);

        JButton editButton = createButton("Edit", new EditAction(ActionType.TIME_UNIT,
                null ,categories, null));
//        JButton deleteButton = new JButton("Delete");
//
//        editButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                selectedTimeUnit = (TimeUnit) timeUnitComboBox.getSelectedItem();
//                dispose();
//            }
//        });

        add(comboPanel, BorderLayout.CENTER);
        add(editButton, BorderLayout.SOUTH);

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