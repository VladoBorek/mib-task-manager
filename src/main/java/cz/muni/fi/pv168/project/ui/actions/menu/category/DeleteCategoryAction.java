package cz.muni.fi.pv168.project.ui.actions.menu.category;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class DeleteCategoryAction extends EntityBaseAction {
    private final JComboBox<Category> comboBox;

    public DeleteCategoryAction(UIDataManager data, JComboBox<Category> comboBox) {
        super("Delete Category", Icons.DELETE_ICON, data);
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deleteCategory();
    }

    private void deleteCategory() {
        var category = (Category) comboBox.getSelectedItem();
        if (category == null) {
            return;
        }
        data.getCategoryListModel().remove(category);
        comboBox.setSelectedItem(null);
    }
}
