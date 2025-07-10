package com.example.project_01.Bai1_2_4_QuanLySach.controller;

import com.example.project_01.Bai1_2_4_QuanLySach.model.Book;
import com.example.project_01.Bai1_2_4_QuanLySach.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Book> getAllBooks(@RequestParam(required = false) String author,
                                  @RequestParam(required = false) Integer page,
                                  @RequestParam(required = false) Integer size) {
        return bookService.getAllBooks(author, page, size);
    }
    // 2. Lấy thông tin sách theo ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    // 3. Thêm sách mới
    @PostMapping
    public String addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return "Book added successfully!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    // 4. Cập nhật thông tin sách
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id,
                             @RequestBody Book updatedBook) {
        try {
            Optional<Book> updated = bookService.updateBook(id, updatedBook);
            if (updated.isPresent()) {
                return "Book updated successfully!";
            } else {
                return "Book not found!";
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
    // 5. Xóa sách theo ID
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return "Book deleted successfully!";
        } else {
            return "Book not found!";
        }
    }
    // 0. Fetch dữ liệu sách từ API ngoài
    @GetMapping("/fetch")
    public String fetchBooksFromApi() {
        bookService.fetchBooksFromApi();
        return "Fetched books from API and updated list!";
    }
    // 6. Tìm kiếm sách nâng cao
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }
}
