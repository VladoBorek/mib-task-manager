package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
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

/**
 * @author Vladimir Borek
 */
public class LogTimeAction extends AbstractAction{
    private final ActionType type;
    //private final JTable contentTable;
    private final DataManager data;
    private JComboBox<TimeUnit> timeUnitsComboBox = null;
    private final Task task;


    public LogTimeAction(ActionType type, DataManager data, Task task) {
        super("Log time " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.data = data;
        this.task = task;
    }


//    public LogTimeAction(ActionType type, JTable contentTable,
//                     DataManager data,
//                     JComboBox<TimeUnit> timeUnitsComboBox
//    ) {
//        super("Log time " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
//        this.type = type;
//        this.contentTable = contentTable;
//        this.data = data;
//        this.timeUnitsComboBox = timeUnitsComboBox;
//    }
    /**
     * Evoked when add button is clicked.
     * Decides which object is to be added and calls appropriate method.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO dialog switch for different types
//        var editClass = contentTable.getModel().getClass();
//        if (editClass == TaskTableModel.class && type == ActionType.TASK){
//            addTask();
//        }

        switch(type) {
            case TIME_UNIT:
                LogTime();
                break;
//            case CATEGORY:
//                if (this.categoryComboBox == null){
//                    addCategory();
//                }
//                else {
//                    addCategory(this.categoryComboBox);
//                }
//                break;
//            case TEMPLATE:
//                addTemplate();
//                break;
        }
    }

    /**
     * Opens a {@link TimeUnitDialog} window, creates a time unit and adds it to {@link TimeUnitListModel}
     */
    private void LogTime() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(data.getTimeUnits()::add);
    }

    /*
    Automatically updates combobox in task window when creating new time unit
     */
    private void LogTime(JComboBox<TimeUnit> timeUnitsComboBox) {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            data.getTimeUnits().add(newTimeUnit);
            DefaultComboBoxModel<TimeUnit> model = (DefaultComboBoxModel<TimeUnit>) timeUnitsComboBox.getModel();
            model.addElement(newTimeUnit);
            timeUnitsComboBox.setSelectedItem(newTimeUnit);
        });
    }

    private void addUnit(TimeUnit timeUnit){

    }


//
//public class LogTimeAction extends AbstractAction {
//
//    private final DataManager data;
//
//    private final JFrame frame;
//    private final Task task;
//
//    public LogTimeAction(DataManager data, JFrame frame, Task task) {
//        this.data = data;
//        this.frame = frame;
//        this.task = task;
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        var dialog = new LogTimeDialog(frame, data);
//        dialog.show(null, "Log Time").ifPresent(newTime -> {
//            task.setLoggedTime(task.getLoggedTime() + newTime);
//>>>>>>> 3e9ed7347563e78318282de0af06065c13c74114
//        });
//    }
}
