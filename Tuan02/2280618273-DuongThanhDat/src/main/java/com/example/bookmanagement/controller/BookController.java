package com.example.bookmanagement.controller;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all books with pagination and filtering
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String author) {

        List<Book> books;

        if (author != null && !author.trim().isEmpty()) {
            books = bookService.searchByAuthor(author);

            // Apply pagination to filtered results
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, books.size());

            if (startIndex >= books.size()) {
                books = List.of();
            } else {
                books = books.subList(startIndex, endIndex);
            }
        } else {
            books = bookService.getBooksPaginated(page, size);
        }

        return ResponseEntity.ok(books);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Search books by keyword (title or author)
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        // Search in both title and author
        List<Book> titleResults = bookService.searchByTitle(keyword);
        List<Book> authorResults = bookService.searchByAuthor(keyword);

        // Combine results and remove duplicates
        List<Book> combinedResults = new java.util.ArrayList<>(titleResults);
        for (Book book : authorResults) {
            if (!combinedResults.contains(book)) {
                combinedResults.add(book);
            }
        }

        return ResponseEntity.ok(combinedResults);
    }

    // Search books by title
    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchByTitle(title);
        return ResponseEntity.ok(books);
    }

    // Search books by author
    @GetMapping("/search/author")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@RequestParam String author) {
        List<Book> books = bookService.searchByAuthor(author);
        return ResponseEntity.ok(books);
    }

    // Search books by subject
    @GetMapping("/search/subject")
    public ResponseEntity<List<Book>> searchBooksBySubject(@RequestParam String subject) {
        List<Book> books = bookService.searchBySubject(subject);
        return ResponseEntity.ok(books);
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        validateBook(book);
        Book createdBook = bookService.addBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        validateBook(book);
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Get total count of books
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalBooksCount() {
        long count = bookService.getTotalBooksCount();
        return ResponseEntity.ok(count);
    }

    // Validate book data
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }
    }
}
