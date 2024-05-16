package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Book;

public interface BookService {
    Iterable<Book> findAll();
}
