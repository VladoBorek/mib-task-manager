package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.business.model.Status;
import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link TaskEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class TaskDao implements DataAccessObject<TaskEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TaskDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TaskEntity create(TaskEntity newTask) {
        var sql = """
                INSERT INTO Task(
                    status,
                    description,
                    categoryId,
                    customer,
                    name,
                    assignedTo,
                    loggedTime,
                    allocatedTime,
                    timeUnitId,
                    dueDate
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newTask.status().toString());
            statement.setString(2, newTask.description());
            statement.setLong(3, newTask.categoryId());
            statement.setString(4, newTask.customer());
            statement.setString(5, newTask.name());
            statement.setString(6, newTask.assignedTo());
            statement.setString(7, newTask.loggedTime().toString());
            statement.setString(8, newTask.allocatedTime().toString());
            if (newTask.timeUnitId() == null) {
                statement.setNull(9, java.sql.Types.BIGINT);
            } else {
                statement.setObject(9, newTask.timeUnitId(), java.sql.Types.BIGINT);
            }
            statement.setDate(10, Date.valueOf(newTask.dueDate()));


            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                long taskId;

                if (keyResultSet.next()) {
                    taskId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newTask);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newTask);
                }

                return findById(taskId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newTask, ex);
        }
    }

    @Override
    public Collection<TaskEntity> findAll() {
        var sql = """
                SELECT id,
                    name,
                    status,
                    description,
                    categoryId,
                    customer,
                    name,
                    assignedTo,
                    loggedTime,
                    allocatedTime,
                    timeUnitId,
                    dueDate
                FROM Task
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {

            List<TaskEntity> tasks = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var task = taskFromResultSet(resultSet);
                    tasks.add(task);
                }
            }

            return tasks;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all tasks", ex);
        }
    }

    @Override
    public Optional<TaskEntity> findById(Long id) {
        var sql = """
                SELECT id,
                    name,
                    status,
                    description,
                    categoryId,
                    customer,
                    name,
                    assignedTo,
                    loggedTime,
                    allocatedTime,
                    timeUnitId,
                    dueDate
                FROM Task
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(taskFromResultSet(resultSet));
            } else {
                // task not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load task by id", ex);
        }
    }

    @Override
    public TaskEntity update(TaskEntity entity) {
        var sql = """
                UPDATE Task
                SET status = ?,
                    description = ?,
                    categoryId = ?,
                    customer = ?,
                    name = ?,
                    assignedTo = ?,
                    loggedTime = ?,
                    allocatedTime = ?,
                    timeUnitId = ?,
                    dueDate = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, entity.status().toString());
            statement.setString(2, entity.description());
            statement.setLong(3, entity.categoryId());
            statement.setString(4, entity.customer());
            statement.setString(5, entity.name());
            statement.setString(6, entity.assignedTo());
            statement.setString(7, entity.loggedTime().toString());
            statement.setString(8, entity.allocatedTime().toString());

            if (entity.timeUnitId() == null) {
                statement.setNull(9, java.sql.Types.BIGINT);
            } else {
                statement.setObject(9, entity.timeUnitId(), java.sql.Types.BIGINT);
            }

            statement.setDate(10, Date.valueOf(entity.dueDate()));
            statement.setLong(11, entity.id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Task not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 task (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update task: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = "DELETE FROM Task WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Task not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 task (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete task, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Task";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all tasks", ex);
        }
    }

    private static TaskEntity taskFromResultSet(ResultSet resultSet) throws SQLException {
        return new TaskEntity(
                resultSet.getLong("id"),
                Status.valueOf(resultSet.getString("status")),
                resultSet.getString("description"),
                resultSet.getLong("categoryId"),
                resultSet.getString("customer"),
                resultSet.getString("name"),
                resultSet.getString("assignedTo"),
                resultSet.getDouble("loggedTime"),
                resultSet.getDouble("allocatedTime"),
                (Long) resultSet.getObject("timeUnitId"),
                resultSet.getDate("dueDate").toLocalDate()
        );
    }
}

