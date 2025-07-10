package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // 1. Lấy danh sách tất cả sách
    @GetMapping
    public List<Book> getAllBooks(
        @RequestParam(required = false) String author,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks(author, page, size);
    }

    // 2. Gọi API công khai để fetch dữ liệu sách
    @GetMapping("/fetch")
    public String fetchBooksFromAPI() {
        bookService.fetchBooksFromAPI();
        return "Books fetched successfully from external API!";
    }

    // 3. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    // 4. Thêm sách mới
    @PostMapping
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully!";
    }

    // 5. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        boolean success = bookService.updateBook(id, updatedBook);
        return success ? "Book updated successfully!" : "Book not found!";
    }

    // 6. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        boolean success = bookService.deleteBook(id);
        return success ? "Book deleted successfully!" : "Book not found!";
    }
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }

}
