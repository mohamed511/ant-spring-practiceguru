package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.ProductDTO;
import com.ant.springpracticeguru.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
//@RequestMapping("/api/v1/product")
public class ProductController {
    public static final String PRODUCT_PATH = "/api/v1/product";
    public static final String PRODUCT_PATH_ID = PRODUCT_PATH + "/{productId}";
    private final ProductService productService;

    @GetMapping(PRODUCT_PATH)
    public List<ProductDTO> productList() {
        return this.productService.findAll();
    }

    @GetMapping(PRODUCT_PATH_ID)
    public ProductDTO getProductById(@PathVariable("productId") UUID productId) {
        log.debug("Get Product by Id - in controller");
        return this.productService.findById(productId).orElseThrow(NotFoundCustomException::new);
    }

    @PostMapping(PRODUCT_PATH)
    public ResponseEntity handlePost(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.add(productDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", PRODUCT_PATH + "/" + savedProduct.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(PRODUCT_PATH_ID)
    public ResponseEntity updateById(@PathVariable("productId") UUID productId, @RequestBody ProductDTO productDTO) {
        productService.updateById(productId, productDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(PRODUCT_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("productId") UUID productId) {
        productService.delete(productId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(PRODUCT_PATH_ID)
    public ResponseEntity updateProductPatchById(@PathVariable("productId") UUID productId, @RequestBody ProductDTO productDTO) {
        productService.patchProduct(productId, productDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
