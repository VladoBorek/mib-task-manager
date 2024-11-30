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
 * Mapper from the {@link TaskEntity} to {@link Task}.
 * @author Maroš Pavlík
 */
public class TaskMapper implements EntityMapper<TaskEntity, Task>{

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;


    public TaskMapper(DataAccessObject<CategoryEntity> categoryDao,
                      EntityMapper<CategoryEntity, Category> categoryMapper,
                      DataAccessObject<TimeUnitEntity> timeUnitDao,
                      EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
    }

    @Override
    public Task mapToBusiness(TaskEntity entity) {
        var category = categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));
        var timeUnit = timeUnitDao
                .findById(entity.timeUnitId())
                .map(timeUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                        entity.timeUnitId()));

        return new Task(
                entity.id(),
                entity.status(),
                entity.description(),
                category,
                entity.customer(),
                entity.name(),
                entity.assignedTo(),
                entity.loggedTime(),
                entity.allocatedTime(),
                timeUnit,
                entity.dueDate()
        );
    }

    @Override
    public TaskEntity mapNewEntityToDatabase(Task entity) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getCategory().getId()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getId())
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                        entity.getTimeUnit().getId()));

        return new TaskEntity(
                entity.getId(),
                entity.getStatus(),
                entity.getDescription(),
                categoryEntity.id(),
                entity.getCustomer(),
                entity.getName(),
                entity.getAssignedTo(),
                entity.getLoggedTime(),
                entity.getAllocatedTime(),
                timeUnitEntity.id(),
                entity.getDueDate()
        );
    }

    @Override
    public TaskEntity mapExistingEntityToDatabase(Task entity, Long dbId) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getCategory().getId()));
        var timeUnitEntity = timeUnitDao
                .findById(entity.getTimeUnit().getId())
                .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                        entity.getTimeUnit().getId()));

        return new TaskEntity(
                dbId,
                entity.getStatus(),
                entity.getDescription(),
                categoryEntity.id(),
                entity.getCustomer(),
                entity.getName(),
                entity.getAssignedTo(),
                entity.getLoggedTime(),
                entity.getAllocatedTime(),
                timeUnitEntity.id(),
                entity.getDueDate()
        );
    }
}
