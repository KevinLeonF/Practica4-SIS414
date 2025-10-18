package com.miniorm.models;

import com.miniorm.annotations.*;
import com.miniorm.enums.GenerationType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(tableName = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    public UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return
                """
                Product {
                    id='%s',
                    name='%s',
                    description='%s',
                    price='%s',
                    stock='%s',
                    createdAt='%s',
                    updatedAt='%s'
                }""".formatted(id, name, description, price, stock, createdAt, updatedAt);
    }
}