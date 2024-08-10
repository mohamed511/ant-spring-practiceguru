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
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJpaImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAll() {
        return this.customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        return Optional.ofNullable(this.customerMapper.customerToCustomerDto(this.customerRepository.findById(id)
                 .orElse(null)));
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
