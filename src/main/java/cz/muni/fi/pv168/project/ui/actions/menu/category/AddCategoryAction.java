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
public class AddCategoryAction extends EntityBaseAction {

    private final JComboBox<Category> comboBox;

    public AddCategoryAction(DataManager data, JComboBox<Category> comboBox) {
        super("Add Category", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddCategoryAction(DataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addCategory();
    }

    private void addCategory() {
        var dialog = new CategoryDialog();
        dialog.show(null, "Add a new Category").ifPresent(newCategory -> {
            data.getCategories().add(newCategory);
            if (comboBox != null) {
                DefaultComboBoxModel<Category> model = (DefaultComboBoxModel<Category>) comboBox.getModel();
                model.addElement(newCategory);
                comboBox.setSelectedItem(newCategory);
            }
        });

    }
}
