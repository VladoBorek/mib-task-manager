package cz.muni.fi.pv168.project.ui.actions.menu;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.DataManager;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.AddTaskDialog;
import cz.muni.fi.pv168.project.ui.dialog.TemplateDialog;
import cz.muni.fi.pv168.project.ui.dialog.TimeUnitDialog;
import cz.muni.fi.pv168.project.ui.model.storagemodels.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TimeUnitListModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class AddAction extends AbstractAction {
    private final ActionType type;
    private final DataManager data;
    private final JComboBox<Template> chosenTemplate;
    private JComboBox<TimeUnit> timeUnitsComboBox = null;
    private JComboBox<Category> categoryComboBox = null;
    private JComboBox<Template> templateComboBox = null;


    public AddAction(ActionType type,
                     DataManager data,
                     JComboBox<Template> chosenTemplate) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.data = data;
        this.chosenTemplate = chosenTemplate;
    }


    public AddAction(ActionType type,
                     DataManager data,
                     JComboBox<Template> chosenTemplate,
                     JComboBox<?> comboBox
                     ) {
        super("Add new " + type.toString().toLowerCase().replace('_', ' '), Icons.ADD_ICON);
        this.type = type;
        this.data = data;
        this.chosenTemplate = chosenTemplate;

        switch (type) {
            case TEMPLATE:
                this.templateComboBox = (JComboBox<Template>) comboBox;
                break;
            case TIME_UNIT:
                this.timeUnitsComboBox = (JComboBox<TimeUnit>) comboBox;
                break;
            case CATEGORY:
                this.categoryComboBox = (JComboBox<Category>) comboBox;
                break;
            default:
                throw new IllegalArgumentException("Unsupported ActionType: " + type);
        }
    }


    /**
     * Evoked when add button is clicked.
     * Decides which object is to be added and calls appropriate method.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(type) {
            case TASK:
                addTask();
                break;
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
                if (this.templateComboBox == null){
                    addTemplate();
                }
                else {
                    addTemplate(this.templateComboBox);
                }
                break;
        }
    }

    /**
     * Opens a {@link AddTaskDialog} window, creates a task and adds it to table
     */
    private void addTask() {
        TaskTableModel taskTableModel = (TaskTableModel) data.getTaskTable().getModel();
        AddTaskDialog dialog;


        if (((Template) Objects.requireNonNull(chosenTemplate.getSelectedItem()))
                .getTemplateName().compareTo("<Don't use a template>") == 0) {
            dialog = new AddTaskDialog(null, data);
        } else {
            dialog = new AddTaskDialog(new Task((Template) chosenTemplate.getSelectedItem()), data);
        }

        dialog.show(data.getTaskTable(), "Add new Task").ifPresent(taskTableModel::addRow);

        StatisticsTableModel statisticsTableModel = (StatisticsTableModel) data.getStatisticsTable().getModel();
        statisticsTableModel.refreshStatistics();
    }

    private void addTemplate() {
        TemplateTableModel templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
        TemplateDialog dialog;

        dialog = new TemplateDialog(data, null);

        dialog.show(data.getTaskTable(), "Add new Template").ifPresent(templateTableModel::addRow);
    }

    private void addTemplate(JComboBox<Template> templateComboBox) {
        TemplateTableModel templateTableModel = (TemplateTableModel) data.getTemplateTable().getModel();
        TemplateDialog dialog = new TemplateDialog(data, null);

        dialog.show(data.getTaskTable(), "Add new Template").ifPresent(newTemplate -> {
            templateTableModel.addRow(newTemplate);
            DefaultComboBoxModel<Template> model = (DefaultComboBoxModel<Template>) templateComboBox.getModel();
            model.addElement(newTemplate);
            templateComboBox.setSelectedItem(newTemplate);
        });
    }

    /**
     * Opens a {@link TimeUnitDialog} window, creates a time unit and adds it to {@link TimeUnitListModel}
     */
    private void addTimeUnit() {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(data.getTimeUnits()::add);
    }

    /*
    Automatically updates combobox in task window when creating new time unit
     */
    private void addTimeUnit(JComboBox<TimeUnit> timeUnitsComboBox) {
        var dialog = new TimeUnitDialog();
        dialog.show(null, "Add new time unit").ifPresent(newTimeUnit -> {
            data.getTimeUnits().add(newTimeUnit);
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
        dialog.show(null, "Add a new Category").ifPresent(data.getCategories()::add);
    }


    /*
    Automatically updates combobox in task window when creating new category
     */
    private void addCategory(JComboBox<Category> categoryComboBox) {
        var dialog = new CategoryDialog();

        dialog.show(null, "Add a new Category").ifPresent(newCategory -> {
            data.getCategories().add(newCategory);
            DefaultComboBoxModel<Category> model = (DefaultComboBoxModel<Category>) categoryComboBox.getModel();
            model.addElement(newCategory);
            categoryComboBox.setSelectedItem(newCategory);
        });

    }
}
