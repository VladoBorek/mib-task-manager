package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.LogTimeInfoEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.LogTimeInfoMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfoSqlRepository implements Repository<LogTimeInfo> {
    private final DataAccessObject<LogTimeInfoEntity> logTimeInfoDao;
    private final LogTimeInfoMapper logTimeInfoMapper;

    public LogTimeInfoSqlRepository(DataAccessObject<LogTimeInfoEntity> logTimeInfoDao,
                                    LogTimeInfoMapper logTimeInfoMapper) {
        this.logTimeInfoDao = logTimeInfoDao;
        this.logTimeInfoMapper = logTimeInfoMapper;
    }

    @Override
    public List<LogTimeInfo> findAll() {
        return logTimeInfoDao
                .findAll()
                .stream()
                .map(logTimeInfoMapper::mapToBusiness)
                .toList();
    }

    @Override
    public LogTimeInfo create(LogTimeInfo newEntity) {
        return logTimeInfoMapper.mapToBusiness(logTimeInfoDao.create(logTimeInfoMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(LogTimeInfo entity) {
        LogTimeInfoEntity existingCategory = logTimeInfoDao.findById(entity.getId())
                .orElseThrow(() -> new DataStorageException("LogTimeInfo not found, id: " + entity.getId()));
        LogTimeInfoEntity updatedEntity = logTimeInfoMapper.mapExistingEntityToDatabase(entity, existingCategory.id());

        logTimeInfoDao.update(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        logTimeInfoDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        logTimeInfoDao.deleteAll();
    }

    @Override
    public Optional<LogTimeInfo> findById(Long id) {
        return logTimeInfoDao.
                findById(id)
                .map(logTimeInfoMapper::mapToBusiness);
    }
}
