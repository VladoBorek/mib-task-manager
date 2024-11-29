package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;

import java.awt.*;

/**
 * Mapper from the {@link TimeUnitEntity} to {@link TimeUnit}.
 * @author Maroš Pavlík
 */
public class TimeUnitMapper implements EntityMapper<TimeUnitEntity, TimeUnit>{
    @Override
    public TimeUnit mapToBusiness(TimeUnitEntity dbTimeUnit) {
        return new TimeUnit(
                dbTimeUnit.id(),
                dbTimeUnit.name(),
                dbTimeUnit.shortName(),
                dbTimeUnit.rate());
    }

    @Override
    public TimeUnitEntity mapNewEntityToDatabase(TimeUnit businessTimeUnit) {
        return new TimeUnitEntity(null,
                businessTimeUnit.getName(),
                businessTimeUnit.getShortName(),
                businessTimeUnit.getRate());
    }

    @Override
    public TimeUnitEntity mapExistingEntityToDatabase(TimeUnit businessTimeUnit, Long dbId) {
        return new TimeUnitEntity(dbId,
                businessTimeUnit.getName(),
                businessTimeUnit.getShortName(),
                businessTimeUnit.getRate());
    }
}
