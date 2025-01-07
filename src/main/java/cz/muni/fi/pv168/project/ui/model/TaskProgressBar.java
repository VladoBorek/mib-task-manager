package cz.muni.fi.pv168.project.ui.model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Class for creating a custom ProgressBar for showing the progress of a task in a table
 */
public class TaskProgressBar extends JProgressBar implements TableCellRenderer {


    public TaskProgressBar() {
        setStringPainted(true);
        setFont(getFont().deriveFont(Font.BOLD));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Float) {
            int progress = Math.round((Float) value);
            setValue(progress);
            setString(String.join(" ", String.valueOf(progress), "%"));
            if (progress >= 100) {
                setForeground(Color.RED);
            } else {
                setForeground(table.getForeground());
            }
        } else {
            setValue(0);
            setForeground(table.getForeground());
        }

        setBackground(table.getBackground());

        return this;
    }
}
