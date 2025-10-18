package com.miniorm.dto;

public record RegisterProductDto(
    String name,
    String description,
    Double price,
    Integer stock
) {}