package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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
            model.addAttribute("message", "Book List");
            model.addAttribute("books", bookService.getAllBooks(null, 0, Integer.MAX_VALUE));
            return "books";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load books: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("message", "Add New Book");
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping
    public String saveBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Please correct the form errors.");
            return "add-book";
        }
        try {
            bookService.addBook(book);
            model.addAttribute("message", "Book added successfully!");
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "add-book";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        try {
            Book book = bookService.getBookById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
            model.addAttribute("message", "Edit Book");
            model.addAttribute("book", book);
            return "edit-book";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Please correct the form errors.");
            return "edit-book";
        }
        try {
            bookService.updateBook(id, book);
            model.addAttribute("message", "Book updated successfully!");
            return "redirect:/books";
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "edit-book";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model) {
        try {
            bookService.deleteBook(id);
            model.addAttribute("message", "Book deleted successfully!");
            return "redirect:/books";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}