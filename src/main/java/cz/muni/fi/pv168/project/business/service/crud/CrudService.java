package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Entity;

import java.util.List;

/**
 * Service for creation, read, update, and delete operations.
 *
 * @param <T> entity type.
 */
public interface CrudService<T extends Entity> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Validate and store the given {@code newEntity}.
     * TODO create validation
     */
    boolean create(T newEntity);

    /**
     * Updates the given {@code entity}.
     * TODO create validation
     */
    boolean update(T entity);

    /**
     * Delete entity with given {@code id}.
     */
    void deleteById(Long id);

    /**
     * Delete all entities.
     */
    void deleteAll();
}