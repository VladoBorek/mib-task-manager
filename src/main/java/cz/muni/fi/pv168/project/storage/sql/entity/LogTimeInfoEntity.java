package cz.muni.fi.pv168.project.storage.sql.entity;

import java.util.Objects;

/**
 * Representation of LogTimeInfo entity in a SQL database.
 *
 * @author Maroš Pavlík
 */
public record LogTimeInfoEntity(
        Long id,
        Double loggedTime,
        String userName,
        Long userId,
        Long taskId,
        Long timeUnitId

) {
    public LogTimeInfoEntity(
            Long id,
            Double loggedTime,
            String userName,
            Long userId,
            Long taskId,
            Long timeUnitId) {
        this.id = id;
        this.loggedTime = Objects.requireNonNull(loggedTime, "loggedTime must not be null");
        this.userName = Objects.requireNonNull(userName, "userName must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.taskId = Objects.requireNonNull(taskId, "taskId must not be null");
        this.timeUnitId = timeUnitId;
    }

    public LogTimeInfoEntity(
            Double loggedTime,
            String userName,
            Long userId,
            Long taskId,
            Long timeUnitId) {
        this(null, loggedTime, userName, userId, taskId, timeUnitId);
    }
}
