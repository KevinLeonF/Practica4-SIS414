package com.miniorm.mini_orm_spring.app;

import com.miniorm.mini_orm_spring.core.EntityManager;
import com.miniorm.mini_orm_spring.dto.RegisterUserDto;
import com.miniorm.mini_orm_spring.models.Product;
import com.miniorm.mini_orm_spring.models.User;
import com.miniorm.mini_orm_spring.service.ProductService;
import com.miniorm.mini_orm_spring.service.UserService;
 
public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = new EntityManager();
        UserService userService = new UserService(entityManager.getRepository(User.class));

        userService.createUser(new RegisterUserDto("John Doe", "john.doe@example.com", "password123"));
        userService.createUser(new RegisterUserDto("Jane Smith", "jane.smith@gmail.com", "securepass"));

        userService.listAllUsers();
        System.out.println("--- Products ---");

        ProductService productService = new ProductService(entityManager.getRepository(Product.class));

        var prod1 = productService.createProduct(new com.miniorm.mini_orm_spring.dto.RegisterProductDto("Laptop", "Portátil de alto rendimiento", 1500.0, 10));
        var prod2 = productService.createProduct(new com.miniorm.mini_orm_spring.dto.RegisterProductDto("Mouse", "Mouse inalámbrico", 25.0, 100));

        System.out.println("Lista inicial de productos:");
        productService.listAllProducts();

        prod1.setPrice(1400.0);
        prod1.setStock(8);
        prod1.setDescription("Portátil actualizado, mejor precio");
        productService.updateProduct(prod1.getId(), prod1);
        System.out.println("\nProducto actualizado:");
        System.out.println(productService.getProductById(prod1.getId()));

        productService.deleteProduct(prod2.getId(), prod2);
        System.out.println("\nLista después de eliminar Mouse:");
        productService.listAllProducts();
    }
}
