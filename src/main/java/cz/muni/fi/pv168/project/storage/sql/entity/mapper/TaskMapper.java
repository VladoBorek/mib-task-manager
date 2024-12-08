package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;
import cz.muni.fi.pv168.project.util.Constants;

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
        var category = MapperUtils.getCategoryById(categoryDao, entity, categoryMapper);

        TimeUnit timeUnit;
        if (entity.timeUnitId() == null) {
            timeUnit = Constants.BASE_TIME_UNIT;
        } else {
            timeUnit = MapperUtils.getTimeUnitById(timeUnitDao, entity, timeUnitMapper);
        }

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
        var categoryEntity = MapperUtils.getCategoryEntityById(categoryDao, entity);

        Long timeUnitId;
        if (entity.getTimeUnit().equals(Constants.BASE_TIME_UNIT)) {
            timeUnitId = null;
        } else {
            var timeUnitEntity = MapperUtils.getTimeUnitEntityById(timeUnitDao, entity);
            timeUnitId = timeUnitEntity.id();
        }

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
                timeUnitId,
                entity.getDueDate()
        );
    }

    // TODO: duplication
    @Override
    public TaskEntity mapExistingEntityToDatabase(Task entity, Long dbId) {
        var categoryEntity = MapperUtils.getCategoryEntityById(categoryDao, entity);

        Long timeUnitId;
        if (entity.getTimeUnit().equals(Constants.BASE_TIME_UNIT)) {
            timeUnitId = null;
        } else {
            var timeUnitEntity = MapperUtils.getTimeUnitEntityById(timeUnitDao, entity);
            timeUnitId = timeUnitEntity.id();
        }

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
                timeUnitId,
                entity.getDueDate()
        );
    }
}
