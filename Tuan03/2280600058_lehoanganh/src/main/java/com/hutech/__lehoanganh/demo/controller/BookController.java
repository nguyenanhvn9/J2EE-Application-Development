package com.hutech.__lehoanganh.demo.controller;

import com.hutech.__lehoanganh.demo.model.Book;
import com.hutech.__lehoanganh.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Integer id, @ModelAttribute Book book) {
        book.setId(id);
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
} 