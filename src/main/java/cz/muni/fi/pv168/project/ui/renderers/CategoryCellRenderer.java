package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.util.ColorService;

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
            cell.setBackground(ColorService.customColorToAWTColor(category.getColor()));
            cell.setForeground(CategoryComboboxRenderer.getRightTextColor(ColorService.customColorToAWTColor(category.getColor())));
        }
        return cell;
    }
}
