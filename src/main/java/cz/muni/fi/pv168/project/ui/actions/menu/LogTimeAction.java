package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.LogTimeDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageCategoriesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTemplatesDialog;
import cz.muni.fi.pv168.project.ui.dialog.ManageTimeUnitDialog;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LogTimeAction extends AbstractAction {

    private final DataManager data;

    private final JFrame frame;
    private final Task task;

    public LogTimeAction(DataManager data, JFrame frame, Task task) {
        this.data = data;
        this.frame = frame;
        this.task = task;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new LogTimeDialog(frame, data);
        dialog.show(null, "Log Time").ifPresent(newTime -> {
            task.setLoggedTime(task.getLoggedTime() + newTime);
        });
    }
}
