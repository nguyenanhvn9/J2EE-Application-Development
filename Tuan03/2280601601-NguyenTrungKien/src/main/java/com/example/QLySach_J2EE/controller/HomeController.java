package com.example.QLySach_J2EE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với hệ thống quản lý sách!");
        model.addAttribute("name", "Nguyễn Trung Kiên");
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
} 