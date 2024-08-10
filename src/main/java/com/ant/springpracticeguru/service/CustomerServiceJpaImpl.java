package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.entities.Customer;
import com.ant.springpracticeguru.mapper.CustomerMapper;
import com.ant.springpracticeguru.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJpaImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAll() {
        return this.customerMapper.customerToCustomerDto(this.customerRepository.findAll());
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        return Optional.of(this.customerMapper.customerToCustomerDto(this.customerRepository.findById(id).get()));
    }

    @Override
    public CustomerDTO add(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void delete(UUID customerId) {

    }

    @Override
    public void patchCustomer(UUID customerId, CustomerDTO customer) {

    }
}
