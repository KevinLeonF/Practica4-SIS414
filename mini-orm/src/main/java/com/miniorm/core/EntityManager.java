package com.miniorm.core;

public class EntityManager {
    public <ID, T> GenericRepository<ID, T> getRepository(Class<T> clazz) {
        return new InMemoryRepository<>(clazz);
    }
}