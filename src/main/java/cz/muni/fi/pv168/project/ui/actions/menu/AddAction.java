package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractAction {
    private final ActionType type; // type of add action
    private final JTable contentTable; // table on which the operation will be performed on
    private final CategoryListModel categories; // list of categories
    private final TimeUnitListModel timeUnits; // list of time units
    public AddAction(ActionType type, JTable contentTable, CategoryListModel categories, TimeUnitListModel timeUnits) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.categories = categories;
        this.timeUnits = timeUnits;
    }


    /**
     * Evoked when add button is clicked.
     * Decides which object is to be added and calls appropriate method.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO dialog switch for different types
        var editClass = contentTable.getModel().getClass();
        if (editClass == TaskTableModel.class && type == ActionType.TASK){
            addTask();
        }

        switch(type) {
            case TIME_UNIT:
                addTimeUnit();
                return;
            case CATEGORY:
                addCategory();
                return;
        }
    }

    /**
     * Opens a {@link TaskDialog} window, creates a task and adds it to table
     */
    private void addTask() {
        var taskTableModel = (TaskTableModel) contentTable.getModel();
        var dialog = new TaskDialog(new DemoDataGenerator().getTasks().get(0), categories.toArray());
        dialog.show(contentTable, "Add new Task").ifPresent(taskTableModel::addRow);
    }

    /**
     * Opens a {@link TimeUnitDialog} window, creates a time unit and adds it to {@link TimeUnitListModel}
     */
    private void addTimeUnit() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(timeUnits::addUnit);
    }

    private void addCategory() {
        var dialog = new CategoryDialog();
        dialog.show(null, "Add a new Category").ifPresent(categories::addCategory);
    }
}
