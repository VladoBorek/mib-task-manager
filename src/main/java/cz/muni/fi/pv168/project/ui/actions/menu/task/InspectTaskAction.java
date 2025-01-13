package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Vladimir Borek
 */
public class InspectTaskAction extends EntityBaseAction {

    public InspectTaskAction(UIDataManager data) {
        super("Inspect Task", Icons.INSPECT_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var selectedRows = data.getTaskTable().getSelectedRows();
        if (selectedRows.length != 1) {
            Logger.error("User tried to inspect more than one (1) task.");
            PopUp.infoDialog("To inspect task, please select exactly one (1) task.",
                    "Invalid selected rows",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        var taskTableModel = data.getTaskTableModel();
        int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
        var task = taskTableModel.getEntity(modelRow);

        var tDialog = new InspectTaskDialog(task, data);
        tDialog.show(data.getTaskTable(), "Inspect Task").ifPresent(inspectedTask -> {
                    ExceptionHandler.exceptionPopUpHandler(
                            () -> taskTableModel.updateRow(inspectedTask),
                            "Input error",
                            null,
                            null
                    );
                }
        );
    }
}

