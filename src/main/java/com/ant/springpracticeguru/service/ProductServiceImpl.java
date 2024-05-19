package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private Map<UUID, Product> products;

    public ProductServiceImpl() {
        this.products = new HashMap<>();

        Product Product1 = Product.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Galaxy Cat")
                .productColor("white")
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Product Product2 = Product.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Crank")
                .productColor("green")
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Product Product3 = Product.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Sunshine City")
                .productColor("red")
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        products.put(Product1.getId(), Product1);
        products.put(Product2.getId(), Product2);
        products.put(Product3.getId(), Product3);
    }

    @Override
    public List<Product> findAll(){
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Product getProductById(UUID id) {

        log.debug("Get product by Id - in service. Id: " + id.toString());

        return this.products.get(id);
    }
}
