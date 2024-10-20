package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAction extends AbstractAction {

    private final JTable contentTable;
    private final JComboBox comboBox;
    private final CategoryListModel categories;
    private final TimeUnitListModel timeUnits;
    private final ActionType type;
    private final TemplateListModel templates;

    // TODO: SOMEHOW FIX RAW USE OF JComboBox
    public DeleteAction(ActionType type, JTable contentTable, CategoryListModel categories,
                        TimeUnitListModel timeUnits, TemplateListModel templates, JComboBox comboBox) {
        super("Edit", Icons.MANAGE_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.categories = categories;
        this.timeUnits = timeUnits;
        this.comboBox = comboBox;
        this.templates = templates;
    }
    @Override
    public void actionPerformed(ActionEvent e) {


        switch(type) {
            case TASK:
//                TODO
//                var selectedRows = contentTable.getSelectedRows();
//                if (selectedRows.length != 1) {
//                    throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
//                }
//                var taskTableModel = (TaskTableModel) contentTable.getModel();
//                int modelRow = contentTable.convertRowIndexToModel(selectedRows[0]);
//                var task = taskTableModel.getEntity(modelRow);
//
//                var tDialog = new TaskDialog(task, categories.toArray());
//                System.out.println(task.getNameOfTask());
//                tDialog.show(contentTable, "Edit Task").ifPresent(taskTableModel::updateRow);
                return;
            case CATEGORY:
                var category = (Category) comboBox.getSelectedItem();
                categories.removeCategory(category);
                comboBox.removeItem(category);
                return;
            case TIME_UNIT:
                var timeUnit = (TimeUnit) comboBox.getSelectedItem();
                timeUnits.removeCategory(timeUnit);
                comboBox.removeItem(timeUnit);
                return;
            case TEMPLATE:
                var template = (Template) comboBox.getSelectedItem();
                templates.removeTemplate(template);
                comboBox.removeItem(template);
                return;
        }
    }
}
