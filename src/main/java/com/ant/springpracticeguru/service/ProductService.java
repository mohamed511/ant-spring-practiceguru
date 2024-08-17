package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(UUID id);

    ProductDTO add(ProductDTO productDTO);

    Optional<ProductDTO> updateById(UUID productId, ProductDTO productDTO);

    void delete(UUID productId);

    void patchProduct(UUID productId, ProductDTO productDTO);
}
