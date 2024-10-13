package cz.muni.fi.pv168.project.model;

import java.time.LocalDate;
import java.util.Optional;

public class Task {

    private Status status;
    private Category category;
    private String customer;
    private String nameOfTask;
    private String assignedTo;
    private Integer loggedTime;
    private Integer allocatedTime;
    private TimeUnit timeUnit;
    private LocalDate dueDate;

    public Task(Status status, Category category, String customer,String nameOfTask, String assignedTo, Integer loggedTime, Integer allocatedTime, TimeUnit timeUnit, LocalDate dueDate) {
        this.status = status;
        this.category = category;
        this.customer = customer;
        this.nameOfTask = nameOfTask;
        this.assignedTo = assignedTo;
        this.loggedTime = loggedTime;
        this.allocatedTime = allocatedTime;
        this.timeUnit = timeUnit;
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Integer getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Integer getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(Integer allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

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
