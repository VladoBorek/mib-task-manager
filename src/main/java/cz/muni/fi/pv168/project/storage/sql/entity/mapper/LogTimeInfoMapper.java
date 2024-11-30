package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.User;
import cz.muni.fi.pv168.project.storage.sql.entity.LogTimeInfoEntity;

/**
 * Mapper from the {@link LogTimeInfoEntity} to {@link LogTimeInfo}.
 * @author Maroš Pavlík
 */
public class LogTimeInfoMapper implements EntityMapper<LogTimeInfoEntity, LogTimeInfo> {
    @Override
    public LogTimeInfo mapToBusiness(LogTimeInfoEntity dbLogTimeInfo) {
        return new LogTimeInfo(
                dbLogTimeInfo.id(),
                dbLogTimeInfo.loggedTime(),
                new User(dbLogTimeInfo.userName(), dbLogTimeInfo.userId()),
                dbLogTimeInfo.taskId()
        );
    }

    @Override
    public LogTimeInfoEntity mapNewEntityToDatabase(LogTimeInfo businessLogTimeInfo) {
        return new LogTimeInfoEntity(null,
                businessLogTimeInfo.getLoggedTime(),
                businessLogTimeInfo.getUsername(),
                businessLogTimeInfo.getUserId(),
                businessLogTimeInfo.getTaskID());

    }

    @Override
    public LogTimeInfoEntity mapExistingEntityToDatabase(LogTimeInfo businessLogTimeInfo, Long dbId) {
        return new LogTimeInfoEntity(dbId,
                businessLogTimeInfo.getLoggedTime(),
                businessLogTimeInfo.getUsername(),
                businessLogTimeInfo.getUserId(),
                businessLogTimeInfo.getTaskID());
    }
}
