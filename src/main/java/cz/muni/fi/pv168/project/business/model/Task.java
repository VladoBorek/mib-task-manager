package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.BaseCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.LogTimeInfoValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.data.DemoDataGenerator;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.model.storagemodels.LogTimeInfoTableModel;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Math.round;

public class Task extends Entity {

    private Status status;
    private String description;
    private Category category;
    private String customer;
    private String nameOfTask;
    private Employee assignedTo;

    // In the base time unit
    private Integer loggedTime;
    private Integer allocatedTime;

    private TimeUnit timeUnit;
    private LocalDate dueDate;

    private JTable timeLogTable;

    public Task(Long id, Status status, String description, Category category, String customer,String nameOfTask,
                Employee assignedTo, Integer loggedTime, Integer allocatedTime, TimeUnit timeUnit, LocalDate dueDate) {
        super(id);
        this.status = status;
        this.description = description;
        this.category = category;
        this.customer = customer;
        this.nameOfTask = nameOfTask;
        this.assignedTo = assignedTo;
        this.loggedTime = loggedTime * timeUnit.getRate();
        this.allocatedTime = allocatedTime * timeUnit.getRate();
        this.timeUnit = timeUnit;
        this.dueDate = dueDate;

        DemoDataGenerator demoDataGenerator = new DemoDataGenerator();
        List<LogTimeInfo> logTimeInfoList = generateLogTimeInfoData(demoDataGenerator.getEmployees());

        Validator<LogTimeInfo> logTimeInfoValidator = new LogTimeInfoValidator();
        Repository<LogTimeInfo> logTimeInfoRepository = new InMemoryRepository<>(logTimeInfoList);
        CrudService<LogTimeInfo> logTimeInfoCrudService = new BaseCrudService<>(logTimeInfoRepository, logTimeInfoValidator);

        this.timeLogTable = createLogTimeInfoTable(logTimeInfoCrudService);

    }

    public Task(Template template) {
        this(null, Status.TO_DO, "", template.getCategory(), "", template.getName(), new Employee(null, "-", 0),
                0, template.getAllocatedTime(), template.getTimeUnit(), null);
    }

    public JTable createLogTimeInfoTable(CrudService<LogTimeInfo> logTimeInfoCrudService){
        var model = new LogTimeInfoTableModel(logTimeInfoCrudService);
        var table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setAutoCreateRowSorter(true);

        var idColumn = table.getColumnModel().getColumn(0);
        var nameColumn = table.getColumnModel().getColumn(1);
        var timeColumn = table.getColumnModel().getColumn(2);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        idColumn.setCellRenderer(centerRenderer);
        nameColumn.setCellRenderer(centerRenderer);
        timeColumn.setCellRenderer(centerRenderer);

        return table;
    }

    public ArrayList<LogTimeInfo> generateLogTimeInfoData(List<Employee> employees) {
        var logTimeInfos = new ArrayList<LogTimeInfo>();
        for (Employee employee : employees) {
            LogTimeInfo logTimeInfo = new LogTimeInfo(this.loggedTime / employees.size(), employee);
            logTimeInfos.add(logTimeInfo);
        }
        return logTimeInfos;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public Employee getAssignedTo() {
        return assignedTo;
    }

    public String getAssignedToString() {
        return assignedTo.toString();
    }


    public void setAssignedTo(Employee assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Integer getConvertedLoggedTime() {
        return loggedTime / timeUnit.getRate();
    }

    public String getConvertedLoggedTimeString() {
        return getConvertedLoggedTime().toString() + " " + timeUnit.getShortName();
    }

    public void setConvertedLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime * timeUnit.getRate();
    }

    public Integer getConvertedAllocatedTime() {
        return allocatedTime / timeUnit.getRate();
    }

    public String getConvertedAllocatedTimeString() {
        return getConvertedAllocatedTime().toString() + " " + timeUnit.getShortName();
    }

    public void setConvertedAllocatedTime(Integer allocatedTime) {
        this.allocatedTime = allocatedTime * timeUnit.getRate();
    }

//    public Integer getAllocatedTime() {
//        return allocatedTime;
//    }
//
//    public void setAllocatedTime(Integer allocatedTime) {
//        this.allocatedTime = allocatedTime;
//    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Float getPercentage()
    {   if (loggedTime == 0){

        return 0.0F;
    }
        return ((float)loggedTime/(float)allocatedTime) * 100;
    }

    public JTable getTimeLogTable(){
        return this.timeLogTable;
    }
}
