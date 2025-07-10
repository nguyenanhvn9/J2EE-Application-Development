package com.example.BaitapJ2EE.QuanLySach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String author,
                               @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size) {
        if (bookService.getBooks().isEmpty()) {
            bookService.fetchBooks();
        }
        return bookService.getBooks(author, page, size);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/{id}")
    public Optional<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setId(id);
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
} 