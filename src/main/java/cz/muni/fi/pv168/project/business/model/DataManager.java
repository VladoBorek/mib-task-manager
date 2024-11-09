package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.TaskValidator;
import cz.muni.fi.pv168.project.business.service.validation.TimeUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.model.storagemodels.CategoryListModel;
import cz.muni.fi.pv168.project.ui.model.storagemodels.EmployeeListModel;
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
    private final TimeUnitListModel timeUnits;
    private final CategoryListModel categories;
    private final EmployeeListModel employees;

    private final User loggedUser;
    private JTable taskTable;
    private TaskTableModel taskTableModel;
    private JTable templateTable;

    public DataManager(User loggedUser) {
        Validator<TimeUnit> timeUnitValidator = new TimeUnitValidator();
        Repository<TimeUnit> timeUnitRepository = new InMemoryRepository<>(DEMO_DATA.getTimeUnits());
        CrudService<TimeUnit> timeUnitCrudService = new BaseCrudService<>(timeUnitRepository, timeUnitValidator);
        this.timeUnits = new TimeUnitListModel(new ArrayList<>(DEMO_DATA.getTimeUnits()), timeUnitCrudService);

        Validator<Category> categoryValidator = new CategoryValidator();
        Repository<Category> categoryRepository = new InMemoryRepository<>(DEMO_DATA.getCategories());
        CrudService<Category> categoryCrudService = new BaseCrudService<>(categoryRepository, categoryValidator);
        this.categories = new CategoryListModel(new ArrayList<>(DEMO_DATA.getCategories()), categoryCrudService);

        Repository<Employee> employeeRepository = new InMemoryRepository<>(DEMO_DATA.getEmployees());
        CrudService<Employee> employeeCrudService = new BaseCrudService<>(employeeRepository, null); // TODO DELETE EMPLOYEES
        this.employees = new EmployeeListModel(new ArrayList<>(DEMO_DATA.getEmployees()), employeeCrudService);

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
