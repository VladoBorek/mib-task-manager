package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv168.project.ui.MainWindow.DEMO_DATA;

/**
 * Object for holding and managing data
 * @author Maroš Pavlík
 */
public class DataManager {
    private final TimeUnitListModel timeUnits;
    private final CategoryListModel categories = new CategoryListModel(new ArrayList<>(DEMO_DATA.getCategories()));
    private final EmployeeListModel employees = new EmployeeListModel(new ArrayList<>(DEMO_DATA.getEmployees()));

    private final User loggedUser;
    private JTable taskTable;
    private TaskTableModel taskTableModel;
    private JTable templateTable;

    public DataManager(User loggedUser) {
        Repository<TimeUnit> timeUnitRepository = new InMemoryRepository<>(DEMO_DATA.getTimeUnits());
        CrudService<TimeUnit> timeUnitCrudService = new BaseCrudService<>(timeUnitRepository);
        this.timeUnits = new TimeUnitListModel(new ArrayList<>(DEMO_DATA.getTimeUnits()), timeUnitCrudService);

        this.loggedUser = loggedUser;
    }

    public TimeUnitListModel getTimeUnits() {
        return timeUnits;
    }

    public CategoryListModel getCategories() {
        return categories;
    }
    public EmployeeListModel getEmployees() {
        return employees;
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
}
