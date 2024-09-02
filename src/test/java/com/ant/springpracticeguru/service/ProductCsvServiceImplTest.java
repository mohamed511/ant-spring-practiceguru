package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.ProductCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductCsvServiceImplTest {
    ProductCsvServiceImpl productCsvService = new ProductCsvServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/productData.csv");
        List<ProductCSVRecord> getProducts = productCsvService.convertCSV(file);
        System.out.println("===> size: "+getProducts.size());
//        assertThat(getProducts.size()).isGreaterThan(0);
        assertThat(getProducts).isNotEmpty();
    }
}