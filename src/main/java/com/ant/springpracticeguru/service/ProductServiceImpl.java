package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.controller.NotFoundCustomException;
import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.domain.ProductStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private Map<UUID, ProductDTO> products;

    public ProductServiceImpl() {
        this.products = new HashMap<>();

        ProductDTO Product1 = ProductDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Galaxy Cat")
                .productStyle(ProductStyle.LAGER)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        ProductDTO Product2 = ProductDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Crank")
                .productStyle(ProductStyle.PILSNER)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        ProductDTO Product3 = ProductDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .productName("Sunshine City")
                .productStyle(ProductStyle.PORTER)
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
    public List<ProductDTO> findAll() {
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Optional<ProductDTO> findById(UUID id) {
        log.debug("Get product by Id - in service. Id: " + id.toString());
        return Optional.of(this.products.get(id));
    }

    @Override
    public ProductDTO add(ProductDTO productDTO) {
        ProductDTO saveProduct = ProductDTO.builder()
                .id(productDTO.getId())
                .version(productDTO.getVersion())
                .productName(productDTO.getProductName())
                .productStyle(productDTO.getProductStyle())
                .upc(productDTO.getUpc())
                .quantityOnHand(productDTO.getQuantityOnHand())
                .price(productDTO.getPrice())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        this.products.put(saveProduct.getId(), saveProduct);
        return saveProduct;
    }

    @Override
    public Optional<ProductDTO> updateById(UUID productId, ProductDTO productDTO) {
        Optional<ProductDTO> current = findById(productId);
        if (current.isPresent()) {
            ProductDTO p = current.get();
            p.setProductName(productDTO.getProductName());
            p.setProductStyle(productDTO.getProductStyle());
            p.setUpc(productDTO.getUpc());
            p.setQuantityOnHand(productDTO.getQuantityOnHand());
            p.setPrice(productDTO.getPrice());
            return Optional.of(p);
        } else {
            throw new NotFoundCustomException("Item not exist to make update");
        }
    }

    @Override
    public Boolean delete(UUID productId) {
        this.products.remove(productId);
        return true;
    }

    @Override
    public Optional<ProductDTO> patchProduct(UUID productId, ProductDTO productDTO) {
        ProductDTO existing = products.get(productId);

        if (StringUtils.hasText(productDTO.getProductName())) {
            existing.setProductName(productDTO.getProductName());
        }

        if (productDTO.getProductStyle() != null) {
            existing.setProductStyle(productDTO.getProductStyle());
        }

        if (productDTO.getPrice() != null) {
            existing.setPrice(productDTO.getPrice());
        }

        if (productDTO.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(productDTO.getQuantityOnHand());
        }

        if (StringUtils.hasText(productDTO.getUpc())) {
            existing.setUpc(productDTO.getUpc());
        }
        this.products.put(existing.getId(), existing);
        return Optional.ofNullable(this.products.get(existing.getId()));
    }


}
