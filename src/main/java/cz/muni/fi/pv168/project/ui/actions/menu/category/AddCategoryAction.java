package cz.muni.fi.pv168.project.ui.actions.menu.category;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class AddCategoryAction extends EntityBaseAction {

    private final JComboBox<Category> comboBox;

    public AddCategoryAction(UIDataManager data, JComboBox<Category> comboBox) {
        super("Add Category", Icons.ADD_ICON, data);
        this.comboBox = comboBox;
    }

    public AddCategoryAction(UIDataManager data) {
        this(data, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new CategoryDialog(data.getDependencyProvider());
        dialog.show(null, "Add a new Category").ifPresent(newCategory -> {
            ExceptionHandler.exceptionPopUpHandler(
                    () -> data.getCategoryListModel().add(newCategory),
                    "Input error",
                    null,
                    ""
            );
            if (comboBox != null) {
                comboBox.setSelectedItem(newCategory);
            } else {
                PopUp.infoDialog(
                        "Category" + newCategory.getName() + " was added",
                        "New category added",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


}
