package com.ant.springpracticeguru.mapper;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.entities.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    List<Customer> customerDtoToCustomer(List<CustomerDTO> dto);

    CustomerDTO customerToCustomerDto(Customer customer);

    List<CustomerDTO> customerToCustomerDto(List<Customer> customer);
}