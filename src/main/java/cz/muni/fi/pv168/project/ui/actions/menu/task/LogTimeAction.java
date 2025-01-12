package cz.muni.fi.pv168.project.ui.actions.menu.task;

import com.github.lgooddatepicker.zinternaltools.Pair;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.service.validation.ValidationException;
import cz.muni.fi.pv168.project.business.service.validation.common.NotNegativeDoubleValidator;
import cz.muni.fi.pv168.project.ui.UIDataManager;
import cz.muni.fi.pv168.project.ui.dialog.PopUp;
import cz.muni.fi.pv168.project.ui.dialog.task.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.task.LogTimeDialog;
import org.tinylog.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;


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
            dialog.show(null, "Log Time").ifPresent(timeAndUnit -> {
                validateInput(timeAndUnit.first);
                handleTimeLogsUpdate(timeAndUnit);
            });

            data.getStatisticsTableModel().refreshStatistics();
            inspectTaskDialog.updateLoggedTime();

        } catch (ValidationException exception) {
            Logger.error("Logging of time for Task (id=" + task.getId() + ",name=" + task.getName() + ") has failed." + exception.getMessage());
            PopUp.infoDialog(
                    exception.getValidationErrors(),
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
        Logger.info("Logged time for Task (id=" + task.getId() + ",name=" + task.getName() + ") has failed.");

    }

    private void validateInput(Double newTime) {
        var validator = new NotNegativeDoubleValidator("Logged Time");
        validator.validate(newTime).intoException();
    }

    private void handleTimeLogsUpdate(Pair<Double, TimeUnit> timeAndUnit) {
        var newTimeInSelectedUnits = timeAndUnit.first;
        var selectedUnit = timeAndUnit.second;
        //var newTimeInTaskUnits = newTimeInBaseUnits / task.getTimeUnit().getRate();
        var newTimeInBaseUnits = newTimeInSelectedUnits * selectedUnit.getRate();


        var currentUser = data.getLoggedUser();
        var logTimeTableModel = data.getLogTimeInfoTableModel();

        var newLog = new LogTimeInfo(newTimeInSelectedUnits, currentUser, task.getId(), selectedUnit);

        task.addLog(newLog);
        logTimeTableModel.addRow(newLog);
        task.setLoggedTime(task.getLoggedTime() + newTimeInBaseUnits);
    }

    //TODO: delete probably will become useless
//    private List<LogTimeInfo> findExistingLogs(User user) {
//        return data.getLogTimeInfoTableModel()
//                .getAllRows().stream()
//                .filter(log -> log.getUserId().equals(user.id()) && log.getTaskID().equals(task.getId()))
//                .toList();
//    }
}
