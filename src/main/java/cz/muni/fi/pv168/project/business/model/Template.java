package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.WorkItem;

public class Template extends WorkItem {
    private String templateName;

    public Template(Long id,
                    String name,
                    Category category,
                    Double allocatedTime,
                    TimeUnit timeUnit,
                    String templateName,
                    String description,
                    String assignedTo) {
        super(id, category, name, allocatedTime, timeUnit, description, assignedTo);
        this.templateName = templateName;
    }

    public Template() {
        this(null, null, null, -1.0, null,
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
