package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Category;

import javax.swing.*;
import java.awt.*;

/**
 * Combobox renderer for {@link Category} objects, it sets the background color of the label
 * according to the color of the category.
 */
public class CategoryComboboxRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Category category) {
            label.setForeground(getRightTextColor(category.getColor()));
            label.setBackground(category.getColor());
        }
        return label;
    }

    public static void setCategoryComboboxColor (JComboBox combobox) {
        Category category = (Category) combobox.getSelectedItem();
        if (category == null) {
            return;
        }
        combobox.setForeground(getRightTextColor(category.getColor()));
        combobox.setBackground(category.getColor());
    }

    public static Color getRightTextColor(Color backgroundColor) {
        if (backgroundColor.getRed() + backgroundColor.getGreen() + backgroundColor.getBlue() < 382) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }
}
