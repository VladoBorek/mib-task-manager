package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.business.utils.CustomColor;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.EntityDialog;
import cz.muni.fi.pv168.project.util.ColorService;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog windows that will open when creating or editing categories
 */
public class CategoryDialog extends EntityDialog<Category> {

    private final JTextField nameField = new JTextField();
    private final JPanel colorPreviewPanel = new JPanel();
    private CustomColor selectedColor = ColorService.customColorFromAWTColor(Color.lightGray);
    private final DependencyProvider provider;

    public CategoryDialog(DependencyProvider provider) {
        this.provider = provider;
        nameField.setPreferredSize(new Dimension(200, 25));

        colorPreviewPanel.setPreferredSize(new Dimension(175, 25));
        colorPreviewPanel.setBackground(ColorService.customColorToAWTColor(selectedColor));
        colorPreviewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        var colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
        colorPanel.add(colorPreviewPanel);
        colorPanel.add(setUpColorButton());

        add("Name:", nameField);
        add("Color:", colorPanel);
        setPanel();
    }

    public CategoryDialog(Category category, DependencyProvider provider) {
        this(provider);
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
                    ColorService.customColorToAWTColor(selectedColor)
            );
            if (chosenColor != null) {
                setSelectedColor(ColorService.customColorFromAWTColor(chosenColor));
            }
        });
        return colorButton;
    }

    private void setSelectedColor(CustomColor color) {
        selectedColor = color;
        colorPreviewPanel.setBackground(ColorService.customColorToAWTColor(color));
    }

    @Override
    public Category getEntity() {
        Validator<Category> categoryValidator = provider.getCategoryValidator();
        var validation = categoryValidator.validate(new Category(null, nameField.getText(), selectedColor));

        if (!validation.isValid()) {
            Logger.error("Category failed Validation " + validation.getValidationErrors());
            PopUp.infoDialog(
                    validation.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (provider.getCategoryService().isNameDuplicate(nameField.getText())) {
            Logger.warn("User tried to create a category with a duplicate name: " + nameField.getText());
            PopUp.infoDialog(
                    "A category with this name already exists. Please choose a different name.",
                    "Duplicate Name",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        return new Category(null, nameField.getText(), selectedColor);
    }
}
