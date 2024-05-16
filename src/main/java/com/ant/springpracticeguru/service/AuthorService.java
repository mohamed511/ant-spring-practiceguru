package com.ant.springpracticeguru.service;

import com.ant.springpracticeguru.domain.Author;

public interface AuthorService {
    Iterable<Author> findAll();
}
