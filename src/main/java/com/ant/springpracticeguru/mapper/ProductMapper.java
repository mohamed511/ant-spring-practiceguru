package com.ant.springpracticeguru.mapper;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.entities.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    Product productDtoToProduct(ProductDTO dto);

    ProductDTO productToProductDto(Product product);
}
