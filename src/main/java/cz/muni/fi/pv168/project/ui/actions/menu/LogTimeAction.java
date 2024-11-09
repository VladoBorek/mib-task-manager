package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.dialog.InspectTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.LogTimeDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class LogTimeAction extends AbstractAction {
    private final DataManager data;
    private final InspectTaskDialog inspectTaskDialog;
    private final Task task;

    public LogTimeAction(DataManager data, InspectTaskDialog inspectTaskDialog, Task task) {
        this.data = data;
        this.inspectTaskDialog = inspectTaskDialog;
        this.task = task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new LogTimeDialog(data, task);
        dialog.show(null, "Log Time").ifPresent(newTime -> {
            task.setLoggedTime(task.getLoggedTime() + newTime);
        });
        inspectTaskDialog.updateLoggedTime();
    }
}
