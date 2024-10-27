package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Employee;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer for combobox of {@link Employee}, it displays the id and the name.
 */
public class EmployeeComboboxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Employee employee) {
            label.setText(employee.toString());
        }

        return label;
    }
}