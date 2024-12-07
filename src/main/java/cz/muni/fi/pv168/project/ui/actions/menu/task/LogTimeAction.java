package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.business.service.validation.common.NotNegativeIntegerValidator;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.task.LogTimeDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;


public class LogTimeAction extends AbstractAction {
    private final UIDataManager data;
    private final InspectTaskDialog inspectTaskDialog;
    private final Task task;

    public LogTimeAction(UIDataManager data, InspectTaskDialog inspectTaskDialog, Task task) {
        this.data = data;
        this.inspectTaskDialog = inspectTaskDialog;
        this.task = task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new LogTimeDialog(data, task);

        try {
            dialog.show(null, "Log Time").ifPresent(newTimeInBaseUnits -> {
                validateInput(newTimeInBaseUnits);
                handleTimeLogsUpdate(newTimeInBaseUnits);
                task.setLoggedTime(task.getLoggedTime() + newTimeInBaseUnits);
            });

            ((StatisticsTableModel) data.getStatisticsTable().getModel()).refreshStatistics();
            inspectTaskDialog.updateLoggedTime();

        } catch (ValidationException exception) {
            PopUp.infoDialog(exception.getValidationErrors(), "Input error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateInput(Integer newTimeInBaseUnits) {
        var validator = new NotNegativeIntegerValidator("Logged Time");
        validator.validate(newTimeInBaseUnits).intoException();
    }

    private void handleTimeLogsUpdate(Integer newTimeInBaseUnits) {
        var newTimeInTaskUnits = newTimeInBaseUnits / task.getTimeUnit().getRate();
        var currentUser = data.getLoggedUser();
        var logTimeInfoService = data.getLogTimeInfoCrudService();
        var existingLogTimeInfos = findExistingLogs(currentUser, logTimeInfoService);

        if (existingLogTimeInfos.isEmpty()) {
            logTimeInfoService.create(new LogTimeInfo(newTimeInTaskUnits, currentUser, task.getId())).intoException();
        } else {
            updateLog(existingLogTimeInfos, logTimeInfoService, newTimeInTaskUnits);
        }
    }

    private List<LogTimeInfo> findExistingLogs(User user, CrudService<LogTimeInfo> logTimeInfoService) {
        return logTimeInfoService
                .findAll().stream()
                .filter(log -> log.getUserId().equals(user.id()) && log.getTaskID().equals(task.getId()))
                .toList();
    }

    private void updateLog(List<LogTimeInfo> existingLogTimeInfos, CrudService<LogTimeInfo> logTimeInfoService, Integer newTimeInTaskUnits) {
        var existingLog = existingLogTimeInfos.get(0);
        existingLog.setLoggedTime(existingLog.getLoggedTime() + newTimeInTaskUnits);
        logTimeInfoService.update(existingLog).intoException();
    }
}
