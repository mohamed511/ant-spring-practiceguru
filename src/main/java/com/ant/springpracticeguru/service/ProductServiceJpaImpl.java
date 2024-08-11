package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.mapper.ProductMapper;
import com.ant.springpracticeguru.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Primary
@AllArgsConstructor
@Service
public class ProductServiceJpaImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public List<ProductDTO> findAll() {
        return this.productRepository.findAll().stream()
                .map(productMapper::productToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findById(UUID id) {
        return Optional.ofNullable(this.productMapper.productToProductDto(productRepository.findById(id).orElse(null)));
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        return null;
    }

    @Override
    public void updateById(UUID productId, ProductDTO productDTO) {

    }

    @Override
    public void delete(UUID productId) {

    }

    @Override
    public void patchProduct(UUID productId, ProductDTO productDTO) {

    }
}
