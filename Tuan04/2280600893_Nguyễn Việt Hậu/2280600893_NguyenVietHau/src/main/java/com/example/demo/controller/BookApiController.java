package com.example.demo.controller;

import com.example.demo.model.Books;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    @Autowired
    private BookService bookService;

    // Lấy tất cả sách
    @GetMapping
    public ResponseEntity<List<Books>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Lấy sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable UUID id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tìm sách theo tác giả
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Books>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    // Thêm sách mới
    @PostMapping
    public ResponseEntity<Books> createBook(@RequestBody Books book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    // Cập nhật sách
    @PutMapping("/{id}")
    public ResponseEntity<Books> updateBook(@PathVariable UUID id, @RequestBody Books book) {
        if (!bookService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    // Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (!bookService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
