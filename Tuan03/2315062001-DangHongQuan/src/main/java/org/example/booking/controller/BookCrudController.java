package org.example.booking.controller;

import org.example.booking.model.BookCrud;
import org.example.booking.service.BookCrudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-crud")
public class BookCrudController {

    private final BookCrudService bookService;

    public BookCrudController(BookCrudService bookService) {
        bookService.fetchFromGutendex();
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookCrud> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public BookCrud getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @PostMapping
    public BookCrud create(@RequestBody BookCrud book) {
        return bookService.create(book);
    }

    @PutMapping("/{id}")
    public BookCrud update(@PathVariable Long id, @RequestBody BookCrud book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    // 🆕 Gọi API này để fetch 5 sách từ Gutendex
    @GetMapping("/fetch")
    public void fetchFromGutendex() {
        bookService.fetchFromGutendex();
    }
}
