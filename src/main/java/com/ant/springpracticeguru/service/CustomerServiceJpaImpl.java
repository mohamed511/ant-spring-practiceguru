package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.mapper.CustomerMapper;
import com.ant.springpracticeguru.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
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
        return this.customerMapper.customerToCustomerDto(
                this.customerRepository.save(
                        this.customerMapper.customerDtoToCustomer(customer)
                )
        );
    }

    @Override
    public Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        this.customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(this.customerRepository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }

    @Override
    public Boolean delete(UUID customerId) {
        if (this.customerRepository.existsById(customerId)) {
            this.customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        this.customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())) {
                foundCustomer.setName(customer.getName());
            }
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(this.customerRepository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }
}
