package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TimeUnitMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladimir Borek
 */
public class TimeUnitSqlRepository implements Repository<TimeUnit> {
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final TimeUnitMapper timeUnitMapper;

    public TimeUnitSqlRepository(DataAccessObject<TimeUnitEntity> timeUnitDao,
                                 TimeUnitMapper timeUnitMapper) {
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
    }

    @Override
    public List<TimeUnit> findAll() {
        return timeUnitDao
                .findAll()
                .stream()
                .map(timeUnitMapper::mapToBusiness)
                .toList();
    }

    @Override
    public TimeUnit create(TimeUnit newEntity) {
        return timeUnitMapper.mapToBusiness(timeUnitDao.create(timeUnitMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(TimeUnit entity) {
        TimeUnitEntity existingEntity = timeUnitDao.findById(entity.getId())
                .orElseThrow(() -> new DataStorageException("TimeUnit not found, id: " + entity.getId()));
        TimeUnitEntity updatedEntity = timeUnitMapper.mapExistingEntityToDatabase(entity, existingEntity.id());

        timeUnitDao.update(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        timeUnitDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        timeUnitDao.deleteAll();
    }

    @Override
    public Optional<TimeUnit> findById(Long id) {
        return timeUnitDao.
                findById(id)
                .map(timeUnitMapper::mapToBusiness);
    }
}
