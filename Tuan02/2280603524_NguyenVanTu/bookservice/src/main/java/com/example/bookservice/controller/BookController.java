package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET /api/books - Lấy tất cả sách với phân trang và lọc
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<BookDTO> books = bookService.getAllBooks(author, page, size);
        return ResponseEntity.ok(books);
    }

    // GET /api/books/{id} - Lấy sách theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // POST /api/books - Tạo sách mới
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    // PUT /api/books/{id} - Cập nhật sách
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, 
                                            @Valid @RequestBody BookDTO bookDTO) {
        Optional<BookDTO> updatedBook = bookService.updateBook(id, bookDTO);
        return updatedBook.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/books/{id} - Xóa sách
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted ? ResponseEntity.noContent().build() 
                       : ResponseEntity.notFound().build();
    }

    // GET /api/books/search?title=xxx&author=xxx&keyword=xxx - Tìm kiếm nâng cao
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String keyword) {
        
        List<BookDTO> books;
        if (keyword != null && !keyword.trim().isEmpty()) {
            books = bookService.searchBooks(keyword);
        } else if (title != null && !title.trim().isEmpty()) {
            books = bookService.searchByTitle(title);
        } else if (author != null && !author.trim().isEmpty()) {
            books = bookService.searchByAuthor(author);
        } else {
            books = bookService.getAllBooks();
        }
        
        return ResponseEntity.ok(books);
    }

    // GET /api/books/gutendex - Endpoint để trigger fetch từ Gutendex
    @GetMapping("/gutendex")
    public ResponseEntity<Map<String, String>> fetchFromGutendex() {
        // Trigger lazy loading by calling getAllBooks
        bookService.getAllBooks();
        return ResponseEntity.ok(Map.of("message", "Books fetched from Gutendex API successfully"));
    }

    // GET /api/books/count - Đếm số lượng sách
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getBookCount() {
        long count = bookService.countBooks();
        return ResponseEntity.ok(Map.of("count", count));
    }
}
