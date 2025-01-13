package cz.muni.fi.pv168.project.ui.actions.menu.category;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditCategoryAction extends EntityBaseAction {
    private final JComboBox<Category> comboBox;

    public EditCategoryAction(UIDataManager data, JComboBox<Category> comboBox) {
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
        var cDialog = new CategoryDialog(category, data.getDependencyProvider());
        cDialog.show(comboBox, "Edit Category").ifPresent(newCat -> {
            category.setName(newCat.getName());
            category.setColor(newCat.getColor());
        });
        
        ExceptionHandler.exceptionPopUpHandler(
                () -> data.getCategoryListModel().update(category),
                "Input error",
                "Successfully saved changes.",
                "Edit was not successful."
        );
        comboBox.setSelectedIndex(0);
        data.getTemplateTableModel().refresh();
        data.getTaskTableModel().refresh();
    }
}
