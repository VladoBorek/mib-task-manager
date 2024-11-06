package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.business.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author Maroš Pavlík
 */
public class CategoryCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof Category category) {
            cell.setBackground(category.getColor());
            cell.setForeground(CategoryComboboxRenderer.getRightTextColor(category.getColor()));
        }
        return cell;
    }
}
