package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.Customer;
import com.ant.springpracticeguru.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listBeers() {
        return this.customerService.findAll();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getProductById(@PathVariable("customerId") UUID customerId) {
        log.debug("Get Product by Id - in controller");
        return this.customerService.findById(customerId);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = this.customerService.add(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/api/v1/customer/" + savedCustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<?> updateCustomerById(@PathVariable("customerId") UUID customerId,
                                                @RequestBody Customer customer) {
        this.customerService.updateById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
