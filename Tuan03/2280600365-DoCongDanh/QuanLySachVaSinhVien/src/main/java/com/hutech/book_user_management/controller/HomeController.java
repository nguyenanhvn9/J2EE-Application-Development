package com.example.book_user_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Thymeleaf!");
        model.addAttribute("name", "Đỗ Công Danh");
        return "home"; // tên file HTML không cần .html
    }
}
