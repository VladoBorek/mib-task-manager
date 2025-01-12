package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

/**
 * Representation of Template entity in a SQL database.
 *
 * @author Maroš Pavlík
 */
public record TemplateEntity(
        Long id,
        String description,
        Long categoryId,
        String templateName,
        String taskName,
        String assignedTo,
        Double allocatedTime,
        Long timeUnitId
) {
    public TemplateEntity(
            Long id,
            String description,
            Long categoryId,
            String templateName,
            String taskName,
            String assignedTo,
            Double allocatedTime,
            Long timeUnitId) {
        this.id = id;
        this.description = description;
        this.categoryId = categoryId;
        this.templateName = Objects.requireNonNull(templateName, "templateName must not be null");
        this.taskName = Objects.requireNonNull(taskName, "taskName must not be null");
        this.assignedTo = Objects.requireNonNull(assignedTo, "assignedTo must not be null");
        this.allocatedTime = Objects.requireNonNull(allocatedTime, "allocatedTime must not be null");
        this.timeUnitId = timeUnitId;
    }
}
