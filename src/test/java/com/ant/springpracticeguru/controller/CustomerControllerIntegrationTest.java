package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.entities.Customer;
import com.ant.springpracticeguru.mapper.CustomerMapper;
import com.ant.springpracticeguru.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testPatchCustomerNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.customerController.patchCustomer(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testCustomerPatch() {
        String updatedName = "Update Name";
        Customer c = this.customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(c);
        customerDTO.setName(updatedName);
        ResponseEntity<?> responseEntity = customerController.patchCustomer(c.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Customer updatedCustomer = this.customerRepository.findById(c.getId()).get();
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo(updatedName);
    }

    @Test
    void testDeleteCustomerNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.customerController.delete(UUID.randomUUID());
        });
    }


    @Test
    void testDeleteCustomer() {
        Customer customer = this.customerRepository.findAll().get(0);
        ResponseEntity<?> responseEntity = this.customerController.delete(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(this.customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Test
    void testUpdateCustomer() {
        String updatedName = "Update Name";
        Customer c = this.customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(c);
        customerDTO.setName(updatedName);
        ResponseEntity<?> responseEntity = customerController.updateCustomerById(c.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Customer updatedCustomer = this.customerRepository.findById(c.getId()).get();
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo(updatedName);
    }

    @Test
    void testAddCustomer() {
        CustomerDTO customerDto = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .name("Customer 1")
                .version(1)
                .build();
        ResponseEntity<?> responseEntity = this.customerController.addCustomer(customerDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        System.out.println(responseEntity.getHeaders().getLocation());
        System.out.println(Arrays.toString(location));
        UUID uuid = UUID.fromString(location[4]);
        Customer save = customerRepository.findById(uuid).get();
        assertThat(save).isNotNull();
    }

    @Test
    void testFindCustomerByIdNotFound() {
        assertThrows(NotFoundCustomException.class, () -> {
            this.customerController.getCustomerById(UUID.randomUUID());
        });

    }

    @Test
    void testFindCustomerById() {
        Customer customer = this.customerRepository.findAll().get(0);
        CustomerDTO customerDTO = this.customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomer() {
        List<CustomerDTO> customerDTOS = this.customerController.customerList();
        // assertThat(customerDTOS.size()).isEqualTo(3);
        assertThat(customerDTOS).hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListCustomer() {
        this.customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = this.customerController.customerList();
        // assertThat(customerDTOS.size()).isEqualTo(0);
        assertThat(customerDTOS).isEmpty();
    }

}