package cz.muni.fi.pv168.project.business.model;

import java.time.LocalDate;

import static java.lang.Math.round;

public class Task extends Entity{

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
    }

    public Task(Template template) {
        this(null, Status.TO_DO, "", template.getCategory(), "", template.getName(), new Employee("-", 0),
                0, template.getAllocatedTime(), template.getTimeUnit(), null);
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
}
