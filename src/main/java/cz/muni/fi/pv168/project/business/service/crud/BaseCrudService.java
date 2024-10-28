package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.repository.Repository;

import java.util.List;

/**
 * Crud operations for the {@link Task} entity.
 */
public class BaseCrudService<T extends Entity> implements CrudService<T> {

    private final Repository<T> taskRepository;

    public BaseCrudService(Repository<T> taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<T> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public boolean create(T newEntity) {
        var savedEntity = taskRepository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return true; //TODO validation
    }

    @Override
    public boolean update(T entity) {
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
