package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
