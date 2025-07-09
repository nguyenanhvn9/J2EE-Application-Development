package com.lehoang.demo_lehoang.controller;

import com.lehoang.demo_lehoang.model.Book;
import com.lehoang.demo_lehoang.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books") // Base URL cho tất cả API trong controller
public class BookController {
    @Autowired
    private BookService bookService;
    // 1. Lấy danh sách tất cả sách
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    // 2. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    // 3. Thêm sách mới
    @PostMapping
    public String addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return "Book added successfully!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id,
                             @RequestBody Book updatedBook) {
        try {
            Optional<Book> result = bookService.updateBook(id, updatedBook);
            if (result.isPresent()) {
                return "Book updated successfully!";
            } else {
                return "Book not found!";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return "Book deleted successfully!";
        } else {
            return "Book not found!";
        }
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
}