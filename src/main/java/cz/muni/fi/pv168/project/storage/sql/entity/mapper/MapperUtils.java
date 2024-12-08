package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;

import java.util.function.Function;

public class MapperUtils {

    public static CategoryEntity getCategoryEntityById(
            DataAccessObject<CategoryEntity> categoryDao,
            Object entity,
            Function<Object, Long> getCategoryId) {

        Long categoryId = getCategoryId.apply(entity);
        return categoryDao
                .findById(categoryId)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " + categoryId));
    }

    public static TimeUnitEntity getTimeUnitEntityById(
            DataAccessObject<TimeUnitEntity> timeUnitDao,
            Object entity,
            Function<Object, Long> getTimeUnitId) {

        Long timeUnitId = getTimeUnitId.apply(entity);
        return timeUnitDao
                .findById(timeUnitId)
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " + timeUnitId));
    }

    public static Category getCategoryById(
            DataAccessObject<CategoryEntity> categoryDao,
            Object entity,
            EntityMapper<CategoryEntity, Category> categoryMapper,
            Function<Object, Long> getCategoryId) {

        Long categoryId = getCategoryId.apply(entity);
        return categoryDao
                .findById(categoryId)
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " + categoryId));
    }


    public static TimeUnit getTimeUnitById(
            DataAccessObject<TimeUnitEntity> timeUnitDao,
            Object entity,
            EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper,
            Function<Object, Long> getTimeUnitId) {

        Long timeUnitId = getTimeUnitId.apply(entity);
        return timeUnitDao
                .findById(timeUnitId)
                .map(timeUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " + timeUnitId));
    }
}
