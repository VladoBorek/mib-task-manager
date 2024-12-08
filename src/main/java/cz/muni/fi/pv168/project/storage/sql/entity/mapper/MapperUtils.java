package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;

/**
 * Utils class for mappers, contains static methods for entity retrieval
 * @author Maroš Pavlík
 */
public class MapperUtils {
    public static CategoryEntity getCategoryEntityById(DataAccessObject<CategoryEntity> categoryDao, Task entity) {
        return categoryDao
                .findById(entity.getCategory().getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getCategory().getId()));
    }

    public static TimeUnitEntity getTimeUnitEntityById(DataAccessObject<TimeUnitEntity> timeUnitDao, Task entity) {
        return timeUnitDao
                .findById(entity.getTimeUnit().getId())
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                        entity.getTimeUnit().getId()));
    }

    public static Category getCategoryById(DataAccessObject<CategoryEntity> categoryDao, TaskEntity entity,
                                           EntityMapper<CategoryEntity, Category> categoryMapper) {
        return categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));
    }

    public static TimeUnit getTimeUnitById(DataAccessObject<TimeUnitEntity> timeUnitDao, TaskEntity entity,
                                           EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper) {
        return timeUnitDao
                .findById(entity.timeUnitId())
                .map(timeUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                        entity.timeUnitId()));
    }
}
