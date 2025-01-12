package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.TemplateEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
            statement.setDouble(6, entity.allocatedTime());
            if (entity.timeUnitId() == null) {
                statement.setNull(7, java.sql.Types.BIGINT);
            } else {
                statement.setObject(7, entity.timeUnitId(), java.sql.Types.BIGINT);
            }

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
        var sql = """
                SELECT id,
                    description,
                    categoryId,
                    templateName,
                    taskName,
                    assignedTo,
                    allocatedTime,
                    timeUnitId
                FROM Template
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {

            List<TemplateEntity> templates = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var template = templateFromResultSet(resultSet);
                    templates.add(template);
                }
            }

            return templates;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all templates", ex);
        }
    }

    @Override
    public Optional<TemplateEntity> findById(Long id) {
        var sql = """
                SELECT id,
                    description,
                    categoryId,
                    templateName,
                    taskName,
                    assignedTo,
                    allocatedTime,
                    timeUnitId
                FROM Template
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(templateFromResultSet(resultSet));
            } else {
                // template not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load template by id", ex);
        }
    }

    @Override
    public TemplateEntity update(TemplateEntity entity) {
        var sql = """
                UPDATE Template
                SET description = ?,
                    categoryId = ?,
                    templateName = ?,
                    taskName = ?,
                    assignedTo = ?,
                    allocatedTime = ?,
                    timeUnitId = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, entity.description());
            statement.setLong(2, entity.categoryId());
            statement.setString(3, entity.templateName());
            statement.setString(4, entity.taskName());
            statement.setString(5, entity.assignedTo());
            statement.setDouble(6, entity.allocatedTime());

            if (entity.timeUnitId() == null) {
                statement.setNull(7, java.sql.Types.BIGINT);
            } else {
                statement.setObject(7, entity.timeUnitId(), java.sql.Types.BIGINT);
            }

            statement.setLong(8, entity.id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update template: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = "DELETE FROM Template WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Template not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 template (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete template, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Template";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all templates", ex);
        }
    }

    private static TemplateEntity templateFromResultSet(ResultSet resultSet) throws SQLException {
        return new TemplateEntity(
                resultSet.getLong("id"),
                resultSet.getString("description"),
                resultSet.getLong("categoryId"),
                resultSet.getString("templateName"),
                resultSet.getString("taskName"),
                resultSet.getString("assignedTo"),
                resultSet.getDouble("allocatedTime"),
                (Long) resultSet.getObject("timeUnitId")
        );
    }
}
