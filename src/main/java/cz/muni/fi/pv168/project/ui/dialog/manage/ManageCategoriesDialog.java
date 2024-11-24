package cz.muni.fi.pv168.project.ui.dialog.manage;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.category.AddCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.category.DeleteCategoryAction;
import cz.muni.fi.pv168.project.ui.actions.menu.category.EditCategoryAction;
import cz.muni.fi.pv168.project.ui.dialog.abstracts.ManageDialog;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;

import javax.swing.*;

import java.awt.*;

import static cz.muni.fi.pv168.project.ui.utils.UIElements.createActionsButtonPanel;
import static cz.muni.fi.pv168.project.ui.utils.UIElements.createComboPanel;

public class ManageCategoriesDialog extends ManageDialog {
    public ManageCategoriesDialog(JFrame parent, DataManager data) {
        super(parent,  "Manage categories");
        var comboBox = categoriesCombobox(data);
        add(createComboPanel("Select a Category: ", comboBox), BorderLayout.NORTH);
        add(createActionsButtonPanel(new AddCategoryAction(data, comboBox),
                new EditCategoryAction(data, comboBox),
                new DeleteCategoryAction(data, comboBox)), BorderLayout.SOUTH);
        set();
    }

    private static JComboBox<Category> categoriesCombobox(DataManager data) {
        var comboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategories()));
        comboBox.setRenderer(new CategoryComboboxRenderer());
        CategoryComboboxRenderer.setCategoryComboboxColor(comboBox);
        comboBox.addActionListener(e -> CategoryComboboxRenderer.setCategoryComboboxColor(comboBox));

        return comboBox;
    }
}