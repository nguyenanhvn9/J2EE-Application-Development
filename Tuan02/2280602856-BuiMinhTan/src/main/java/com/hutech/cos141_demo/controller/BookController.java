package com.hutech.cos141_demo.controller;

import com.hutech.cos141_demo.model.Book;
import com.hutech.cos141_demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService service;

    // 🔥 Đã chỉnh: getBooks() hỗ trợ lọc theo author + phân trang
    @GetMapping
    public List<Book> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getBooks(author, page, size);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public String addBook(@RequestBody Book book) {
        service.addBook(book);
        return "Book added successfully.";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book book) {
        service.updateBook(id, book);
        return "Book updated.";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        service.deleteBook(id);
        return "Book deleted.";
    }

    @GetMapping("/search")
    public List<Book> search(@RequestParam String keyword) {
        return service.searchBooks(keyword);
    }
}
