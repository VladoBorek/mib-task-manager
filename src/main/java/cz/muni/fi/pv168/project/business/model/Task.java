package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.TaskBase;

import java.time.LocalDate;

public class Task extends TaskBase {
    private Status status;
    private String customer;

    // In the base time unit
    private Integer loggedTime;
    private LocalDate dueDate;

    public Task(Long id, Status status, String description, Category category, String customer, String name,
                String assignedTo, Integer loggedTime, Integer allocatedTime, TimeUnit timeUnit, LocalDate dueDate) {
        super(id, category, name, allocatedTime, timeUnit, description, assignedTo);
        this.status = status;
        this.customer = customer;
        this.loggedTime = loggedTime;
        this.dueDate = dueDate;
    }

    public Task(Template template) {
        this(null, Status.TO_DO, template.getDescription(), template.getCategory(), "", template.getName(), template.getAssignedTo(),
                0, template.getAllocatedTime(), template.getTimeUnit(), null);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getCustomer() {
        return customer;
    }

    public Integer getLoggedTime() {
        return loggedTime;
    }

    public Integer getConvertedLoggedTime() {
        return loggedTime / getTimeUnit().getRate();
    }

    public String getConvertedLoggedTimeString() {
        return getConvertedLoggedTime().toString() + " " + getTimeUnit().getShortName();
    }

    public Float getTaskCompletionPercentage() {
        if (loggedTime == 0) {
            return 0.0F;
        }
        return ((float) getLoggedTime() / (float) getAllocatedTime()) * 100;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}
