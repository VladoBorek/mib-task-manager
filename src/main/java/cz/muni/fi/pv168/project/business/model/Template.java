package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.EntityWithCategory;

public class Template extends EntityWithCategory {

    private String name;
    private Integer allocatedTime;
    private TimeUnit timeUnit;
    private String templateName;

    public Template(Long id, String name, Category category,
                    Integer allocatedTime, TimeUnit timeUnit, String templateName) {
        super(id, category);
        this.name = name;
        this.allocatedTime = allocatedTime;
        this.timeUnit = timeUnit;
        this.templateName = templateName;
    }

    public Template() {
        this(null, null, null, -1, null, "<Don't use a template>");
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getAllocatedTimeString() {
        return getAllocatedTime().toString() + " " + timeUnit.getShortName();
    }

    @Override
    public String toString() {
        return templateName;
    }
}
