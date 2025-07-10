package com.example.QLySachJ2EE.controller;

import com.example.QLySachJ2EE.model.Book;
import com.example.QLySachJ2EE.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    // 1. Lấy danh sách tất cả sách (với phân trang và lọc)
    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return bookService.getAllBooks(author, page, size);
    }

    // 2. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 3. Thêm sách mới
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            boolean success = bookService.addBook(book);
            if (success) {
                return ResponseEntity.ok("Book added successfully!");
            } else {
                return ResponseEntity.badRequest().body("Failed to add book");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        Optional<Book> result = bookService.updateBook(id, updatedBook);
        if (result.isPresent()) {
            return ResponseEntity.ok("Book updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok("Book deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // 6. Tìm kiếm sách
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
} 