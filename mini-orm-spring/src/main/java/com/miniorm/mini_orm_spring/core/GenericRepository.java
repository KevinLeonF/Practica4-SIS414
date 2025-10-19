package com.miniorm.mini_orm_spring.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericRepository<ID, T> {
    T save(ID id, T entity);
    Optional<T> findById(ID id, Object o);
    T update(ID id, T entity);
    Boolean delete(ID id, T entity);
    List<Map<ID, T>> findAll();
}