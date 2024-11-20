package cz.muni.fi.pv168.project.business.model;


import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.storagemodels.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TimeUnitListModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv168.project.ui.MainWindow.DEMO_DATA;

/**
 * Object for holding and managing data
 * @author Maroš Pavlík
 */
public class DataManager {
    private TimeUnitListModel timeUnits;
    private CategoryListModel categories;

    private final User loggedUser;
    private JTable taskTable;
    private TaskTableModel taskTableModel;
    private JTable templateTable;

    private JTable statisticsTable;

    public DataManager(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    //TODO provisional solution
    public void setCategories(CrudService<Category> categoryCrudService){
        this.categories = new CategoryListModel(new ArrayList<>(DEMO_DATA.getCategories()), categoryCrudService);
    }
    public void setTimeUnits(CrudService<TimeUnit> timeUnitCrudService){
        this.timeUnits = new TimeUnitListModel(new ArrayList<>(DEMO_DATA.getTimeUnits()), timeUnitCrudService);
    }

    public TimeUnitListModel getTimeUnits() {
        return timeUnits;
    }

    public CategoryListModel getCategories() {
        return categories;
    }

    public JTable getTaskTable(){
        return taskTable;
    }

    public void setTaskTable(JTable taskTable){
        this.taskTable = taskTable;
    }

    public JTable getTemplateTable() {
        return templateTable;
    }

    public void setTemplateTable(JTable templateTable) {
        this.templateTable = templateTable;
    }

    public void setTaskTableModel(TaskTableModel taskTableModel) {
        this.taskTableModel = taskTableModel;
    }

    public TaskTableModel getTaskTableModel(){return this.taskTableModel;}

    public User getLoggedUser() {
        return loggedUser;
    }

    public List<Template> getTemplates() {
        return ((TemplateTableModel) getTemplateTable().getModel()).getAllRows();
    }
    public TemplateTableModel getTemplateTableModel() {
        return ((TemplateTableModel) getTemplateTable().getModel());
    }

    public JTable getStatisticsTable() {
        return statisticsTable;
    }

    public void setStatisticsTable(JTable statisticsTable) {
        this.statisticsTable = statisticsTable;
    }
}
