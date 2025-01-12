package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Status;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of Task entity in a SQL database.
 *
 * @author Maroš Pavlík
 */
public record TaskEntity(

        Long id,
        Status status,
        String description,
        Long categoryId,
        String customer,
        String name,
        String assignedTo,
        Double loggedTime,
        Double allocatedTime,
        Long timeUnitId,
        LocalDate dueDate
) {
    public TaskEntity(
            Long id,
            Status status,
            String description,
            Long categoryId,
            String customer,
            String name,
            String assignedTo,
            Double loggedTime,
            Double allocatedTime,
            Long timeUnitId,
            LocalDate dueDate) {
        this.id = id;
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.description = description;
        this.categoryId = categoryId;
        this.customer = Objects.requireNonNull(customer, "customer must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.assignedTo = Objects.requireNonNull(assignedTo, "assignedTo must not be null");
        this.loggedTime = Objects.requireNonNull(loggedTime, "loggedTime must not be null");
        this.allocatedTime = Objects.requireNonNull(allocatedTime, "allocatedTime must not be null");
        this.timeUnitId = timeUnitId;
        this.dueDate = Objects.requireNonNull(dueDate, "dueDate must not be null");
    }
}
