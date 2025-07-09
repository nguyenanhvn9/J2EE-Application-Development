package com.hutech.cos141_demo.BaiTH124.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.cos141_demo.BaiTH124.dto.PaginatedResponse;
import com.hutech.cos141_demo.BaiTH124.model.Book;
import com.hutech.cos141_demo.BaiTH124.service.BookService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    private final BookService bookService;
    
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    /**
     * GET /api/books - Get all books with pagination and filtering
     * Parameters:
     * - page: Page number (default: 0)
     * - size: Page size (default: 10)
     * - author: Filter by author name (optional)
     */
    @GetMapping
    public ResponseEntity<PaginatedResponse<Book>> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String author) {
        
        PaginatedResponse<Book> response = bookService.getAllBooks(page, size, author);
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/books/{id} - Get book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * POST /api/books - Add a new book
     */
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        try {
            boolean success = bookService.addBook(book);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Book added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to add book");
            }
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * PUT /api/books/{id} - Update an existing book
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book) {
        try {
            Optional<Book> updatedBook = bookService.updateBook(id, book);
            if (updatedBook.isPresent()) {
                return ResponseEntity.ok(updatedBook.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * DELETE /api/books/{id} - Delete a book
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                return ResponseEntity.ok("Book deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * GET /api/books/search?keyword={keyword} - Search books by keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        try {
            List<Book> books = bookService.searchBooks(keyword);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * POST /api/books/fetch - Manually trigger fetch from API
     */
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchBooks() {
        try {
            bookService.fetch();
            return ResponseEntity.ok("Books fetched successfully from Gutenberg API");
        } catch (Exception e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
    
    /**
     * GET /api/books/status - Check if books are loaded
     */
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        boolean isLoaded = bookService.isLoaded();
        return ResponseEntity.ok("Books loaded: " + isLoaded);
    }
    
    /**
     * DELETE /api/books/clear - Clear loaded books
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearBooks() {
        bookService.clearBooks();
        return ResponseEntity.ok("Books cleared successfully");
    }
} 