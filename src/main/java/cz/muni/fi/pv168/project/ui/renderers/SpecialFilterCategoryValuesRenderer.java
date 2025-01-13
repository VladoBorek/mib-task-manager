package cz.muni.fi.pv168.project.ui.renderers;

import cz.muni.fi.pv168.project.ui.filters.values.SpecialFilterCategoryValues;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SpecialFilterCategoryValuesRenderer extends AbstractRenderer<SpecialFilterCategoryValues> {
    public SpecialFilterCategoryValuesRenderer() {
        super(SpecialFilterCategoryValues.class);
    }

    protected void updateLabel(JLabel label, SpecialFilterCategoryValues value) {
        if (Objects.requireNonNull(value) == SpecialFilterCategoryValues.ALL) {
            renderAll(label);
        }
    }

    private static void renderAll(JLabel label) {
        label.setText("(ALL)");
        label.setFont(label.getFont().deriveFont(Font.ITALIC));
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
    }
}
