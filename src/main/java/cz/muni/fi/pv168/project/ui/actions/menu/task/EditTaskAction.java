package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.task.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import java.awt.event.ActionEvent;

/**
 * @author Marcel Nadzam
 */
public class EditTaskAction extends EntityBaseAction {

    public EditTaskAction(DataManager data) {
        super("Edit Task", Icons.MANAGE_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editTask();
    }

    private void editTask() {
        var selectedRows = data.getTaskTable().getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }

        var taskTableModel = data.getTaskTableModel();
        int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
        var task = taskTableModel.getEntity(modelRow);

        var tDialog = new AddTaskDialog(task, data);
        tDialog.show(data.getTaskTable(), "Edit Task").ifPresent(newTask -> {
                    updateTask(task, newTask);
                    taskTableModel.updateRow(task);
                }
        );

        // TODO: umh..
        ((StatisticsTableModel) data.getStatisticsTable().getModel()).refreshStatistics();
    }

    private static void updateTask(Task oldT, Task newT) {
        oldT.setName(newT.getName());
        oldT.setCustomer(newT.getCustomer());
        oldT.setAssignedTo(newT.getAssignedTo());
        oldT.setCategory(newT.getCategory());
        oldT.setStatus(newT.getStatus());
        oldT.setConvertedAllocatedTime(newT.getConvertedAllocatedTime());
        oldT.setDueDate(newT.getDueDate());
        oldT.setTimeUnit(newT.getTimeUnit());
    }
}
