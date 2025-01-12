package cz.muni.fi.pv168.project.ui;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.ui.model.abstracts.BaseListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.StatisticsTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TaskTableModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.TemplateTableModel;
import cz.muni.fi.pv168.project.wiring.DependencyProvider;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Object for holding and managing data for UI
 *
 * @author Maroš Pavlík
 */
public class UIDataManager {
    private final User loggedUser;
    private final DependencyProvider dependencyProvider;
    private BaseListModel<Category> categoryListModel;
    private BaseListModel<TimeUnit> timeUnitListModel;
    private LogTimeInfoTableModel logTimeInfoTableModel;
    private JTable taskTable;
    private JTable templateTable;
    private JTable statisticsTable;

    public UIDataManager(User loggedUser, DependencyProvider dependencyProvider) {
        this.loggedUser = loggedUser;
        this.dependencyProvider = dependencyProvider;
        setUp();
    }

    public void setUp() {
        this.categoryListModel = new BaseListModel<>(
                new ArrayList<>(
                        dependencyProvider.getCategoryCrudService().findAll()),
                dependencyProvider.getCategoryCrudService()) {
        };
        this.timeUnitListModel = new BaseListModel<>(
                new ArrayList<>(
                        dependencyProvider.getTimeUnitCrudService().findAll()),
                dependencyProvider.getTimeUnitCrudService()) {
        };
        this.logTimeInfoTableModel = new LogTimeInfoTableModel(dependencyProvider.getLogTimeInfoCrudService());
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setTaskTable(JTable taskTable) {
        this.taskTable = taskTable;
    }

    public void setTemplateTable(JTable templateTable) {
        this.templateTable = templateTable;
    }

    public void setStatisticsTable(JTable statisticsTable) {
        this.statisticsTable = statisticsTable;
    }

    public JTable getTaskTable() {
        return taskTable;
    }

    public JTable getTemplateTable() {
        return templateTable;
    }

    public TaskTableModel getTaskTableModel() {
        return (TaskTableModel) getTaskTable().getModel();
    }

    public TemplateTableModel getTemplateTableModel() {
        return (TemplateTableModel) getTemplateTable().getModel();
    }

    public StatisticsTableModel getStatisticsTableModel() {
        return (StatisticsTableModel) statisticsTable.getModel();
    }

    public BaseListModel<TimeUnit> getTimeUnitListModel() {
        return timeUnitListModel;
    }

    public BaseListModel<Category> getCategoryListModel() {
        return categoryListModel;
    }

    public LogTimeInfoTableModel getLogTimeInfoTableModel() {
        return logTimeInfoTableModel;
    }

    public DependencyProvider getDependencyProvider() {
        return dependencyProvider;
    }
}
