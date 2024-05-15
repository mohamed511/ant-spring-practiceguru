package com.ant.springpracticeguru.boostrap;

import com.ant.springpracticeguru.domain.Author;
import com.ant.springpracticeguru.domain.Book;
import com.ant.springpracticeguru.domain.Publisher;
import com.ant.springpracticeguru.repository.AuthorRepository;
import com.ant.springpracticeguru.repository.BookRepository;
import com.ant.springpracticeguru.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Author
        Author ali = new Author("Ali", "Ahmed");
        Author noah = new Author("noah", "mohamed");
        // Books
        Book clean = new Book("Clean Code", "123");
        Book ddd = new Book("Domain Driven Design", "789");
        // add publisher
        Publisher aliPublisher = new Publisher("Ali publisher", "Egypt", "cairo", "shybra" ,"123");
        // save author
        Author aliSaved = this.authorRepository.save(ali);
        Author noahSaved = this.authorRepository.save(noah);
        // save books
        Book cleanSave = this.bookRepository.save(clean);
        Book dddSave = this.bookRepository.save(ddd);
        // save publisher and add book to publisher
        Publisher savedPublisher = this.publisherRepository.save(aliPublisher);
        dddSave.setPublisher(savedPublisher);
        cleanSave.setPublisher(savedPublisher);
        this.bookRepository.save(dddSave);
        this.bookRepository.save(cleanSave);
        // add books to authors
        noahSaved.getBooks().add(dddSave);
        aliSaved.getBooks().add(cleanSave);
        this.authorRepository.save(noahSaved);
        this.authorRepository.save(aliSaved);

        System.out.println("===> Bootstrap");
        System.out.println(" Author " + authorRepository.count());
        System.out.println(" Book " + bookRepository.count());
        System.out.println(" publisher " + publisherRepository.count());

    }
}
