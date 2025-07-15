package com.example.QLySach_J2EE.controller;

import com.example.QLySach_J2EE.model.Book;
import com.example.QLySach_J2EE.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookWebController {
    private final BookService bookService;

    public BookWebController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        try {
            System.out.println("Loading books page...");
            model.addAttribute("books", bookService.getAllBooks());
            System.out.println("Books page loaded successfully");
            return "books";
        } catch (Exception e) {
            System.err.println("Error loading books page: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải danh sách sách: " + e.getMessage());
            return "error";
        }
    }

    // Hiển thị form thêm sách
    @GetMapping("/add")
    public String addBookForm(Model model) {
        try {
            model.addAttribute("book", new Book());
            return "add-book";
        } catch (Exception e) {
            System.err.println("Error loading add book form: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải form thêm sách: " + e.getMessage());
            return "error";
        }
    }

    // Thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        try {
            bookService.addBook(book);
            return "redirect:/books";
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/books?error=add";
        }
    }

    // Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        try {
            bookService.getBookById(id).ifPresent(book -> model.addAttribute("book", book));
            return "edit-book";
        } catch (Exception e) {
            System.err.println("Error loading edit book form: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi tải form sửa sách: " + e.getMessage());
            return "error";
        }
    }

    // Cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        try {
            bookService.updateBook(book);
            return "redirect:/books";
        } catch (Exception e) {
            System.err.println("Error updating book: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/books?error=update";
        }
    }

    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return "redirect:/books";
        } catch (Exception e) {
            System.err.println("Error deleting book: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/books?error=delete";
        }
    }
} 