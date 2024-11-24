package cz.muni.fi.pv168.project.ui.actions.menu.category;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditCategoryAction extends EntityBaseAction {
    private final JComboBox<Category> comboBox;

    public EditCategoryAction(DataManager data, JComboBox<Category> comboBox) {
        super("Edit Category", Icons.MANAGE_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editCategory();
    }

    private void editCategory() {
        var category = (Category) comboBox.getSelectedItem();
        if (category == null) {
            return;
        }
        var cDialog = new CategoryDialog(category);
        cDialog.show(comboBox, "Edit Category").ifPresent(newCat -> {
            category.setName(newCat.getName());
            category.setColor(newCat.getColor());
        });
        data.getCategories().update(category);

        comboBox.setSelectedIndex(0);
    }
}
