package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAll();

    Optional<Customer> findById(UUID id);

    Customer add(Customer customer);

    void updateById(UUID customerId, Customer customer);

    void delete(UUID customerId);

    void patchCustomer(UUID customerId, Customer customer);
}
