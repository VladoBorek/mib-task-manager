package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
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
 * DAO for {@link CategoryEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class CategoryDao implements DataAccessObject<CategoryEntity> {

    private final Supplier<ConnectionHandler> connections;

    public CategoryDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public CategoryEntity create(CategoryEntity entity) {
        var sql = """
                INSERT INTO Category(
                    name,
                    color
                )
                VALUES (?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setInt(2, entity.color());

            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                long categoryId;

                if (keyResultSet.next()) {
                    categoryId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + entity);
                }

                return findById(categoryId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        var sql = """
                SELECT id,
                    name,
                    color
                FROM Category
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {

            List<CategoryEntity> categories = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var category = categoryFromResultSet(resultSet);
                    categories.add(category);
                }
            }

            return categories;
        } catch (SQLException ex) {
            Logger.error(ex.getMessage());
            throw new DataStorageException("Failed to load all categories", ex);
        }
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        var sql = """
                SELECT id,
                    name,
                    color
                FROM Category
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(categoryFromResultSet(resultSet));
            } else {
                // category not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load category by id", ex);
        }
    }

    @Override
    public CategoryEntity update(CategoryEntity entity) {
        var sql = """
                UPDATE Category
                SET name = ?,
                    color = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setString(1, entity.name());
            statement.setInt(2, entity.color());
            statement.setLong(3, entity.id());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Category not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 category (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update category: " + entity, ex);
        }
    }

    @Override
    public void deleteById(Long id) {
        var sql = "DELETE FROM Category WHERE id = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("Category not found, id: " + id);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 category (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, id));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete category, id: " + id, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM Category";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all categories", ex);
        }
    }

    private static CategoryEntity categoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new CategoryEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("color")
        );
    }
}
