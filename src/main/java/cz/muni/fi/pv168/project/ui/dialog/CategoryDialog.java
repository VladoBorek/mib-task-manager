package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog windows that will open when creating or editing categories
 */
public class CategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JPanel colorPreviewPanel = new JPanel();
    private Color selectedColor = Color.lightGray;

    public CategoryDialog() {
        nameField.setPreferredSize(new Dimension(200, 25));

        colorPreviewPanel.setPreferredSize(new Dimension(175, 25));
        colorPreviewPanel.setBackground(selectedColor);
        colorPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        var colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
        colorPanel.add(colorPreviewPanel);
        colorPanel.add(setUpColorButton());

        add("Name:", nameField);
        add("Color:", colorPanel);
        setPanel();
    }

    public CategoryDialog(Category category) {
        this();
        nameField.setText(category.getName());
        setSelectedColor(category.getColor());
    }

    private JButton setUpColorButton() {
        var colorButton = new JButton("˅");
        colorButton.setPreferredSize(new Dimension(25, 25));
        colorButton.setFont(new Font("Dialog", Font.BOLD, 12));
        colorButton.addActionListener(e -> {
            var chosenColor = JColorChooser.showDialog(
                    colorPreviewPanel,
                    "Choose Category Color",
                    selectedColor
            );
            if (chosenColor != null) {
                setSelectedColor(chosenColor);
            }
        });
        return colorButton;
    }

    private void setSelectedColor(Color color) {
        selectedColor = color;
        colorPreviewPanel.setBackground(color);
    }

    @Override
    public Category getEntity() {
        Validator<Category> categoryValidator = new CategoryValidator();
        var validation = categoryValidator.validate(new Category(null, nameField.getText(), selectedColor));
        if (!validation.isValid()) {
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new Category(null, nameField.getText(), selectedColor);
    }
}
