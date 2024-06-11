package com.ant.springpracticeguru.repository;

import com.ant.springpracticeguru.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll(Pageable pageable);
}
