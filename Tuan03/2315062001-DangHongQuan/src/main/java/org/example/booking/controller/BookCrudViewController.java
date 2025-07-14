package org.example.booking.controller;

import org.example.booking.model.BookCrud;
import org.example.booking.service.BookCrudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book-crud/view")
public class BookCrudViewController {

    private final BookCrudService bookService;

    public BookCrudViewController(BookCrudService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("newBook", new BookCrud());
        return "book-crud";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("newBook") BookCrud book) {
        bookService.create(book);
        return "redirect:/book-crud/view";
    }

    @PostMapping("/update")
    public String updateBook(@ModelAttribute BookCrud book) {
        bookService.update(book.getId(), book);
        return "redirect:/book-crud/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/book-crud/view";
    }

    @GetMapping("/fetch")
    public String fetchFromApi() {
        bookService.fetchFromGutendex();
        return "redirect:/book-crud/view";
    }
}
