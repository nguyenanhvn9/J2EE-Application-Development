package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/books"})

public class BookController {

   @Autowired
   private BookService bookService;

   public BookController() {
   }

   @GetMapping
   public String listBooks(Model model) {
      model.addAttribute("books", this.bookService.getAllBooks());
      return "books";
   }

   @GetMapping({"/add"})
   public String addBookForm(Model model) {
      model.addAttribute("book", new Book());
      return "add-book";
   }

   @PostMapping({"/add"})
   public String addBook(@ModelAttribute Book book) {
      this.bookService.addBook(book);
      return "redirect:/books";
   }

   @GetMapping({"/edit/{id}"})
   public String editBookForm(@PathVariable int id, Model model) {
      this.bookService.getBookById(id).ifPresent((book) -> {
         model.addAttribute("book", book);
      });
      return "edit-book";
   }

   @PostMapping({"/edit"})
   public String updateBook(@ModelAttribute Book book) {
      this.bookService.updateBook(book);
      return "redirect:/books";
   }

   @GetMapping({"/delete/{id}"})
   public String deleteBook(@PathVariable int id) {
      this.bookService.deleteBook(id);
      return "redirect:/books";
   }
}

