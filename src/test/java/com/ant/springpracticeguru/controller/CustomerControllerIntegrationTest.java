package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testListCustomer() {
        List<CustomerDTO> customerDTOS = this.customerController.customerList();
        // assertThat(customerDTOS.size()).isEqualTo(3);
        assertThat(customerDTOS).hasSize(3);
    }

//    @Rollback
//    @Transactional
    @Test
    void testEmptyListCustomer() {
        this.customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = this.customerController.customerList();
        // assertThat(customerDTOS.size()).isEqualTo(0);
        assertThat(customerDTOS).isEmpty();
    }

    @Test
    void testListCustomer2() {
        List<CustomerDTO> customerDTOS = this.customerController.customerList();
        assertThat(customerDTOS.size()).isEqualTo(3);
       // assertThat(customerDTOS).hasSize(3);
    }

}