package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.domain.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDTO, UUID> {
}
