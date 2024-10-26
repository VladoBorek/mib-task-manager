package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog windows that will open when creating or editing categories
 */
public class CategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JColorChooser colorChooser = new JColorChooser();

    public CategoryDialog() {
        nameField.setMaximumSize(new Dimension(10, 10));
        add("Name", nameField);
        add("colour", colorChooser);
        setPanel();
    }

    public CategoryDialog(Category category) {
        nameField.setMaximumSize(new Dimension(50, 10));
        nameField.setText(category.getName());
        colorChooser.setColor(category.getColor());
        add("Name", nameField);
        add("colour", colorChooser);
        setPanel();
    }


    @Override
    Category getEntity() {
        return new Category(nameField.getText(), colorChooser.getColor());
    }
}
