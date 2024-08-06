package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void testSaveProduct(){
        Product saved = productRepository.save(Product.builder().productName("test").build());
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }
}