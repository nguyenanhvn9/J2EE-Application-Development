package leducanh.name.vn.leducanh_2280600056.controller;

import leducanh.name.vn.leducanh_2280600056.model.Book;
import leducanh.name.vn.leducanh_2280600056.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
        this.bookService.fetchBooksFromGutendex();
    }

    /**
     * Get all books
     * GET /api/book
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get the number of books currently available
     * GET /api/book/count
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getBookCount() {
        int count = bookService.getBookCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.ok(newBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable String id, @RequestBody Book updatedBook) {
        // throw error if book or title or authors is empty
        if (updatedBook == null ||
                updatedBook.getTitle().trim().isEmpty() ||
                updatedBook.getAuthors().isEmpty())
            throw new IllegalArgumentException("Book hoặc title hoặc authors là bắt buộc");
        Book book = bookService.updateBookById(id, updatedBook);
        return ResponseEntity.ok(book);
    }

    /**
     * Delete all books in the list
     * DELETE /api/book
     */
    @DeleteMapping
    public ResponseEntity<String> clearAllBooks() {
        bookService.clearAllBooks();
        return ResponseEntity.ok("Đã xóa tất cả sách trong danh sách");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable String id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Đã xóa sách có ID: " + id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("keyword") String keyword) {
        if (keyword.trim().isEmpty())
            throw new IllegalArgumentException("Keyword là bắt buộc");
        List<Book> results = bookService.searchBooks(keyword);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/getPage")
    public ResponseEntity<List<Book>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Book> books = bookService.getBooks(author, page, size);
        return ResponseEntity.ok(books);
    }
}