package cz.muni.fi.pv168.project.business.model;

public class Template extends Entity {


    private String name;
    private Category category;
    private Integer allocatedTime;
    private TimeUnit timeUnit;

    private String templateName;


    public Template(Long id, String name, Category category,
                    Integer allocatedTime, TimeUnit timeUnit, String templateName) {
        super(id);
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public Integer getConvertedAllocatedTime() {
        return allocatedTime / timeUnit.getRate();
    }

    public String getConvertedAllocatedTimeString() {
        return getConvertedAllocatedTime().toString() + " " + timeUnit.getShortName();
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return templateName;
    }
}
