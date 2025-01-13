package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.LogTimeInfo;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.TimeUnit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.LogTimeInfoEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.TimeUnitEntity;
import cz.muni.fi.pv168.project.util.Constants;

import java.util.List;

/**
 * Mapper from the {@link TaskEntity} to {@link Task}.
 *
 * @author Maroš Pavlík
 */
public class TaskMapper implements EntityMapper<TaskEntity, Task> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final DataAccessObject<TimeUnitEntity> timeUnitDao;
    private final DataAccessObject<LogTimeInfoEntity> logTimeInfoDao;
    private final EntityMapper<CategoryEntity, Category> categoryMapper;
    private final EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper;
    private final EntityMapper<LogTimeInfoEntity, LogTimeInfo> logTimeInfoMapper;


    public TaskMapper(DataAccessObject<CategoryEntity> categoryDao,
                      EntityMapper<CategoryEntity, Category> categoryMapper,
                      DataAccessObject<TimeUnitEntity> timeUnitDao,
                      EntityMapper<TimeUnitEntity, TimeUnit> timeUnitMapper,
                      DataAccessObject<LogTimeInfoEntity> logTimeInfoDao,
                      EntityMapper<LogTimeInfoEntity, LogTimeInfo> logTimeInfoMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.timeUnitDao = timeUnitDao;
        this.timeUnitMapper = timeUnitMapper;
        this.logTimeInfoDao = logTimeInfoDao;
        this.logTimeInfoMapper = logTimeInfoMapper;
    }

    @Override
    public Task mapToBusiness(TaskEntity entity) {
        var category = categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));


        TimeUnit timeUnit;
        if (entity.timeUnitId() == null) {
            timeUnit = Constants.BASE_TIME_UNIT;
        } else {
            timeUnit = timeUnitDao
                    .findById(entity.timeUnitId())
                    .map(timeUnitMapper::mapToBusiness)
                    .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                            entity.timeUnitId()));
        }


            var newTask = new Task(
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
                entity.dueDate());

        addLogsToTask(newTask, entity);
        return newTask;
    }

    private void addLogsToTask(Task task, TaskEntity entity) {
        List<LogTimeInfo> logs = logTimeInfoDao.findAll().stream()
                .filter(log -> log.taskId().equals(entity.id()))
                .map(logTimeInfoMapper::mapToBusiness)
                .toList();

        for (LogTimeInfo log : logs) {
            task.addLog(log);
        }
    }

    @Override
    public TaskEntity mapNewEntityToDatabase(Task entity) {
        return mapExistingEntityToDatabase(entity, entity.getId());
    }

    @Override
    public TaskEntity mapExistingEntityToDatabase(Task entity, Long dbId) {
        var categoryEntity = categoryDao
                .findById(entity.getCategory().getId())
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.getCategory().getId()));


        Long timeUnitId;
        if (entity.getTimeUnit().equals(Constants.BASE_TIME_UNIT)) {
            timeUnitId = null;
        } else {
            var timeUnitEntity = timeUnitDao
                    .findById(entity.getTimeUnit().getId())
                    .orElseThrow(() -> new DataStorageException("Time Unit not found, id: " +
                            entity.getTimeUnit().getId()));
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
