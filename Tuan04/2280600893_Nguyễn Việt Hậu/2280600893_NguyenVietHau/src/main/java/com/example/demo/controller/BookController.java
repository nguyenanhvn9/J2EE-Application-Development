package com.example.demo.controller;

import com.example.demo.model.Books;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Hiển thị danh sách sách
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list"; // trả về view books/list.html
    }

    // Hiển thị form thêm sách mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Books());
        return "books/create"; // trả về view books/create.html
    }

    // Xử lý thêm sách
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("book") Books book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Hiển thị form cập nhật sách
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        Books book = bookService.getBookById(id).orElse(null);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/edit"; // trả về view books/edit.html
    }

    // Xử lý cập nhật sách
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable UUID id, @ModelAttribute("book") Books book) {
        book.setId(id);
        bookService.saveBook(book);
        return "redirect:/books";
    }

    // Xử lý xoá sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
