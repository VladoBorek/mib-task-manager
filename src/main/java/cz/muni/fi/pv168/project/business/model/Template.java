package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.TaskBase;

public class Template extends TaskBase {
    private String templateName;

    public Template(Long id,
                    String name,
                    Category category,
                    Integer allocatedTime,
                    TimeUnit timeUnit,
                    String templateName,
                    String description,
                    String assignedTo) {
        super(id, category, name, allocatedTime, timeUnit, description, assignedTo);
        this.templateName = templateName;
    }

    // TODO: Temporary constructor, remove after implementing description and assignedTo for templates
    public Template(Long id,
                    String name,
                    Category category,
                    Integer allocatedTime,
                    TimeUnit timeUnit,
                    String templateName) {
        this(id, name, category, allocatedTime, timeUnit, templateName, null, null);
    }

    public Template() {
        this(null, null, null, -1, null,
                "<Don't use a template>", null, null);
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return templateName;
    }
}
