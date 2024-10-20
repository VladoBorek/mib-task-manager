package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditAction extends AbstractAction {

    private final JTable contentTable;
    private final JComboBox comboBox;
    private final CategoryListModel categories;
    private final TimeUnitListModel timeUnits;
    private final ActionType type;
    public EditAction (ActionType type, JTable contentTable, CategoryListModel categories,
                       TimeUnitListModel timeUnits, JComboBox comboBox) {
        super("Edit", Icons.MANAGE_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.categories = categories;
        this.timeUnits = timeUnits;
        this.comboBox = comboBox;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(type) {
            case TASK:
                var selectedRows = contentTable.getSelectedRows();
                if (selectedRows.length != 1) {
                    throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
                }
                var taskTableModel = (TaskTableModel) contentTable.getModel();
                int modelRow = contentTable.convertRowIndexToModel(selectedRows[0]);
                var task = taskTableModel.getEntity(modelRow);

                var tDialog = new TaskDialog(task, categories, timeUnits);
                System.out.println(task.getNameOfTask());
                tDialog.show(contentTable, "Edit Task").ifPresent(taskTableModel::updateRow);
                return;

            case CATEGORY:
                var category = (Category) comboBox.getSelectedItem();
                if (category == null) {
                    return;
                }
                var cDialog = new CategoryDialog(category);
                cDialog.show(comboBox, "Edit Category").ifPresent(newCat -> {
                    category.setName(newCat.getName());
                    category.setColor(newCat.getColor());
                });
                comboBox.setSelectedIndex(0);
                return;

            case TIME_UNIT:
                var timeunit = (TimeUnit) comboBox.getSelectedItem();
                if (timeunit == null) {
                    return;
                }
                var timeUnitDialog = new TimeUnitDialog(timeunit);
                timeUnitDialog.show(comboBox, "Edit Time Unit").ifPresent(newTimeUnit -> {
                    timeunit.setName(newTimeUnit.getName());
                    timeunit.setRate(newTimeUnit.getRate());
                });
                comboBox.setSelectedIndex(0);
                return;
        }
    }
}
