package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

/**
 * Representation of Category entity in a SQL database.
 *
 * @author Maroš Pavlík
 */
public record CategoryEntity(
        Long id,
        String name,
        Integer color) {

    public CategoryEntity(
            Long id,
            String name,
            Integer color) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.color = Objects.requireNonNull(color, "color must not be null");
    }

    public CategoryEntity(
            String name,
            Integer color) {
        this(null, name, color);
    }
}
