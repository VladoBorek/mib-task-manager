package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.repository.Repository;

import java.util.List;

/**
 * Crud operations for the {@link Task} entity.
 */
public class BaseCrudService<T extends Entity> implements CrudService<T> {

    private final Repository<T> repository;

    public BaseCrudService(Repository<T> taskRepository) {
        this.repository = taskRepository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean create(T newEntity) {
        var savedEntity = repository.create(newEntity);
        newEntity.setId(savedEntity.getId());

        return true; //TODO validation
    }

    @Override
    public boolean update(T entity) {
        repository.update(entity);

        return true; //TODO validation
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
