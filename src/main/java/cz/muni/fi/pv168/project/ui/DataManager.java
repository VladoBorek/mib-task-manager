package cz.muni.fi.pv168.project.ui;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Object for holding and managing data
 *
 * @author Maroš Pavlík
 */
public class DataManager {
    private BaseListModel<TimeUnit> timeUnits;
    private BaseListModel<Category> categories;

    private CrudService<LogTimeInfo> logTimeInfoCrudService;

    private final User loggedUser;
    private JTable taskTable;
    private TaskTableModel taskTableModel;
    private JTable templateTable;

    private JTable statisticsTable;

    public DataManager(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setCategories(CrudService<Category> categoryCrudService) {
        this.categories = new BaseListModel<>(new ArrayList<>(categoryCrudService.findAll()), categoryCrudService) {
        };
    }

    public void setTimeUnits(CrudService<TimeUnit> timeUnitCrudService) {
        this.timeUnits = new BaseListModel<>(new ArrayList<>(timeUnitCrudService.findAll()), timeUnitCrudService) {
        };
    }

    public void setLogInfo(CrudService<LogTimeInfo> logTimeInfoCrudService) {
        this.logTimeInfoCrudService = logTimeInfoCrudService;
    }

    public BaseListModel<TimeUnit> getTimeUnits() {
        return timeUnits;
    }

    public BaseListModel<Category> getCategories() {
        return categories;
    }

    public CrudService<LogTimeInfo> getLogTimeInfoCrudService() {
        return logTimeInfoCrudService;
    }

    public JTable getTaskTable() {
        return taskTable;
    }

    public void setTaskTable(JTable taskTable) {
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

    public TaskTableModel getTaskTableModel() {
        return this.taskTableModel;
    }

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
