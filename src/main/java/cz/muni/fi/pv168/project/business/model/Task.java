package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.TaskBase;

import javax.swing.*;
import java.time.LocalDate;

import static java.lang.Math.round;

public class Task extends TaskBase {
    private Status status;
    private String customer;

    // In the base time unit
    private Integer loggedTime;
    private LocalDate dueDate;

    public Task(Long id, Status status, String description, Category category, String customer,String name,
                String assignedTo, Integer loggedTime, Integer allocatedTime, TimeUnit timeUnit, LocalDate dueDate) {
        super(id, category, name, allocatedTime, timeUnit, description, assignedTo);
        this.status = status;
        this.customer = customer;
        this.loggedTime = loggedTime * timeUnit.getRate();
        this.dueDate = dueDate;
    }

    public Task(Template template) {
        this(null, Status.TO_DO, "", template.getCategory(), "", template.getName(), null,
                0, template.getAllocatedTime(), template.getTimeUnit(), null);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime;
    }

    public Integer getConvertedLoggedTime() {
        return loggedTime / getTimeUnit().getRate();
    }

    public String getConvertedLoggedTimeString() {
        return getConvertedLoggedTime().toString() + " " + getTimeUnit().getShortName();
    }

    public void setConvertedLoggedTime(Integer loggedTime) {
        this.loggedTime = loggedTime * getTimeUnit().getRate();
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

    // TODO: Vague name
    public Float getPercentage()
    {   if (loggedTime == 0) {
            return 0.0F;
        }
        return ((float)getLoggedTime()/(float)getAllocatedTime()) * 100;
    }
}
