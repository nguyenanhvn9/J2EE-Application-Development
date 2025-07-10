package com.hutech.cos141_demo.controller;

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

import com.hutech.cos141_demo.model.Book;
import com.hutech.cos141_demo.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
private BookService bookService;
// 1. Lấy danh sách tất cả sách
@GetMapping
public List<Book> getAllBooks(
    @RequestParam(required = false) String author,
    @RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size) {
    return bookService.getBooks(author, page, size);
}
// 2. Lấy thông tin sách theo ID
@GetMapping("/{id}")
public Book getBookById(@PathVariable int id) {
return bookService.getBookById(id);
}
//3. Thêm sách mới
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
public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
    try {
        Optional<Book> result = bookService.updateBook(id, updatedBook);
        if (result.isPresent()) {
            return "Book updated successfully!";
        } else {
            return "Book with ID " + id + " not found.";
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
        return "Book with ID " + id + " not found.";
    }
}

@GetMapping("/search")
public List<Book> searchBooks(@RequestParam String keyword) {
    return bookService.searchBooks(keyword);
}
}