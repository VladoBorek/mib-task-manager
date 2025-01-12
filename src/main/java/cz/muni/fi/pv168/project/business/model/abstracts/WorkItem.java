package cz.muni.fi.pv168.project.business.model.abstracts;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;

public abstract class WorkItem extends Entity {
    private Category category;
    private String name;

    // In the base time unit
    private Double allocatedTime;
    private TimeUnit timeUnit;
    private String description;
    private String assignedTo;

    public WorkItem(Long id,
                    Category category,
                    String name,
                    Double allocatedTime,
                    TimeUnit timeUnit,
                    String description,
                    String assignedTo) {
        super(id);
        this.category = category;
        this.name = name;
        this.timeUnit = timeUnit;
        this.description = description;
        this.assignedTo = assignedTo;
        this.allocatedTime = allocatedTime;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllocatedTime(Double allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public void setConvertedAllocatedTime(Double allocatedTime) {
        this.allocatedTime = allocatedTime * timeUnit.getRate();
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Double getAllocatedTime() {
        return allocatedTime;
    }

    public Double getConvertedAllocatedTime() {
        return allocatedTime / timeUnit.getRate();
    }

    public String getConvertedAllocatedTimeString() {
        return getConvertedAllocatedTime().toString() + " " + timeUnit.getShortName();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }
}
