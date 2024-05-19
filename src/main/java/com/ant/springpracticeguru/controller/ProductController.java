package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.Product;
import com.ant.springpracticeguru.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Product> listBeers() {
        return this.productService.findAll();
    }

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("productId") UUID productId) {
        log.debug("Get Product by Id - in controller");
        return this.productService.getProductById(productId);
    }
}
