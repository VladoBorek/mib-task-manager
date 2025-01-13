package cz.muni.fi.pv168.project.ui.utils;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.category.AddCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.timeunit.AddTimeUnitAction;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        descriptionScrollPane.setPreferredSize(new Dimension(width, height));
        descriptionScrollPane.setMinimumSize(new Dimension(width, height));
        descriptionScrollPane.setMaximumSize(new Dimension(width, height));

        JPanel descriptionLabelPanel = new JPanel(new BorderLayout());

        JPanel titleDescriptionPanel = new JPanel(new BorderLayout());
        titleDescriptionPanel.add(new JLabel("Description:"));

        JPanel textDescriptionPanel = new JPanel(new BorderLayout());
        textDescriptionPanel.add(descriptionScrollPane);

        descriptionLabelPanel.add(titleDescriptionPanel, BorderLayout.NORTH);
        descriptionLabelPanel.add(textDescriptionPanel, BorderLayout.CENTER);

        return descriptionLabelPanel;
    }

    public static JTable createLogTimeInfoTable(LogTimeInfoTableModel model, Task task) {
        TableRowSorter<LogTimeInfoTableModel> sorter = new TableRowSorter<>(model);

        JTable logTimeTable = new JTable(model);

        sorter.setRowFilter(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, task.getId(), 0));
        logTimeTable.setRowSorter(sorter);

        var cmodel = logTimeTable.getColumnModel();
        var col = cmodel.getColumn(0);
        cmodel.removeColumn(col);

        logTimeTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        var idColumn = logTimeTable.getColumnModel().getColumn(0);
        var nameColumn = logTimeTable.getColumnModel().getColumn(1);
        var timeColumn = logTimeTable.getColumnModel().getColumn(2);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        idColumn.setCellRenderer(centerRenderer);
        nameColumn.setCellRenderer(centerRenderer);
        timeColumn.setCellRenderer(centerRenderer);

        return logTimeTable;
    }

    public static JPanel setupCategoryTwoPartPanel(JComboBox<Category> categoryComboBox, UIDataManager data) {
        categoryComboBox.setRenderer(new CategoryComboboxRenderer());

        var addCategoryButton = createButton("", Icons.ADD_ICON,
                new AddCategoryAction(data, categoryComboBox));

        CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox);
        categoryComboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(categoryComboBox));

        return createTwoPartPanel(categoryComboBox, addCategoryButton);
    }

    public static JPanel setupTimeUnitTwoPartPanel(JComboBox<TimeUnit> timeUnitComboBox, UIDataManager data) {
        var addTimeUnitButton = createButton("", Icons.ADD_ICON,
                new AddTimeUnitAction(data, timeUnitComboBox));
        return createTwoPartPanel(timeUnitComboBox, addTimeUnitButton);
    }

    public static JFormattedTextField createDecimalFormattedTextField() {
        NumberFormat format = new DecimalFormat("#.##");
        format.setGroupingUsed(false);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(true);
        formatter.setMinimum(0.0);
        formatter.setValueClass(Double.class);

        JFormattedTextField field = new JFormattedTextField(formatter);
        field.setValue(0.0);
        field.setHorizontalAlignment(SwingConstants.LEFT);

        formatTextFieldBehavior(field);

        return field;
    }

    static private void formatTextFieldBehavior(JFormattedTextField field) {
        final StringBuffer buffer = new StringBuffer();

        field.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                buffer.setLength(0);
                buffer.append(field.getText());
            }
        });

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }

                if (c == '.' && field.getText().contains(".")) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }

                buffer.setLength(0);
                buffer.append(field.getText());
            }
        });
    }
}



