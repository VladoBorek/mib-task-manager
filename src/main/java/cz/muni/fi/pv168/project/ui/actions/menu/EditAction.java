package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditAction extends AbstractAction {

    private final JTable contentTable;
    private final JComboBox comboBox;
    private final DataManager data;
    private final ActionType type;

    public EditAction (ActionType type, JTable contentTable, JComboBox comboBox, DataManager data) {
        super("Edit", Icons.MANAGE_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.data = data;
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

                var tDialog = new AddTaskDialog(task, data);
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
            case TEMPLATE:
                var template = (Template) comboBox.getSelectedItem();
                if (template == null) {
                    return;
                }
                var templateDialog = new TemplateDialog(data, template);
                templateDialog.show(comboBox, "Edit Template").ifPresent(newTemplate -> {
                    template.setName(newTemplate.getName());
                    template.setCategory(newTemplate.getCategory());
                    template.setAllocatedTime(newTemplate.getAllocatedTime());
                    template.setTimeUnit(newTemplate.getTimeUnit());
                });
                comboBox.setSelectedIndex(0);
                return;
        }
    }
}
