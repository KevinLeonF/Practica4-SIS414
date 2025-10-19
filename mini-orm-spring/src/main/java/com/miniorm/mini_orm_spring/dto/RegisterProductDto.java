package com.miniorm.mini_orm_spring.dto;

public record RegisterProductDto(
    String name,
    String description,
    Double price,
    Integer stock
) {}