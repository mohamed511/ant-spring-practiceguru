package com.ant.springpracticeguru.mapper;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.entities.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}