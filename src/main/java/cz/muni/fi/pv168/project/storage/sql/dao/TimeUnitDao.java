package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link TimeUnitEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class TimeUnitDao implements DataAccessObject<TimeUnitEntity> {
    private final Supplier<ConnectionHandler> connections;

    public TimeUnitDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TimeUnitEntity create(TimeUnitEntity entity) {
        var sql = """
                INSERT INTO TimeUnit(
                    name,
                    shortName,
                    rate
                )
                VALUES (?, ?, ?);
                """;

        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setString(2, entity.shortName());
            statement.setInt(3, entity.rate());

            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                long timeUnitId;

                if (keyResultSet.next()) {
                    timeUnitId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + entity);
                }

                return findById(timeUnitId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<TimeUnitEntity> findAll() {
        var sql = """
                SELECT id,
                    name,
                    shortName,
                    rate
                FROM TimeUnit
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {

            List<TimeUnitEntity> timeUnits = new ArrayList<>();
            timeUnits.add(null);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var timeUnit = timeUnitFromResultSet(resultSet);
                    timeUnits.add(timeUnit);
                }
            }

            return timeUnits;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all time units", ex);
        }
    }

    @Override
    public Optional<TimeUnitEntity> findById(Long id) {
        var sql = """
                SELECT id,
                    name,
                    shortName,
                    rate
                FROM TimeUnit
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(timeUnitFromResultSet(resultSet));
            } else {
                // timeUnit not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load time unit by id", ex);
        }
    }

    @Override
    public TimeUnitEntity update(TimeUnitEntity entity) {
        var sql = """
                UPDATE TimeUnit
                SET name = ?,
                    shortName = ?,
                    rate = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, entity.name());
            statement.setString(2, entity.shortName());
            statement.setInt(3, entity.rate());
            statement.setLong(4, entity.id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Time unit not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 time unit (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update time unit: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = "DELETE FROM TimeUnit WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Time unit not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 time unit (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete time unit, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM TimeUnit";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all time units", ex);
        }
    }

    private static TimeUnitEntity timeUnitFromResultSet(ResultSet resultSet) throws SQLException {
        return new TimeUnitEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("shortName"),
                resultSet.getInt("rate")
        );
    }
}
