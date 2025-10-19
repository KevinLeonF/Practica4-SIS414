package com.miniorm.mini_orm_spring.core;

public class EntityManager {
    public <ID, T> GenericRepository<ID, T> getRepository(Class<T> clazz) {
        return new InMemoryRepository<>(clazz);
    }
}