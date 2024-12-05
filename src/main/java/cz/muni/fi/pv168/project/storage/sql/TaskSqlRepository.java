package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.TaskEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.TaskMapper;

import java.util.List;
import java.util.Optional;

/**
 * @author Vladimir Borek
 */
public class TaskSqlRepository implements Repository<Task> {
    private final DataAccessObject<TaskEntity> taskDao;
    private final TaskMapper taskMapper;

    public TaskSqlRepository(DataAccessObject<TaskEntity> taskDao,
                             TaskMapper taskMapper) {
        this.taskDao = taskDao;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> findAll() {
        return taskDao
                .findAll()
                .stream()
                .map(taskMapper::mapToBusiness)
                .toList();
    }

    @Override
    public Task create(Task newEntity) {
        return taskMapper.mapToBusiness(taskDao.create(taskMapper.mapNewEntityToDatabase(newEntity)));
    }

    @Override
    public void update(Task entity) {
        TaskEntity existingCategory = taskDao.findById(entity.getId())
                .orElseThrow(() -> new DataStorageException("Task not found, id: " + entity.getId()));
        TaskEntity updatedEntity = taskMapper.mapExistingEntityToDatabase(entity, existingCategory.id());

        taskDao.update(updatedEntity);
    }

    @Override
    public void deleteById(Long id) {
        taskDao.deleteById(id);
    }

    @Override
    public void deleteAll() {
        taskDao.deleteAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskDao.
                findById(id)
                .map(taskMapper::mapToBusiness);
    }
}
