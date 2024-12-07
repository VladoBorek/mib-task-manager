package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Vladimir Borek
 */
public class InspectTaskAction extends EntityBaseAction {

    public InspectTaskAction(DataManager data) {
        super("Inspect Task", Icons.INSPECT_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var selectedRows = data.getTaskTable().getSelectedRows();
        if (selectedRows.length != 1) {
            //throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
            PopUp.infoDialog("To inspect task, please select exactly one (1) task.",
                            "Invalid selected rows",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        var taskTableModel = data.getTaskTableModel();
        int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
        var task = taskTableModel.getEntity(modelRow);

        var tDialog = new InspectTaskDialog(task, data);
        tDialog.show(data.getTaskTable(), "Inspect Task").ifPresent( inspectedTask -> {
            try {
                taskTableModel.updateRow(inspectedTask);
            } catch (ValidationException exception) {
                PopUp.infoDialog(exception.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
            }
        }
        );
    }
}

