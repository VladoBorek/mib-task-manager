package cz.muni.fi.pv168.project.business.repository;

import cz.muni.fi.pv168.project.business.model.Entity;
import java.util.List;

/**
 * Represents a repository for any entity.
 *
 * @param <T> the type of the entity.
 */
public interface Repository<T extends Entity> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Persist given {@code newEntity}.
     *
     * @return the persisted entity with generated id.
     */
    T create(T newEntity);

    /**
     * Update given {@code entity}.
     */
    void update(T entity);

    /**
     * Delete entity with given {@code id}.
     */
    void deleteById(Long id);

    /**
     * Delete all entities.
     */
    void deleteAll();
}