package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
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

    // 1. Lấy danh sách tất cả sách
    @GetMapping
    public List<Book> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        bookService.fetchBooks(); // nạp dữ liệu 1 lần nếu rỗng
        return bookService.getAllBooks(author, page, size);
    }

    // 2. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
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
            bookService.addBook(book);
            return ResponseEntity.ok("Book added successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error occurred.");
        }
    }

    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Optional<Book> updated = bookService.updateBook(id, updatedBook);
            return updated.isPresent()
                    ? ResponseEntity.ok(updated.get())
                    : ResponseEntity.status(404).body("Book not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted
                ? ResponseEntity.ok("Book deleted successfully!")
                : ResponseEntity.status(404).body("Book not found.");
    }

    // 6. Tìm kiếm sách theo từ khóa (title hoặc author)
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> result = bookService.searchBooks(keyword);
        return ResponseEntity.ok(result);
    }
}
