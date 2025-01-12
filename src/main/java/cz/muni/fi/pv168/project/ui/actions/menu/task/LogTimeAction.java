package cz.muni.fi.pv168.project.ui.actions.menu.task;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.business.service.validation.common.NotNegativeDoubleValidator;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.task.LogTimeDialog;
import org.tinylog.Logger;

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

            data.getStatisticsTableModel().refreshStatistics();
            inspectTaskDialog.updateLoggedTime();

        } catch (ValidationException exception) {
            Logger.error("Logging of time for Task (id=" + task.getId() +",name=" + task.getName() + ") has failed." + exception.getMessage());
            PopUp.infoDialog(
                    exception.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
        Logger.info("Logged time for Task (id=" + task.getId() +",name=" + task.getName() + ") has failed.");

    }

    private void validateInput(Double newTimeInBaseUnits) {
        var validator = new NotNegativeDoubleValidator("Logged Time");
        validator.validate(newTimeInBaseUnits).intoException();
    }

    private void handleTimeLogsUpdate(Double newTimeInBaseUnits) {
        var newTimeInTaskUnits = newTimeInBaseUnits / task.getTimeUnit().getRate();

        data.getLogTimeInfoTableModel().addRow(
                new LogTimeInfo(newTimeInTaskUnits, data.getLoggedUser(), task.getId()));
    }

    private List<LogTimeInfo> findExistingLogs(User user) {
        return data.getLogTimeInfoTableModel()
                .getAllRows().stream()
                .filter(log -> log.getUserId().equals(user.id()) && log.getTaskID().equals(task.getId()))
                .toList();
    }
}
