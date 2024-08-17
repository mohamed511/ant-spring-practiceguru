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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Primary
@AllArgsConstructor
@Service
public class ProductServiceJpaImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public List<ProductDTO> findAll() {
        return this.productRepository.findAll().stream().map(productMapper::productToProductDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findById(UUID id) {
        return Optional.ofNullable(this.productMapper.productToProductDto(productRepository.findById(id).orElse(null)));
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        return productMapper.productToProductDto(productRepository.save(productMapper.productDtoToProduct(productDTO)));
    }

    @Override
    public Optional<ProductDTO> updateById(UUID productId, ProductDTO productDTO) {
        AtomicReference<Optional<ProductDTO>> atomicReference = new AtomicReference<>();

        this.productRepository.findById(productId).ifPresentOrElse(foundBeer -> {
            foundBeer.setProductName(productDTO.getProductName());
            foundBeer.setProductColor(productDTO.getProductColor());
            foundBeer.setUpc(productDTO.getUpc());
            foundBeer.setPrice(productDTO.getPrice());
            foundBeer.setQuantityOnHand(productDTO.getQuantityOnHand());
            productRepository.save(foundBeer);
            atomicReference.set(Optional.of(productMapper.productToProductDto(productRepository.save(foundBeer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean delete(UUID productId) {
        if (this.productRepository.existsById(productId)) {
            this.productRepository.deleteById(productId);
            return true;
        }
        return false;

    }

    @Override
    public void patchProduct(UUID productId, ProductDTO productDTO) {

    }
}
