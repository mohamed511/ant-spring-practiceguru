package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAll();

    Customer findById(UUID id);

    Customer add(Customer customer);

    void updateById(UUID customerId, Customer customer);

    void delete(UUID customerId);

    void patchCustomer(UUID customerId, Customer customer);
}
