
package auth_service.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryRepository<T, ID>
        implements CrudRepository<T, ID> {

    private final Map<ID, T> storage = new LinkedHashMap<>();
    private final Function<T, ID> idExtractor;

    public InMemoryRepository(Function<T, ID> idExtractor) {
        if (idExtractor == null) {
            throw new IllegalArgumentException(
                    "ID extractor must not be null");
        }
        this.idExtractor = idExtractor;
    }

    @Override
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Entity must not be null");
        }

        ID id = idExtractor.apply(entity);

        if (id == null) {
            throw new IllegalArgumentException(
                    "Entity ID must not be null");
        }

        storage.put(id, entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(new ArrayList<>(storage.values()));
    }

    @Override
    public boolean deleteById(ID id) {
        if (id == null) {
            return false;
        }
        return storage.remove(id) != null;
    }

    @Override
    public long count() {
        return storage.size();
    }
}
