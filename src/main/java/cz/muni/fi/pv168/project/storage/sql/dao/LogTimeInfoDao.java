package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.LogTimeInfoEntity;
import cz.muni.fi.pv168.project.util.Constants;
import org.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link LogTimeInfoEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class LogTimeInfoDao implements DataAccessObject<LogTimeInfoEntity> {

    private final Supplier<ConnectionHandler> connections;

    public LogTimeInfoDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public LogTimeInfoEntity create(LogTimeInfoEntity entity) {
        var sql = """
                INSERT INTO LogTimeInfo(
                    loggedTime,
                    userName,
                    userId,
                    taskId,
                    timeUnitId
                )
                VALUES (?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setDouble(1, entity.loggedTime());
            statement.setString(2, entity.userName());
            statement.setLong(3, entity.userId());
            statement.setLong(4, entity.taskId());
            if (entity.timeUnitId() == null || entity.timeUnitId().equals(Constants.BASE_TIME_UNIT_ID)) {
                statement.setNull(5, java.sql.Types.BIGINT);
            } else {
                statement.setLong(5, entity.timeUnitId());
            }

            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                if (keyResultSet.next()) {
                    Logger.info("Generated ID: " + keyResultSet.getLong(1));
                    return findById(keyResultSet.getLong(1)).orElseThrow();
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<LogTimeInfoEntity> findAll() {
        var sql = """
                SELECT id,
                    loggedTime,
                    userName,
                    userId,
                    taskId,
                    timeUnitId
                FROM LogTimeInfo
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            List<LogTimeInfoEntity> logTimeInfos = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    logTimeInfos.add(logTimeInfoFromResultSet(resultSet));
                }
            }
            return logTimeInfos;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all LogTimeInfos", ex);
        }
    }

    @Override
    public Optional<LogTimeInfoEntity> findById(Long id) {
        var sql = """
                SELECT id,
                    loggedTime,
                    userName,
                    userId,
                    taskId,
                    timeUnitId
                FROM LogTimeInfo
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(logTimeInfoFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load LogTimeInfo by id", ex);
        }
    }

    private LogTimeInfoEntity logTimeInfoFromResultSet(ResultSet resultSet) throws SQLException {
        Long timeUnitId = resultSet.getObject("timeUnitId", Long.class);

        return new LogTimeInfoEntity(
                resultSet.getLong("id"),
                resultSet.getDouble("loggedTime"),
                resultSet.getString("userName"),
                resultSet.getLong("userId"),
                resultSet.getLong("taskId"),
                timeUnitId
        );
    }

    @Override
    public LogTimeInfoEntity update(LogTimeInfoEntity entity) {
        var sql = """
                UPDATE LogTimeInfo
                SET loggedTime = ?,
                    userName = ?,
                    userId = ?,
                    taskId = ?,
                    timeUnitId = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setDouble(1, entity.loggedTime());
            statement.setString(2, entity.userName());
            statement.setLong(3, entity.userId());
            statement.setLong(4, entity.taskId());
            if (entity.timeUnitId() == null || entity.timeUnitId().equals(Constants.BASE_TIME_UNIT_ID)) {
                statement.setNull(5, java.sql.Types.BIGINT);
            } else {
                statement.setLong(5, entity.timeUnitId());
            }
            statement.setLong(6, entity.id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("LogTimeInfo not found, id: " + entity.id());
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update LogTimeInfo: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = "DELETE FROM LogTimeInfo WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("LogTimeInfo not found, id: " + id);
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete LogTimeInfo, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM LogTimeInfo";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all LogTimeInfos", ex);
        }
    }
}
