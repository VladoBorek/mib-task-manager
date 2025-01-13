package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.actions.menu.abstracts.EntityBaseAction;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.utils.ExceptionHandler;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author Marcel Nadzam
 */
public class EditTaskAction extends EntityBaseAction {

    public EditTaskAction(UIDataManager data) {
        super("Edit Task", Icons.MANAGE_ICON, data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        editTask();
    }

    private void editTask() {
        var selectedRows = data.getTaskTable().getSelectedRows();
        if (selectedRows.length != 1) {
            Logger.error("User tried to edit more than one (1) task.");
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
                    ExceptionHandler.exceptionPopUpHandler(
                            () -> taskTableModel.updateRow(task),
                            "Input error",
                            "Successfully saved changes.",
                            "Edit was not successful."
                    );
                }
        );
        data.getStatisticsTableModel().refreshStatistics();
    }

    private void updateTask(Task oldT, Task newT) {
        oldT.setName(newT.getName());
        oldT.setCustomer(newT.getCustomer());
        oldT.setAssignedTo(newT.getAssignedTo());
        oldT.setCategory(newT.getCategory());
        oldT.setStatus(newT.getStatus());

        var alocTime = newT.getConvertedAllocatedTime();
        oldT.setTimeUnit(newT.getTimeUnit());
        oldT.setConvertedAllocatedTime(alocTime);
        oldT.setDueDate(newT.getDueDate());
        oldT.setDescription(newT.getDescription());

        updateTaskLogs(oldT, newT);
    }

    private void updateTaskLogs(Task oldT, Task newT) {
        var logTimeTableModel = data.getLogTimeInfoTableModel();
        var taskLogs = findExistingLogs(oldT);

        taskLogs.forEach(taskLog -> {
            try {
                var actualNewTaskUnitTime = oldT.getLoggedTime() / newT.getTimeUnit().getRate();
                taskLog.setLoggedTime(actualNewTaskUnitTime);
                logTimeTableModel.updateRow(taskLog);

            } catch (ValidationException exception) {
                Logger.error("Transfer of Task log (id=" + taskLog.getId() + ") has failed." + exception.getMessage());
                PopUp.infoDialog(
                        exception.getValidationErrors(),
                        "Error validating time logs",
                        JOptionPane.ERROR_MESSAGE);
            }
            Logger.info("Transferred time log (id=" + taskLog.getId() + ",userName=" + taskLog.getUsername() + ") to Task ");

        });
    }

    private List<LogTimeInfo> findExistingLogs(Task task) {
        return data.getLogTimeInfoTableModel()
                .getAllRows().stream()
                .filter(log -> log.getTaskID().equals(task.getId()))
                .toList();
    }
}
