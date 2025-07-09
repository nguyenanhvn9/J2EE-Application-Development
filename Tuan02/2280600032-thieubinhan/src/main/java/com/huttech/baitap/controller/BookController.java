package com.huttech.baitap.controller;
import com.huttech.baitap.model.Book;
import com.huttech.baitap.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Gọi fetch từ API public
    @GetMapping("/fetch")
    public String fetchBooks() {
        bookService.fetchBooksFromGutenberg();
        return "Books fetched successfully from Gutenberg API!";
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks(author, page, size);
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}
