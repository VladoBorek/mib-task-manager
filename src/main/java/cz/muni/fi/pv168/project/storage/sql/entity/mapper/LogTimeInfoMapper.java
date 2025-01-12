package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.LogTimeInfoEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;
import cz.muni.fi.pv168.project.util.Constants;

/**
 * Mapper from the {@link LogTimeInfoEntity} to {@link LogTimeInfo}.
 *
 * @author Maroš Pavlík
 */
public class LogTimeInfoMapper implements EntityMapper<LogTimeInfoEntity, LogTimeInfo> {

    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;

    public LogTimeInfoMapper(DataAccessObject<TimeUnitEntity> timeUnitDao,
                             EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper) {
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
    }

    @Override
    public LogTimeInfo mapToBusiness(LogTimeInfoEntity dbLogTimeInfo) {
        TimeUnit timeUnit;
        if (dbLogTimeInfo.timeUnitId() == null) {
            timeUnit = Constants.BASE_TIME_UNIT;
        } else {
            timeUnit = timeUnitDao.findById(dbLogTimeInfo.timeUnitId())
                    .map(timeUnitMapper::mapToBusiness)
                    .orElseThrow(() -> new DataStorageException("TimeUnit not found, id: " + dbLogTimeInfo.timeUnitId()));
        }
        return new LogTimeInfo(
                dbLogTimeInfo.id(),
                dbLogTimeInfo.loggedTime(),
                new User(dbLogTimeInfo.userName(), dbLogTimeInfo.userId()),
                dbLogTimeInfo.taskId(),
                timeUnit
        );
    }

    @Override
    public LogTimeInfoEntity mapNewEntityToDatabase(LogTimeInfo businessLogTimeInfo) {
        Long timeUnitId = businessLogTimeInfo.getTimeUnit().equals(Constants.BASE_TIME_UNIT) ? null : businessLogTimeInfo.getTimeUnit().getId();

        return new LogTimeInfoEntity(null,
                businessLogTimeInfo.getLoggedTime(),
                businessLogTimeInfo.getUsername(),
                businessLogTimeInfo.getUserId(),
                businessLogTimeInfo.getTaskID(),
                timeUnitId);
    }

    @Override
    public LogTimeInfoEntity mapExistingEntityToDatabase(LogTimeInfo businessLogTimeInfo, Long dbId) {
        Long timeUnitId = businessLogTimeInfo.getTimeUnit().equals(Constants.BASE_TIME_UNIT)
                ? null
                : businessLogTimeInfo.getTimeUnit().getId();
        return new LogTimeInfoEntity(dbId,
                businessLogTimeInfo.getLoggedTime(),
                businessLogTimeInfo.getUsername(),
                businessLogTimeInfo.getUserId(),
                businessLogTimeInfo.getTaskID(),
                timeUnitId);
    }
}
