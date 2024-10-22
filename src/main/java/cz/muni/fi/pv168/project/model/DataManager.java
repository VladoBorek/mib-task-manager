package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.model.*;

import javax.swing.*;
import java.util.ArrayList;

import static cz.muni.fi.pv168.project.ui.MainWindow.DEMO_DATA;

/**
 * Object for holding and managing data
 * @author Maroš Pavlík
 */
public class DataManager {
    private final TimeUnitListModel timeUnits = new TimeUnitListModel(new ArrayList<>(DEMO_DATA.getTimeUnits()));
    private final CategoryListModel categories = new CategoryListModel(new ArrayList<>(DEMO_DATA.getCategories()));
    private final TemplateListModel templates = new TemplateListModel(new ArrayList<>());
    private final EmployeeListModel employees = new EmployeeListModel(new ArrayList<>(DEMO_DATA.getEmployees()));

    private JTable taskTable;
    private TaskTableModel taskTableModel;

    //public DataManager(JTable taskTable){
        //this.taskTable = taskTable;
    //}


    public TimeUnitListModel getTimeUnits() {
        return timeUnits;
    }

    public CategoryListModel getCategories() {
        return categories;
    }
    public TemplateListModel getTemplates() {
        return templates;
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

    public void setTaskTableModel(TaskTableModel taskTableModel){this.taskTableModel = taskTableModel;}
    public  TaskTableModel getTaskTableModel(){return this.taskTableModel;}
}
