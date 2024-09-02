package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.domain.ProductStyle;
import com.ant.springpracticeguru.entities.Product;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void testSaveProductNameTooLong(){
        Product product = Product.builder()
                .productName("123".repeat(20))
                .productStyle(ProductStyle.GOSE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .build();
        assertThrows(ConstraintViolationException.class,()->{
            Product saved = productRepository.save(product);
            productRepository.flush();
        });
    }

    @Test
    void testSaveProduct(){
        Product product = Product.builder()
                .productName("123").productStyle(ProductStyle.GOSE).upc("12356222")
                .price(new BigDecimal("11.99")).quantityOnHand(392)
                .build();
        Product saved = productRepository.save(product);
        productRepository.flush();
        assertNotNull(saved);
        assertNotNull(saved.getId());
    }
}