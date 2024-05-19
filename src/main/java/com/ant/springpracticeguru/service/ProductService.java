package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();

    Product getProductById(UUID id);
}
