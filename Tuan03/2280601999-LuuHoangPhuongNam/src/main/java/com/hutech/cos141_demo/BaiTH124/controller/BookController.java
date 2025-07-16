package com.hutech.cos141_demo.BaiTH124.controller;

import com.hutech.cos141_demo.BaiTH124.model.Book;
import com.hutech.cos141_demo.BaiTH124.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
@Autowired
private BookService bookService;
@GetMapping
public String listBooks(Model model) {
model.addAttribute("books", bookService.getAllBooks());
return "books";
}
// Hiển thị form thêm sách
@GetMapping("/add")
public String addBookForm(Model model) {
model.addAttribute("book", new Book());
return "add-book";
}
// Thêm sách mới
@PostMapping("/add")
public String addBook(@ModelAttribute Book book) {
bookService.addBook(book);
return "redirect:/books";
}
// Hiển thị form sửa sách
@GetMapping("/edit/{id}")
public String editBookForm(@PathVariable Long id, Model model) {
bookService.getBookById(id).ifPresent(book -> model.addAttribute("book",
book));
return "edit-book";
}
// Cập nhật sách
@PostMapping("/edit")
public String updateBook(@ModelAttribute Book book) {
bookService.updateBook(book);
return "redirect:/books";
}
// Xóa sách
@GetMapping("/delete/{id}")
public String deleteBook(@PathVariable Long id) {
bookService.deleteBook(id);
return "redirect:/books";
}
}