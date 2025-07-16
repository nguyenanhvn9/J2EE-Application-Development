package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks(0, 10);
        model.addAttribute("books", books);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "books/detail";
        } else {
            model.addAttribute("error", "Book not found.");
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        try {
            bookService.addBook(book);
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "books/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "books/edit";
        } else {
            model.addAttribute("error", "Book not found.");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") Book updatedBook, Model model) {
        Optional<Book> updated = bookService.updateBook(id, updatedBook);
        if (updated.isPresent()) {
            return "redirect:/books";
        } else {
            model.addAttribute("error", "Book not found.");
            return "books/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id, Model model) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return "redirect:/books";
        } else {
            model.addAttribute("error", "Book not found.");
            return "error";
        }
    }
}
