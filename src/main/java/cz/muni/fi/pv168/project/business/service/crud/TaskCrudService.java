package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.Template;
import cz.muni.fi.pv168.project.business.repository.Repository;

import java.util.List;

/**
 * Crud operations for the {@link Task} entity.
 */
public class TaskCrudService implements CrudService<Task> {

    private final Repository<Task> taskRepository;

    public TaskCrudService(Repository<Task> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public boolean create(Task newEntity) {
        var savedEntity = taskRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return true; //TODO validation
    }

    @Override
    public boolean update(Task entity) {
        taskRepository.update(entity);

        return true; //TODO validation
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        taskRepository.deleteAll();
    }
}
