package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladimir Borek
 */
public class CategorySqlRepository implements Repository<Category> {
    private final DataAccessObject<CategoryEntity> categoryDao;
    private final CategoryMapper categoryMapper;

    public CategorySqlRepository(DataAccessObject<CategoryEntity> categoryDao,
                                 CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao
                .findAll()
                .stream()
                .map(categoryMapper::mapToBusiness)
                .toList();
    }

    @Override
    public Category create(Category newEntity) {
        return categoryMapper.mapToBusiness(categoryDao.create(categoryMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(Category entity) {
        CategoryEntity existingCategory = categoryDao.findById(entity.getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " + entity.getId()));
        CategoryEntity updatedEntity = categoryMapper.mapExistingEntityToDatabase(entity, existingCategory.id());

        categoryDao.update(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        categoryDao.deleteAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryDao.
                findById(id)
                .map(categoryMapper::mapToBusiness);
    }
}
