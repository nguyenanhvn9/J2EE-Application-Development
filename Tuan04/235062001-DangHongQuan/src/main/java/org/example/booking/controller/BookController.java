package org.example.booking.controller;

import org.example.booking.model.BookCrud;
import org.example.booking.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", service.findAll());
        return "book-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookCrud());
        return "book-form";
    }

    @PostMapping
    public String createOrUpdateBook(@ModelAttribute BookCrud book) {
        service.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable int id, Model model) {
        BookCrud book = service.findById(id).orElseThrow();
        model.addAttribute("book", book);
        return "book-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/books";
    }
}
