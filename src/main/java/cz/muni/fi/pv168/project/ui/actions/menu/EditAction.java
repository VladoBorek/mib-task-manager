package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditAction extends AbstractAction {
    private final JComboBox comboBox;
    private final DataManager data;
    private final ActionType type;

    private ManageTimeUnitDialog manageTimeUnitDialog;

    public EditAction (ActionType type, JComboBox comboBox, DataManager data) {
        super("Edit", Icons.MANAGE_ICON);
        this.type = type;
        this.data = data;
        this.comboBox = comboBox;
    }

    public EditAction (ActionType type, JComboBox comboBox, DataManager data, ManageTimeUnitDialog manageTimeUnitDialog) {
        super("Edit", Icons.MANAGE_ICON);
        this.type = type;
        this.data = data;
        this.comboBox = comboBox;
        this.manageTimeUnitDialog = manageTimeUnitDialog;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(type) {
            case TASK:
                var selectedRows = data.getTaskTable().getSelectedRows();
                if (selectedRows.length != 1) {
                    throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
                }
                var taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
                int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
                var task = taskTableModel.getEntity(modelRow);

                var tDialog = new AddTaskDialog(task, data);
                tDialog.show(data.getTaskTable(), "Edit Task").ifPresent(taskTableModel::updateRow);
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
                data.getCategories().update(category);

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
                    timeunit.setShortName(newTimeUnit.getShortName());

                });
                data.getTimeUnits().update(timeunit);

                comboBox.setSelectedIndex(0);
                manageTimeUnitDialog.dispose();

                return;
            case TEMPLATE:

                Template template;

                if (comboBox != null){
                    template = (Template) comboBox.getSelectedItem();
                    if (template == null) {
                        return;
                    }
                }
                else {
                    selectedRows = data.getTemplateTable().getSelectedRows();
                    if (selectedRows.length != 1) {
                        throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
                    }

                    int model = data.getTemplateTable().convertRowIndexToModel(selectedRows[0]);
                    TemplateTableModel templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
                    template = templateTableModel.getEntity(model);

                }

                var templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
                var templateDialog = new TemplateDialog(data, template);
                templateDialog.show(comboBox, "Edit Template").ifPresent(templateTableModel::updateRow);
        }
    }
}
