package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EditAction extends AbstractAction {

    private final JTable contentTable;
    private final List<Category> categories;
    public EditAction (JTable contentTable, List<Category> categories){
        super("Edit", Icons.MANAGE_ICON);
        this.contentTable = contentTable;
        this.categories = categories;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        var selectedRows = contentTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        //TODO dialog switch for different types
        var editClass = contentTable.getModel().getClass();
        if (editClass == TaskTableModel.class){
            var taskTableModel = (TaskTableModel) contentTable.getModel();
            int modelRow = contentTable.convertRowIndexToModel(selectedRows[0]);
            var task = taskTableModel.getEntity(modelRow);

            var dialog = new TaskDialog(task, categories.toArray());
            System.out.println(task.getNameOfTask());
            dialog.show(contentTable, "Edit Task").ifPresent(taskTableModel::updateRow);
        }


    }
}
