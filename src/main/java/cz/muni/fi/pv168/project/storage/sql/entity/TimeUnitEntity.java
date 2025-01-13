package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

/**
 * Representation of TimeUnit entity in a SQL database.
 *
 * @author Maroš Pavlík
 */
public record TimeUnitEntity(
        Long id,
        String name,
        String shortName,
        Integer rate) {

    public TimeUnitEntity(
            Long id,
            String name,
            String shortName,
            Integer rate) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.shortName = Objects.requireNonNull(shortName, "shortName must not be null");
        this.rate = Objects.requireNonNull(rate, "rate must not be null");
    }

    public TimeUnitEntity(
            String name,
            String shortName,
            Integer rate) {
        this(null, name, shortName, rate);
    }
}
