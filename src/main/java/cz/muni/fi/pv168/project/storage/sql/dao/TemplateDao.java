package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * DAO for {@link TemplateEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class TemplateDao implements DataAccessObject<TemplateEntity> {

    private final Supplier<ConnectionHandler> connections;

    public TemplateDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public TemplateEntity create(TemplateEntity entity) {
        var sql = """
                INSERT INTO Template(
                    description,
                    categoryId,
                    templateName,
                    taskName,
                    assignedTo,
                    allocatedTime,
                    timeUnitId
                )
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.description());
            statement.setLong(2, entity.categoryId());
            statement.setString(3, entity.templateName());
            statement.setString(4, entity.taskName());
            statement.setString(5, entity.assignedTo());
            statement.setInt(6, entity.allocatedTime());
            statement.setLong(7, entity.timeUnitId());

            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                long templateId;

                if (keyResultSet.next()) {
                    templateId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + entity);
                }

                return findById(templateId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<TemplateEntity> findAll() {
        return null;
    }

    @Override
    public Optional<TemplateEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TemplateEntity update(TemplateEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
