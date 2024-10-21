package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.model.DataManager;
import cz.muni.fi.pv168.project.model.Task;
import cz.muni.fi.pv168.project.model.Template;
import cz.muni.fi.pv168.project.model.TimeUnit;
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
import java.util.Objects;

public class AddAction extends AbstractAction {
    private final ActionType type;
    private final JTable contentTable;
    private final DataManager data;
    private final JComboBox<Template> chosenTemplate;
    private JComboBox<TimeUnit> timeUnitsComboBox = null;
    private JComboBox<Object> categoryComboBox = null;




    public AddAction(ActionType type, JTable contentTable,
                     DataManager data,
                     JComboBox<Template> chosenTemplate) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.data = data;
        this.chosenTemplate = chosenTemplate;
    }


    public AddAction(ActionType type, JTable contentTable,
                     DataManager data,
                     JComboBox<Template> chosenTemplate,
                     JComboBox<TimeUnit> timeUnitsComboBox,
                     JComboBox<Object> categoryComboBox
                     ) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.contentTable = contentTable;
        this.data = data;
        this.chosenTemplate = chosenTemplate;
        this.categoryComboBox = categoryComboBox;
        this.timeUnitsComboBox = timeUnitsComboBox;
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
                if (this.timeUnitsComboBox == null){
                    addTimeUnit();
                }
                else {
                    addTimeUnit(this.timeUnitsComboBox);
                }
                break;
            case CATEGORY:
                if (this.categoryComboBox == null){
                    addCategory();
                }
                else {
                    addCategory(this.categoryComboBox);
                }
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
        TaskDialog dialog;
        if (((Template) Objects.requireNonNull(chosenTemplate.getSelectedItem()))
                .getTemplateName().compareTo("<Don't use a template>") == 0) {
            dialog = new TaskDialog(null, data);
        } else {
            dialog = new TaskDialog(new Task((Template) chosenTemplate.getSelectedItem()), data);
        }

        dialog.show(contentTable, "Add new Task").ifPresent(taskTableModel::addRow);
    }

    /**
     * Opens a {@link TimeUnitDialog} window, creates a time unit and adds it to {@link TimeUnitListModel}
     */
    private void addTimeUnit() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(data.getTimeUnits()::addUnit);
    }

    /*
    Automatically updates combobox in task window when creating new time unit
     */
    private void addTimeUnit(JComboBox<TimeUnit> timeUnitsComboBox) {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            data.getTimeUnits().addUnit(newTimeUnit);
            DefaultComboBoxModel<TimeUnit> model = (DefaultComboBoxModel<TimeUnit>) timeUnitsComboBox.getModel();
            model.addElement(newTimeUnit);
            timeUnitsComboBox.setSelectedItem(newTimeUnit);
        });
    }

    /**
     * Opens a {@link CategoryDialog} window, creates a category and adds it to {@link CategoryListModel}
     */
    private void addCategory() {
        var dialog = new CategoryDialog();
        dialog.show(null, "Add a new Category").ifPresent(data.getCategories()::addCategory);
    }

    /*
    Automatically updates combobox in task window when creating new category
     */
    private void addCategory(JComboBox<Object> categoryComboBox) {
        var dialog = new CategoryDialog();
        dialog.show(null, "Add a new Category").ifPresent(newCategory -> {
            data.getCategories().addCategory(newCategory);
            DefaultComboBoxModel<Object> model = (DefaultComboBoxModel<Object>) categoryComboBox.getModel();
            model.addElement(newCategory);
            categoryComboBox.setSelectedItem(newCategory);
        });
    }

    private void addTemplate() {
        var dialog = new TemplateDialog(data, null);
        dialog.show(null, "Add a new Template").ifPresent(data.getTemplates()::addTemplate);
    }
}
