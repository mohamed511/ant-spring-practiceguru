package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.controller.NotFoundCustomException;
import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private Map<UUID, CustomerDTO> customers;

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 2")
                .version(1)
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 3")
                .version(1)
                .build();

        customers = new HashMap<>();
        customers.put(customer1.getId(), customer1);
        customers.put(customer2.getId(), customer2);
        customers.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return new ArrayList<>(this.customers.values());
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        log.debug("Get product by Id - in service. Id: {}", id.toString());
        return Optional.of(this.customers.get(id));
    }

    @Override
    public CustomerDTO add(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name(customer.getName())
                .version(customer.getVersion())
                .build();
        this.customers.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer) {
        Optional<CustomerDTO> current = findById(customerId);
        if (current.isPresent()) {
            CustomerDTO c = current.get();
            c.setName(customer.getName());
            c.setVersion(customer.getVersion());
            c.setUpdateDate(LocalDateTime.now());
            this.customers.put(c.getId(), c);
            return Optional.of(this.customers.get(c.getId()));
        } else {
            throw new NotFoundCustomException("Item not exist to make update");
        }
    }

    @Override
    public Boolean delete(UUID customerId) {
        this.customers.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomer(UUID customerId, CustomerDTO customer) {
        Optional<CustomerDTO> c = findById(customerId);
        if (c.isEmpty()) {
            throw new CustomerNotFoundException("Customer not exist to make patch update");
        }
        CustomerDTO current = c.get();
        if (StringUtils.hasText(customer.getName())) {
            current.setName(customer.getName());
        }
        if (customer.getVersion() != null) {
            current.setVersion(customer.getVersion());
        }
        this.customers.put(current.getId(), current);
        return Optional.ofNullable(this.customers.get(current.getId()));
    }

    @Scheduled(fixedRate = 6000)
    public void runEveryMinute() {
        System.out.println("========> run every minute schedule");
    }

}
