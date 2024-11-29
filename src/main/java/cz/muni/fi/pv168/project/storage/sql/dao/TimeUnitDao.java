package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * DAO for {@link TimeUnitEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class TimeUnitDao implements DataAccessObject<TimeUnitEntity> {
    @Override
    public TimeUnitEntity create(TimeUnitEntity entity) {
        return null;
    }

    @Override
    public Collection<TimeUnitEntity> findAll() {
        return null;
    }

    @Override
    public Optional<TimeUnitEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TimeUnitEntity update(TimeUnitEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
