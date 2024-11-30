package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Status;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of Template entity in a SQL database.
 * @author Maroš Pavlík
 */
public record TemplateEntity(
        Long id,
        String description,
        long categoryId,
        String templateName,
        String taskName,
        String assignedTo,
        Integer allocatedTime,
        long timeUnitId
) {
    public TemplateEntity(
            Long id,
            String description,
            long categoryId,
            String templateName,
            String taskName,
            String assignedTo,
            Integer allocatedTime,
            long timeUnitId) {
        this.id = id;
        this.description = description;
        this.categoryId = categoryId;
        this.templateName = Objects.requireNonNull(templateName, "templateName must not be null");
        this.taskName = Objects.requireNonNull(taskName, "taskName must not be null");
        this.assignedTo = Objects.requireNonNull(assignedTo, "assignedTo must not be null");
        this.allocatedTime = Objects.requireNonNull(allocatedTime, "allocatedTime must not be null");
        this.timeUnitId = timeUnitId;
    }

    public TemplateEntity(
            String description,
            long categoryId,
            String templateName,
            String taskName,
            String assignedTo,
            Integer allocatedTime,
            long timeUnitId) {
        this(null, description, categoryId, templateName, taskName, assignedTo, allocatedTime, timeUnitId);
    }
}
