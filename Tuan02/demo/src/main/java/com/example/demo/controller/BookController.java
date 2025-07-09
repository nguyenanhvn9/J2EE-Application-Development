package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import java.util.List;
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
        } catch (Exception e) {
            return "Book added error!";
        }
    }
    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id,
                             @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "Book updated successfully!";
    }
    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }
}