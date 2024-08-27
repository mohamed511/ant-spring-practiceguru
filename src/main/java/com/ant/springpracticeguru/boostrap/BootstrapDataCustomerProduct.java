package com.ant.springpracticeguru.boostrap;

import com.ant.springpracticeguru.domain.ProductStyle;
import com.ant.springpracticeguru.entities.Customer;
import com.ant.springpracticeguru.entities.Product;
import com.ant.springpracticeguru.repository.CustomerRepository;
import com.ant.springpracticeguru.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@AllArgsConstructor
public class BootstrapDataCustomerProduct implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
        loadProductData();
    }

    private void loadProductData() {
        if (productRepository.count() == 0) {
            Product product1 = Product.builder()
                    .productName("Galaxy Cat")
                    .productStyle(ProductStyle.ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Product product2 = Product.builder()
                    .productName("Crank")
                    .productStyle(ProductStyle.GOSE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Product product3 = Product.builder()
                    .productName("Sunshine City")
                    .productStyle(ProductStyle.LAGER)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Customer 1")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Customer 2")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Customer 3")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
