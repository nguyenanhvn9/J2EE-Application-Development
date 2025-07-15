package com.example.project_01.controller;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Thymeleaf!");
        model.addAttribute("name", "Nguyễn Huy Cường");
        return "home"; // Tên file view (home.html)
    }
}
