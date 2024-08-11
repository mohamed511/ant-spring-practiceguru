package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.entities.Product;
import com.ant.springpracticeguru.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerIntegrationTest {
    @Autowired
    ProductController productController;
    @Autowired
    ProductRepository productRepository;

    @Test
    void testFindProductByIdNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.productController.getProductById(UUID.randomUUID());
        });
    }

    @Test
    void testFindProductById() {
        Product product = this.productRepository.findAll().get(0);
        ProductDTO productDTO = this.productController.getProductById(product.getId());
        assertThat(productDTO).isNotNull();
    }

    @Test
    void testFindAllProduct() {
        List<ProductDTO> product = this.productController.productList();
        assertThat(product).isNotNull().hasSize(3);
    }

    @Test
    void testFindAllProductNotData() {
        this.productRepository.deleteAll();
        List<ProductDTO> product = this.productController.productList();
        assertThat(product).isEmpty();
    }
}