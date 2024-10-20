package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.TaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.TemplateListModel;
import cz.muni.fi.pv168.project.ui.model.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractAction {
    private final ActionType type;
    private final JTable contentTable;
    private final CategoryListModel categories;
    private final TimeUnitListModel timeUnits;
    private final TemplateListModel templates;


    public AddAction(ActionType type, JTable contentTable,
                     CategoryListModel categories,
                     TimeUnitListModel timeUnits,
                     TemplateListModel templates) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.categories = categories;
        this.timeUnits = timeUnits;
        this.templates = templates;
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
                break;
            case CATEGORY:
                addCategory();
                break;
            case TEMPLATE:
                addTemplate();
                break;
        }
    }

    /**
     * Opens a {@link TaskDialog} window, creates a task and adds it to table
     */
    private void addTask() {
        var taskTableModel = (TaskTableModel) contentTable.getModel();
        var dialog = new TaskDialog(null, categories, timeUnits);
        dialog.show(contentTable, "Add new Task").ifPresent(taskTableModel::addRow);
    }

    /**
     * Opens a {@link TimeUnitDialog} window, creates a time unit and adds it to {@link TimeUnitListModel}
     */
    private void addTimeUnit() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(timeUnits::addUnit);
    }

    /**
     * Opens a {@link CategoryDialog} window, creates a category and adds it to {@link CategoryListModel}
     */
    private void addCategory() {
        var dialog = new CategoryDialog();
        dialog.show(null, "Add a new Category").ifPresent(categories::addCategory);
    }

    private void addTemplate() {
        var dialog = new TemplateDialog(categories, timeUnits, null);
        dialog.show(null, "Add a new Template").ifPresent(templates::addTemplate);
    }
}
