package com.example.QLS.controller;

import com.example.QLS.model.Book;
import com.example.QLS.service.BookService;
import com.example.QLS.service.BookViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BookViewController {
    @Autowired
    private BookViewService bookViewService;
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookViewService.getAllBooks());
        return "books";
    }
    // Hiển thị form thêm sách
    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-books";
    }
    // Thêm sách mới
    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookViewService.addBook(book);
        return "redirect:/books";
    }
    // Hiển thị form sửa sách
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        bookViewService.getBookById(id).ifPresent(book -> model.addAttribute("book",
                book));
        return "edit-books";
    }
    // Cập nhật sách
    @PostMapping("/edit")
    public String updateBook(@ModelAttribute Book book) {
        bookViewService.updateBook(book);
        return "redirect:/books";
    }
    // Xóa sách
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookViewService.deleteBook(id);
        return "redirect:/books";
    }
}