package com.example.BaitapJ2EE.QuanLySach.service;

import com.example.BaitapJ2EE.QuanLySach.model.Book;
import com.example.BaitapJ2EE.QuanLySach.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public Book getBookById(Long id) { return bookRepository.findById(id); }
    public void saveBook(Book book) { bookRepository.save(book); }
    public void deleteBook(Long id) { bookRepository.deleteById(id); }
} 