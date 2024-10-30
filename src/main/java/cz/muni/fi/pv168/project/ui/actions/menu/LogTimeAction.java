package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
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
        dialog.show(null, "Add new time unit").ifPresent(data.getTimeUnits()::addUnit);
    }

    /*
    Automatically updates combobox in task window when creating new time unit
     */
    private void LogTime(JComboBox<TimeUnit> timeUnitsComboBox) {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            data.getTimeUnits().addUnit(newTimeUnit);
            DefaultComboBoxModel<TimeUnit> model = (DefaultComboBoxModel<TimeUnit>) timeUnitsComboBox.getModel();
            model.addElement(newTimeUnit);
            timeUnitsComboBox.setSelectedItem(newTimeUnit);
        });
    }
}
