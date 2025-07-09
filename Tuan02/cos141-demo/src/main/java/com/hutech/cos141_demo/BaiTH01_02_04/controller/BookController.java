package com.hutech.cos141_demo.BaiTH01_02_04.controller;

import com.hutech.cos141_demo.model.Book;
import com.hutech.cos141_demo.service.BookService;
import com.hutech.cos141_demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String author,
                               @RequestParam(required = false) Integer page,
                               @RequestParam(required = false) Integer size) {
        if (bookService.getBooks(null, null, null).isEmpty()) {
            bookService.fetch();
        }
        return bookService.getBooks(author, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping
    public Optional<Book> updateBook(@RequestBody Book book) {
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