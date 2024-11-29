package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;

import java.util.Collection;
import java.util.Optional;

/**
 * DAO for {@link CategoryEntity} entity.
 *
 * @author Maroš Pavlík
 */
public class CategoryDao implements DataAccessObject<CategoryEntity> {

    @Override
    public CategoryEntity create(CategoryEntity entity) {
        return null;
    }

    @Override
    public Collection<CategoryEntity> findAll() {
        return null;
    }

    @Override
    public Optional<CategoryEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CategoryEntity update(CategoryEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

    }
}
