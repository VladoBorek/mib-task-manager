package cz.muni.fi.pv168.project.storage.memory;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;
import cz.muni.fi.pv168.project.business.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic implementation of {@link Repository} which persists entities in memory.
 *
 * @param <T> entity type
 */
public class InMemoryRepository<T extends Entity> implements Repository<T> {

    private Map<Long, T> data = new HashMap<>();

    public InMemoryRepository(Collection<T> initEntities) {
        initEntities.forEach(this::create);
    }

    public Optional<T> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAll() {
        return data.values().stream()
                .toList();
    }

    @Override
    public T create(T newEntity) {
        if (newEntity.getId() == null) {
            //finds max id
            var newId = data.values().stream()
                    .map(Entity::getId)
                    .max(Long::compareTo)
                    .orElse(0L) + 1;
            newEntity.setId(newId);
        }
        data.put(newEntity.getId(), newEntity);
        return newEntity;
    }

    @Override
    public void update(T entity) {
        var entityOptional = findById(entity.getId());
        if (entityOptional.isEmpty()) {
            throw new IllegalArgumentException("No existing entity found with given id: " + entity.getId());
        }
        data.put(entity.getId(), entity);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        data.remove(id);
    }

    @Override
    public void deleteAll() {
        data = new HashMap<>();
    }
}