package com.example.__NguyenNhatNam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Thymeleaf!");
        model.addAttribute("name", "Nguyễn Nhật Nam");
        return "home";
    }
}
