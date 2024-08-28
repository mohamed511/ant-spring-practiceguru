package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.service.ProductService;
import com.ant.springpracticeguru.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    ProductServiceImpl productServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<ProductDTO> productArgumentCaptor;

    @BeforeEach
    void setUp() {
        productServiceImpl = new ProductServiceImpl();
    }

    @Test
    void testPatchProduct() throws Exception {
        ProductDTO productDTO = productServiceImpl.findAll().get(0);

        Map<String, Object> productMap = new HashMap<>();
        productMap.put("productName", "New Name");

        given(productService.patchProduct(any(),any())).willReturn(Optional.of(productDTO));
        mockMvc.perform(patch(ProductController.PRODUCT_PATH_ID, productDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productMap)))
                .andExpect(status().isNoContent());

        verify(productService).patchProduct(uuidArgumentCaptor.capture(), productArgumentCaptor.capture());

        assertThat(productDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(productMap.get("productName")).isEqualTo(productArgumentCaptor.getValue().getProductName());
    }

    @Test
    void testDeleteProduct() throws Exception {
        ProductDTO productDTO = productServiceImpl.findAll().get(0);
        given(this.productService.delete(any())).willReturn(true);
        mockMvc.perform(delete(ProductController.PRODUCT_PATH_ID, productDTO.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService).delete(uuidArgumentCaptor.capture());

        assertThat(productDTO.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO productDTO = productServiceImpl.findAll().get(0);
        given(productService.updateById(any(), any())).willReturn(Optional.of(productDTO));

        mockMvc.perform(put(ProductController.PRODUCT_PATH_ID, productDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isNoContent());

        verify(productService).updateById(any(UUID.class), any(ProductDTO.class));
    }

    @Test
    void testCreateNewProduct() throws Exception {
        ProductDTO productDTO = productServiceImpl.findAll().get(0);
        productDTO.setVersion(null);
        productDTO.setId(null);

        given(productService.add(any(ProductDTO.class))).willReturn(productServiceImpl.findAll().get(1));

        mockMvc.perform(post(ProductController.PRODUCT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListProducts() throws Exception {
        given(productService.findAll()).willReturn(productServiceImpl.findAll());

        mockMvc.perform(get(ProductController.PRODUCT_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getProductByIdNotFound() throws Exception {

        given(productService.findById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(ProductController.PRODUCT_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getProductById() throws Exception {
        ProductDTO testProduct = productServiceImpl.findAll().get(0);

        given(productService.findById(testProduct.getId())).willReturn(Optional.of(testProduct));

        mockMvc.perform(get(ProductController.PRODUCT_PATH_ID, testProduct.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testProduct.getId().toString())))
                .andExpect(jsonPath("$.productName", is(testProduct.getProductName())));
    }

    @Test
    void testCreateNewProductNullProductName() throws Exception {
        ProductDTO productDTO = ProductDTO.builder().build();

        given(productService.add(any(ProductDTO.class))).willReturn(productServiceImpl.findAll().get(1));

        MvcResult mvcResult =
        mockMvc.perform(post(ProductController.PRODUCT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(6)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void testUpdateProductNullProductName() throws Exception {
        ProductDTO productDTO = productServiceImpl.findAll().get(0);
        productDTO.setProductName("");
        given(productService.updateById(any(), any())).willReturn(Optional.of(productDTO));

        mockMvc.perform(put(ProductController.PRODUCT_PATH_ID, productDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()",is(1)))
                .andReturn();
    }
}