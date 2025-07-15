package com.example.demo;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;

@Controller
public class IndexController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    // Thêm sách
    @PostMapping("/books/add")
    public String addBook(@RequestParam int id, @RequestParam String title, @RequestParam String authors, @RequestParam int downloadCount) {
        Book book = new Book(id, title, Arrays.asList(authors.split(",")), downloadCount);
        try {
            bookService.addBook(book);
        } catch (Exception e) { e.printStackTrace(); }
        return "redirect:/";
    }

    // Xóa sách
    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "redirect:/";
    }

    // Thêm người dùng
    @PostMapping("/users/add")
    public String addUser(@RequestParam int id, @RequestParam String name, @RequestParam String username, @RequestParam String email, @RequestParam String phone) {
        User user = new User(id, name, username, email, phone);
        try {
            userService.addUser(user);
        } catch (Exception e) { e.printStackTrace(); }
        return "redirect:/";
    }

    // Xóa người dùng
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
