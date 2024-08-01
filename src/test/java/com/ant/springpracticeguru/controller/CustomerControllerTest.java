package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.Customer;
import com.ant.springpracticeguru.service.CustomerService;
import com.ant.springpracticeguru.service.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void patchCustomerTest() throws Exception {
        Customer customer = customerServiceImpl.findAll().get(0);

        Map<String, Object> map = new HashMap<>();
        //attribute name and the value
        map.put("name", "new name");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH+"/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map))
        ).andExpect(status().isNoContent());

        verify(customerService).patchCustomer(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
        assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(map.get("name"));
    }

    @Test
    void deleteTest() throws Exception {
        Customer customer = customerServiceImpl.findAll().get(0);
        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH+"/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
        verify(customerService).delete(uuidArgumentCaptor.capture());
        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void updateCustomerByIdTest() throws Exception {
        Customer customer = customerServiceImpl.findAll().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH+"/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))
        ).andExpect(status().isNoContent());

        verify(customerService).updateById(any(UUID.class), any(Customer.class));
    }

    @Test
    void addCustomerTest() throws Exception {
        Customer customer = customerServiceImpl.findAll().get(0);
        customer.setId(null);
        customer.setVersion(null);
        given(customerService.add(any(Customer.class))).willReturn(customerServiceImpl.findAll().get(1));
        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer))
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void getCustomersList() throws Exception {
        given(customerService.findAll()).willReturn(customerServiceImpl.findAll());
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = this.customerServiceImpl.findAll().get(0);

        given(customerService.findById(any(UUID.class))).willReturn(customer);

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH+"/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customer.getId().toString())))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }


}