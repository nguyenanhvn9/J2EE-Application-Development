package com.hutech.cos141_demo.BaiTH01_02_04.controller;

import com.hutech.cos141_demo.BaiTH01_02_04.model.Book;
import com.hutech.cos141_demo.BaiTH01_02_04.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookViewController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public String listBooks(Model model) {
        // Nếu danh sách rỗng, fetch lại từ API ngoài
        if (bookService.getBooks(null, null, null).isEmpty()) {
            bookService.fetch();
        }
        List<Book> allBooks = bookService.getBooks(null, null, null);
        List<Book> books = allBooks.size() > 5 ? allBooks.subList(0, 5) : allBooks;
        model.addAttribute("books", books);
        return "books";
    }


    @GetMapping("/add")
    public String showAddForm() {
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title, @RequestParam String authors) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthors(Arrays.asList(authors.split("\\s*,\\s*")));
        int newId = bookService.getBooks(null, null, null).stream().mapToInt(Book::getId).max().orElse(0) + 1;
        book.setId(newId);
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "edit-book";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable int id, @RequestParam String title, @RequestParam String authors) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthors(Arrays.asList(authors.split("\\s*,\\s*")));
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
} 