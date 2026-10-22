package com.hcl.auth;

import java.util.List;
import java.util.Optional;

/** Generic repository contract, bounded by Identifiable<ID>. */
public interface Repository<T extends Identifiable<ID>, ID> {
    void save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
}
