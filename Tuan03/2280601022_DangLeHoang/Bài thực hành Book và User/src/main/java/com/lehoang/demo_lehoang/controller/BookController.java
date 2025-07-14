package com.lehoang.demo_lehoang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lehoang.demo_lehoang.model.Book;
import com.lehoang.demo_lehoang.service.BookService;

import org.springframework.ui.Model;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    // Hiển thị form thêm sách
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    // Thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    // Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        bookService.getBookById(Math.toIntExact(id)).ifPresent(book -> model.addAttribute("book", book));
        return "edit-book";
    }

    // Cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/import")
    public String importGutendexBooks() {
        bookService.fetchAndAddBooksFromGutendex();
        return "redirect:/books";
    }

    @GetMapping("/api/books/search")
    @ResponseBody
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }

    @GetMapping("/api/books/page")
    @ResponseBody
    public Map<String, Object> getBooksByPage(@RequestParam int number, @RequestParam int size) {
        List<Book> pageBooks = bookService.getBooksByPage(number, size);
        int totalPages = bookService.getTotalPages(size);
        Map<String, Object> result = new HashMap<>();
        result.put("books", pageBooks);
        result.put("totalPages", totalPages);
        return result;
    }
}