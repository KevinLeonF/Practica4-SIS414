package com.miniorm.mini_orm_spring.core;

import com.miniorm.mini_orm_spring.annotations.Id;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRepository <ID, T> implements GenericRepository<ID, T> {

    private final Class<T> entityClass;
    private final Map<Object, List<Map<ID, T>>> storage = new ConcurrentHashMap<>(); // Thread-safe map for in-memory storage
    private final AtomicInteger sequence = new AtomicInteger(1); // For generating unique IDs if needed
    private Field idField;

    public InMemoryRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        for (Field field : this.entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                this.idField = field;
                break;
            }
        }
        if (this.idField == null) {
            throw new IllegalArgumentException("No @Id field found in class " + entityClass.getName());
        }
    }

    private Object getId(T entity) {
        try {
            return idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access ID field", e);
        }
    }

    private void setIdValue(T entity, Object id) {
        try {
            idField.set(entity, id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set ID field", e);
        }
    }

    @Override
    public T save(ID id,T entity) {
        Object entityClassId = getId(entity);
        if (id == null || (id instanceof Integer && ((Integer) id) == 0)) {
            setIdValue(entity, sequence.getAndIncrement());
        }
        if (!storage.containsKey(entityClassId)) {
            storage.put(entityClassId, new ArrayList<>());
        }
        assert id != null;
        storage.get(entityClassId).add(Map.of(id, entity));
        return entity;
    }

    @Override
    public Optional<T> findById(ID id, Object o) {
        return storage.get(o).stream()
                .map(m -> m.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }

    @Override
    public List<Map<ID, T>> findAll() {
        List<Map<ID, T>> all = new ArrayList<>();
        for (List<Map<ID, T>> maps : storage.values()) {
            all.addAll(maps);
        }
        return all;
    }

    @Override
    public Boolean delete(ID id, T entity) {
        return storage.remove(entity) != null;
    }

    @Override
    public T update(ID id, T entity) {
        return storage.put(getId(entity), List.of(Map.of(id, entity))) != null ? entity : null;
    }
}
