package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.domain.ProductStyle;
import com.ant.springpracticeguru.entities.Product;
import com.ant.springpracticeguru.mapper.ProductMapper;
import com.ant.springpracticeguru.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ProductControllerIntegrationTest {

    @Autowired
    ProductController productController;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchBadProductName() throws Exception {
        Product product = productRepository.findAll().get(0);

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("productName", "New Name".repeat(20));

        MvcResult mvcResult =
        mockMvc.perform(patch(ProductController.PRODUCT_PATH_ID, product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testPatchNotFoundProduct() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.productController.updateProductPatchById(UUID.randomUUID(), ProductDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchExistingProduct() {
        final String updateName = "update";
        Product product = this.productRepository.findAll().get(0);
        ProductDTO productDto = this.productMapper.productToProductDto(product);
        productDto.setId(null);
        productDto.setVersion(null);
        productDto.setProductName(updateName);
        ResponseEntity<?> responseEntity = this.productController.updateProductPatchById(product.getId(), productDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Product findProduct = productRepository.findById(product.getId()).get();
        assertThat(findProduct.getProductName()).isEqualTo(updateName);
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.productController.deleteById(UUID.randomUUID());
        });

    }

    @Rollback
    @Transactional
    @Test
    void testDeleteById() {
        Product product = this.productRepository.findAll().get(0);
        ResponseEntity<?> responseEntity = this.productController.deleteById(product.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(this.productRepository.findById(product.getId())).isEmpty();
    }

    @Test
    void updateNotFoundProduct() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.productController.updateById(UUID.randomUUID(), ProductDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingProduct() {
        final String updateName = "update";
        Product product = this.productRepository.findAll().get(0);
        ProductDTO productDto = this.productMapper.productToProductDto(product);
        productDto.setId(null);
        productDto.setVersion(null);
        productDto.setProductName(updateName);
        ResponseEntity<?> responseEntity = this.productController.updateById(product.getId(), productDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Product findProduct = productRepository.findById(product.getId()).get();
        assertThat(findProduct.getProductName()).isEqualTo(updateName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewProduct() {
        ProductDTO productDto = ProductDTO.builder()
                .productName("Crank").productStyle(ProductStyle.GOSE).upc("12356222")
                .price(new BigDecimal("11.99")).quantityOnHand(392)
                .build();
        ResponseEntity<?> responseEntity = this.productController.handlePost(productDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Product product = this.productRepository.findById(savedUUID).get();
        assertThat(product).isNotNull();
    }

    @Test
    void testFindProductByIdNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.productController.getProductById(UUID.randomUUID());
        });
    }

    @Test
    void testFindProductById() {
        Product product = this.productRepository.findAll().get(0);
        ProductDTO productDTO = this.productController.getProductById(product.getId());
        assertThat(productDTO).isNotNull();
    }

    @Test
    void testFindAllProduct() {
        List<ProductDTO> product = this.productController.productList();
        assertThat(product).isNotNull().hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testFindAllProductNotData() {
        this.productRepository.deleteAll();
        List<ProductDTO> product = this.productController.productList();
        assertThat(product).isEmpty();
    }
}