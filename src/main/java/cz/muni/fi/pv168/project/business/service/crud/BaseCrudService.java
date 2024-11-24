package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Task;
import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

/**
 * Crud operations for the {@link Task} entity.
 */
public class BaseCrudService<T extends Entity> implements CrudService<T> {

    private final Repository<T> repository;
    private final Validator<T> validator;

    public BaseCrudService(Repository<T> taskRepository, Validator<T> validator) {
        this.repository = taskRepository;
        this.validator = validator;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public ValidationResult create(T newEntity) {
        var validationResult = validator.validate(newEntity);
        if (validationResult.isValid()) {
            var savedEntity = repository.create(newEntity);
            newEntity.setId(savedEntity.getId());
        }
        return validationResult;
    }

    @Override
    public ValidationResult update(T entity) {
        var validationResult = validator.validate(entity);
        if (validationResult.isValid()) {
            repository.update(entity);
        }
        return validationResult;
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
