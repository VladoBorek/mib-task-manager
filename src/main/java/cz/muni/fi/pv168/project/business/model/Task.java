package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.WorkItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task extends WorkItem {
    private Status status;
    private String customer;

    // In the base time unit
    private Double loggedTime;
    private LocalDate dueDate;
    private final List<LogTimeInfo> logHistory = new ArrayList<>();

    public Task(Long id, Status status, String description, Category category, String customer, String name,
                String assignedTo, Double loggedTime, Double allocatedTime, TimeUnit timeUnit, LocalDate dueDate) {
        super(id, category, name, allocatedTime, timeUnit, description, assignedTo);
        this.status = status;
        this.customer = customer;
        this.loggedTime = loggedTime;
        this.dueDate = dueDate;
    }

    public Task(Template template) {
        this(null, Status.TO_DO, template.getDescription(), template.getCategory(), "", template.getName(), template.getAssignedTo(),
                0d, template.getAllocatedTime(), template.getTimeUnit(), null);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setLoggedTime(Double loggedTime) {
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

    public Double getLoggedTime() {
        return loggedTime;
    }

    public Double getConvertedLoggedTime() {
        return loggedTime / getTimeUnit().getRate();
    }

    public String getConvertedLoggedTimeString() {
        return getConvertedLoggedTime().toString() + " " + getTimeUnit().getShortName();
    }

    public float getTaskCompletionPercentage() {
        if (loggedTime == 0) {
            return 0.0F;
        }
        return (float) ((getLoggedTime() / getAllocatedTime()) * 100);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<LogTimeInfo> getLogHistory() {
        return logHistory;
    }

    public void addLog(LogTimeInfo log) {
        logHistory.add(log);
    }
}
