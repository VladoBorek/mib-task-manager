package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;

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
            Color backgroundColor = category.getColor();
            cell.setBackground(backgroundColor);

            if (backgroundColor.getRed() + backgroundColor.getGreen() + backgroundColor.getBlue() < 382) {
                cell.setForeground(Color.WHITE);
            } else {
                cell.setForeground(Color.BLACK);
            }
        } else {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }
        return cell;
    }
}
