package com.ant.springpracticeguru.boostrap;

import com.ant.springpracticeguru.domain.ProductCSVRecord;
import com.ant.springpracticeguru.domain.ProductStyle;
import com.ant.springpracticeguru.entities.Customer;
import com.ant.springpracticeguru.entities.Product;
import com.ant.springpracticeguru.repository.CustomerRepository;
import com.ant.springpracticeguru.repository.ProductRepository;
import com.ant.springpracticeguru.service.ProductCsvService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class BootstrapDataCustomerProduct implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductCsvService productCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
        loadProductData();
        loadProductDataFromCsv();
    }

    private void loadProductDataFromCsv() throws FileNotFoundException {
        if (productRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/productData.csv");
            List<ProductCSVRecord> productCSV = productCsvService.convertCSV(file);
            productCSV.forEach(p -> {
                ProductStyle productStyle = switch (p.getStyle()) {
                    case "American Pale Lager" -> ProductStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            ProductStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> ProductStyle.IPA;
                    case "American Porter" -> ProductStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> ProductStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> ProductStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> ProductStyle.WHEAT;
                    case "English Pale Ale" -> ProductStyle.PALE_ALE;
                    default -> ProductStyle.PILSNER;
                };

                productRepository.save(Product.builder()
                        .productName(StringUtils.abbreviate(p.getProductName(),50))
                        .productStyle(productStyle)
                        .upc(p.getRow().toString())
                        .price(BigDecimal.TEN)
                        .quantityOnHand(p.getCount())
                        .build());
            });
        }
    }

    private void loadProductData() {
        if (productRepository.count() == 0) {
            Product product1 = Product.builder()
                    .productName("Galaxy Cat")
                    .productStyle(ProductStyle.ALE)
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .build();

            Product product2 = Product.builder()
                    .productName("Crank")
                    .productStyle(ProductStyle.GOSE)
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .build();

            Product product3 = Product.builder()
                    .productName("Sunshine City")
                    .productStyle(ProductStyle.LAGER)
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .build();

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .name("Customer 1")
                    .build();

            Customer customer2 = Customer.builder()
                    .name("Customer 2")
                    .build();

            Customer customer3 = Customer.builder()
                    .name("Customer 3")
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
