package cz.muni.fi.pv168.project.ui.dialog;

import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomTimeUnit;
import cz.muni.fi.pv168.project.model.TimeUnit;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog windows that will open when creating or editing time units
 */
public class NewCategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JColorChooser colorChooser = new JColorChooser();

    public NewCategoryDialog() {
        nameField.setMaximumSize(new Dimension(100, 50));
        add("Name", nameField);
        add("colour", colorChooser);
        setPanel();
    }


    @Override
    Category getEntity() {
        return new Category(nameField.getText(), colorChooser.getColor());
    }
}
