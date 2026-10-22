package com.hcl.auth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Generic in-memory store. LinkedHashMap gives fast key lookup and predictable insertion order.
 */
public final class InMemoryRepository<T extends Identifiable<ID>, ID>
        implements Repository<T, ID> {
    private final Map<ID, T> entities = new LinkedHashMap<>();

    @Override
    public void save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null.");
        }
        ID id = entity.getId();
        if (entities.containsKey(id)) {
            throw new IllegalArgumentException("An entity with ID " + id + " already exists.");
        }
        entities.put(id, entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(new ArrayList<>(entities.values()));
    }
}
