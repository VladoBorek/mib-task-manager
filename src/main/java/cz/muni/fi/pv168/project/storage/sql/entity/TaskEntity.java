package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Status;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of Task entity in a SQL database.
 * @author Maroš Pavlík
 */
public record TaskEntity(

        Long id,
        String name,
        String customer,
        Category category,
        String assignedTo,
        Status status,
        LocalDate dueDate,
        String description,
        Integer allocatedTime,
        Integer loggedTime,
        long timeUnitId
) {
    public TaskEntity(
            Long id,
            String name,
            String customer,
            Category category,
            String assignedTo,
            Status status,
            LocalDate dueDate,
            String description,
            Integer allocatedTime,
            Integer loggedTime,
            long timeUnitId) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.customer = Objects.requireNonNull(customer, "customer must not be null");
        this.category = Objects.requireNonNull(category, "category must not be null");
        this.assignedTo = Objects.requireNonNull(assignedTo, "assignedTo must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.dueDate = Objects.requireNonNull(dueDate, "dueDate must not be null");
        this.description = description;
        this.allocatedTime = Objects.requireNonNull(allocatedTime, "allocatedTime must not be null");
        this.loggedTime = Objects.requireNonNull(loggedTime, "loggedTime must not be null");;
        this.timeUnitId = timeUnitId;
    }

    public TaskEntity(
            String name,
            String customer,
            Category category,
            String assignedTo,
            Status status,
            LocalDate dueDate,
            String description,
            Integer allocatedTime,
            Integer loggedTime,
            long timeUnitId) {
        this(null, name, customer, category, assignedTo, status, dueDate,
                description, allocatedTime, loggedTime, timeUnitId
        );
    }
}
