package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.ActionType;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.renderers.CategoryComboboxRenderer;

import javax.swing.*;


public class ManageCategoriesDialog extends ManageDialog {

    public ManageCategoriesDialog(JFrame parent, DataManager data) {
        super(parent, data, ActionType.CATEGORY, "Manage categories", "Select a category:",
                categoriesCombobox(data));
    }

    private static JComboBox<Category> categoriesCombobox(DataManager data){
        var comboBox = new JComboBox<>(new ComboBoxModelAdapter<>(data.getCategories()));
        comboBox.setRenderer(new CategoryComboboxRenderer());
        CategoryComboboxRenderer.setCategoryComboboxColor(comboBox);
        comboBox.addActionListener(e -> {
            CategoryComboboxRenderer.setCategoryComboboxColor(comboBox);
        });

        return comboBox;
    }
}