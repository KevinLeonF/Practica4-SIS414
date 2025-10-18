package com.miniorm.service;

import com.miniorm.core.GenericRepository;
import com.miniorm.dto.RegisterProductDto;
import com.miniorm.models.Product;

import java.sql.Timestamp;
import java.util.UUID;

public class ProductService {
    private final GenericRepository<UUID, Product> productRepository;

    public ProductService(GenericRepository<UUID, Product> productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(RegisterProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setStock(productDto.stock());

        UUID uuid = UUID.randomUUID();
        product.setId(uuid);
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
        return productRepository.save(uuid, product);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id, null).orElse(null);
    }

    public Product updateProduct(UUID id, Product product) {
        return productRepository.update(id, product);
    }

    public Boolean deleteProduct(UUID id, Product product) {
        return productRepository.delete(id, product);
    }

    public void listAllProducts() {
        productRepository.findAll().forEach(System.out::println);
    }
}