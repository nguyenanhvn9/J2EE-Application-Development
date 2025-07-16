package org.example.booking.service;

import org.example.booking.Response.BookRepository;
import org.example.booking.model.BookCrud;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookCrud> findAll() {
        return repository.findAll();
    }

    public Optional<BookCrud> findById(int id) {
        return repository.findById(id);
    }

    public BookCrud save(BookCrud book) {
        return repository.save(book);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
