package com.example.BookService.Controller;

import com.example.BookService.DTO.BookResponse;
import com.example.BookService.Exception.ResourceNotFoundException;
import com.example.BookService.Service.BookService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public List<BookResponse> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.getBooks(author, page, size);
    }

    @PostMapping("/fetch")
    public String fetchBooks() {
        bookService.fetchBooksFromApi();
        return "Books fetched successfully!";
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookResponse book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.ok("Book added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            BookResponse book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id, @RequestBody BookResponse book) {
        try {
            Optional<BookResponse> updated = bookService.updateBook(id, book);
            return updated.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted
                ? ResponseEntity.ok("Book deleted successfully.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
    }


    @GetMapping("/search")
    public List<BookResponse> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
}
