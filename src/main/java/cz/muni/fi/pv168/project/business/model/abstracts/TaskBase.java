package cz.muni.fi.pv168.project.business.model.abstracts;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

public abstract class TaskBase extends Entity {
    private Category category;
    private String name;

    // In the base time unit
    private Integer allocatedTime;
    private TimeUnit timeUnit;
    private String description;
    private String assignedTo;

    public TaskBase(Long id,
                    Category category,
                    String name,
                    Integer allocatedTime,
                    TimeUnit timeUnit,
                    String description,
                    String assignedTo) {
        super(id);
        this.category = category;
        this.name = name;
        if (timeUnit == null) {
            this.allocatedTime = allocatedTime;
        }
        else {
            this.allocatedTime = allocatedTime * timeUnit.getRate();
        }
        this.timeUnit = timeUnit;
        this.description = description;
        this.assignedTo = assignedTo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAllocatedTimeString() {
        return getAllocatedTime().toString() + " " + timeUnit.getShortName();
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
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
}
