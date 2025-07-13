package com.__TranThanhDat.__TranThanhDat.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.__TranThanhDat.__TranThanhDat.models.Book;
import com.__TranThanhDat.__TranThanhDat.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
        public List<Book> getBooks(
                @RequestParam(required = false) String author,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size) {
            return service.getBooks(author, page, size);
        }


    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        return service.addBook(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> result = service.updateBook(id, book);
        return result.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean deleted = service.deleteBook(id);
        return deleted ? ResponseEntity.ok("Deleted") : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return service.searchBooks(keyword);
    }
}

