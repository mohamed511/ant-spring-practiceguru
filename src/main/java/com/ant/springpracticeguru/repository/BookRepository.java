package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
