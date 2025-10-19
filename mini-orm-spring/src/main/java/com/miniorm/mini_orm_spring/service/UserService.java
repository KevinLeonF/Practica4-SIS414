package com.miniorm.mini_orm_spring.service;

import com.miniorm.mini_orm_spring.core.GenericRepository;
import com.miniorm.mini_orm_spring.dto.RegisterUserDto;
import com.miniorm.mini_orm_spring.models.User;

import java.sql.Timestamp;
import java.util.UUID;

public class UserService {
    private final GenericRepository<UUID, User> userRepository;

    public UserService(GenericRepository<UUID, User> userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(RegisterUserDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        UUID uuid = UUID.randomUUID();
        user.setId(uuid);
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        return userRepository.save(uuid, user);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id, null).orElse(null);
    }

    public User updateUser(UUID id, User user) {
        return userRepository.update(id, user);
    }

    public Boolean deleteUser(UUID id, User user) {
        return userRepository.delete(id, user);
    }

    public void listAllUsers() {
        userRepository.findAll().forEach(System.out::println);
    }
}
