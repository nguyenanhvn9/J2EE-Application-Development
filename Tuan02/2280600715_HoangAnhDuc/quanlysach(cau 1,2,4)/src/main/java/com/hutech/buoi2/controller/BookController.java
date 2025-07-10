package com.hutech.buoi2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.buoi2.dto.BookDTO;
import com.hutech.buoi2.model.Book;
import com.hutech.buoi2.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // ✅ 1. Lấy danh sách có phân trang và lọc tác giả
    @GetMapping
    public List<Book> getFilteredBooks(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.getFilteredBooks(author, page, size);
    }

    // ✅ 2. Lấy sách theo ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    // ✅ 3. Thêm sách mới
    @PostMapping
    public String addBook(@RequestBody Book book) {
        try {
            boolean added = bookService.addBook(book);
            if (added) {
                return "Book added successfully!";
            }
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
        return "Book not added.";
    }

    // ✅ 4. Cập nhật sách
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Optional<Book> result = bookService.updateBook(id, updatedBook);
            return result.isPresent()
                    ? "Book updated successfully!"
                    : "Book with ID " + id + " not found.";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    // ✅ 5. Xóa sách
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        return deleted
                ? "Book deleted successfully!"
                : "Book with ID " + id + " not found.";
    }

    // ✅ 6. Tìm kiếm theo từ khóa
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }

    // ✅ 7. Lấy sách từ API công cộng
    @GetMapping("/public")
    public List<BookDTO> getPublicBooks() {
        return bookService.fetchBooksFromAPI();
    }
}
