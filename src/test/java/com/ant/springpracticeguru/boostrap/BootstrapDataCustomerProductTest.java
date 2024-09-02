package com.ant.springpracticeguru.boostrap;

import com.ant.springpracticeguru.repository.CustomerRepository;
import com.ant.springpracticeguru.repository.ProductRepository;
import com.ant.springpracticeguru.service.ProductCsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapDataCustomerProductTest {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCsvService productCsvService;
    BootstrapDataCustomerProduct bootstrapDataCustomerProduct;

    @BeforeEach
    void setUp() {
        bootstrapDataCustomerProduct = new BootstrapDataCustomerProduct(customerRepository, productRepository, productCsvService);
    }

    @Test
    void run() throws Exception {
        bootstrapDataCustomerProduct.run(null);
        assertThat(customerRepository.count()).isEqualTo(3);
        assertThat(productRepository.count()).isEqualTo(3);
    }
}