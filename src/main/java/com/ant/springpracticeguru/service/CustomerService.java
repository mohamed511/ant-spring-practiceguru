package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> findAll();

    Optional<CustomerDTO> findById(UUID id);

    CustomerDTO add(CustomerDTO customer);

    void updateById(UUID customerId, CustomerDTO customer);

    void delete(UUID customerId);

    void patchCustomer(UUID customerId, CustomerDTO customer);
}
