package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link Task} entity.
 *
 * @author Maroš Pavlík
 */
public class TaskDao implements DataAccessObject<Task> {

    private final Supplier<ConnectionHandler> connections;

    public TaskDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }


    @Override
    public Task create(TaskEntity newTask) {
        var sql = """
                INSERT INTO Task(
                    name,
                    customer,
                    category,
                    assignedTo,
                    status,
                    dueDate,
                    description,
                    allocatedTime,
                    loggedTime,
                    timeUnitId
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newTask.name());
            statement.setString(2, newTask.customer());
            statement.setString(3, newTask.category());
            statement.setString(4, newTask.assignedTo());
            statement.setString(5, newTask.status().toString());
            statement.setString(6, Date.valueOf(newTask.dueDate()));
            statement.setString(7, newTask.description());
            statement.setString(8, newTask.allocatedTime().toString());
            statement.setString(9, newTask.loggedTime().toString());
            statement.setString(10, newTask.timeUnitId());


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
    public Collection<Task> findAll() {
        return null;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Task update(Task entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
