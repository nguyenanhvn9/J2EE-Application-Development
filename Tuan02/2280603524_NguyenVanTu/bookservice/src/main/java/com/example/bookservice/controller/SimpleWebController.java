package com.example.bookservice.controller;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/simple")
public class SimpleWebController {

    @Autowired
    private BookService bookService;

    // Test endpoint đơn giản
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "SimpleWebController is working! Time: " + System.currentTimeMillis();
    }

    // Test BookService
    @GetMapping("/test-service")
    @ResponseBody
    public String testService() {
        try {
            long count = bookService.countBooks();
            return "BookService is working! Book count: " + count;
        } catch (Exception e) {
            return "BookService error: " + e.getMessage();
        }
    }

    // Test danh sách sách đơn giản
    @GetMapping("/books")
    public String listBooks(Model model) {
        try {
            System.out.println("=== SimpleWebController /simple/books called ===");
            
            List<BookDTO> books = bookService.getAllBooks();
            System.out.println("Retrieved " + books.size() + " books from service");
            
            model.addAttribute("books", books);
            model.addAttribute("totalBooks", books.size());
            
            return "simple-list";
        } catch (Exception e) {
            System.err.println("Error in /simple/books endpoint: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
}
