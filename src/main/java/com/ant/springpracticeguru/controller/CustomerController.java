package com.ant.springpracticeguru.controller;

import com.ant.springpracticeguru.domain.CustomerDTO;
import com.ant.springpracticeguru.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
//@RequestMapping("/api/v1/customer")
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";
    private final CustomerService customerService;

    //@Cacheable("customers")
    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> customerList() {
        return this.customerService.findAll();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("Get Customer by Id - in controller");
        return this.customerService.findById(customerId).orElseThrow(NotFoundCustomException::new);
    }

    //@CacheEvict(value = "customers", allEntries = true)
    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity<?> addCustomer(@RequestBody CustomerDTO customer) {
        CustomerDTO savedCustomer = this.customerService.add(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("location", "/api/v1/customer/" + savedCustomer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<?> updateCustomerById(@PathVariable("customerId") UUID customerId,
                                                @RequestBody CustomerDTO customer) {
        this.customerService.updateById(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<?> delete(@PathVariable("customerId") UUID customerId) {
        this.customerService.delete(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity<?> patchCustomer(@PathVariable("customerId") UUID customerId,
                                           @RequestBody CustomerDTO customer) {
        this.customerService.patchCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @ExceptionHandler
//    public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException ex) {
//        CustomerErrorResponse errorResponse = new CustomerErrorResponse();
//        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setTimeStamp(System.currentTimeMillis());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<CustomerErrorResponse> handleException(Exception ex) {
//        CustomerErrorResponse errorResponse = new CustomerErrorResponse();
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setTimeStamp(System.currentTimeMillis());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
}
