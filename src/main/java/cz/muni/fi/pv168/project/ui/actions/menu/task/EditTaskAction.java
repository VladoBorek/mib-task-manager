package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.DataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

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
            //throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
            PopUp.infoDialog("To edit task, please select exactly one (1) task.",
                    "Invalid selected rows",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        var taskTableModel = data.getTaskTableModel();
        int modelRow = data.getTaskTable().convertRowIndexToModel(selectedRows[0]);
        var task = taskTableModel.getEntity(modelRow);

        var tDialog = new AddTaskDialog(task, data);
        tDialog.show(data.getTaskTable(), "Edit Task").ifPresent(newTask -> {
                    updateTask(task, newTask);
                    try {
                        taskTableModel.updateRow(task);
                    } catch (ValidationException exception) {
                        PopUp.infoDialog(exception.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );

        // TODO: umh..
        ((StatisticsTableModel) data.getStatisticsTable().getModel()).refreshStatistics();
    }

    private void updateTask(Task oldT, Task newT) {
        oldT.setName(newT.getName());
        oldT.setCustomer(newT.getCustomer());
        oldT.setAssignedTo(newT.getAssignedTo());
        oldT.setCategory(newT.getCategory());
        oldT.setStatus(newT.getStatus());
        oldT.setConvertedAllocatedTime(newT.getConvertedAllocatedTime());
        oldT.setDueDate(newT.getDueDate());
        oldT.setTimeUnit(newT.getTimeUnit());
        oldT.setDescription(newT.getDescription());

        updateTaskLogs(oldT, newT);
    }

    private void updateTaskLogs(Task oldT, Task newT) {
        var logTimeInfoService = data.getLogTimeInfoCrudService();
        var taskLogs = findExistingLogs(oldT, logTimeInfoService);

        taskLogs.forEach(taskLog -> {
            try {
                var actualNewTaskUnitTime = oldT.getLoggedTime() / newT.getTimeUnit().getRate();
                taskLog.setLoggedTime(actualNewTaskUnitTime);
                logTimeInfoService.update(taskLog).intoException();

            } catch (ValidationException exception) {
                PopUp.infoDialog(exception.getValidationErrors(), "Error validating time logs", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private List<LogTimeInfo> findExistingLogs(Task task, CrudService<LogTimeInfo> logTimeInfoService) {
        return logTimeInfoService
                .findAll().stream()
                .filter(log -> log.getTaskID().equals(task.getId()))
                .toList();
    }
}
