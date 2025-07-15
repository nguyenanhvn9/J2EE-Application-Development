package com.example.QLySach_J2EE.controller;

import com.example.QLySach_J2EE.model.Book;
import com.example.QLySach_J2EE.service.BookService;
import com.example.QLySach_J2EE.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books") // Base URL cho tất cả API trongcontroller
public class BookController {
    @Autowired
    private BookService bookService;

    // 1. Lấy danh sách tất cả sách
    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false) String author,
                                  @RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer size) {
        return bookService.getAllBooks(author, page, size);
    }
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
    // 2. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    // 3. Thêm sách mới
    @PostMapping
    public boolean addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }
    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public Optional<Book> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }
    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable int id) {
        return bookService.deleteBook(id);
    }
}
