package com.hutech.__BuiMinhTan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/Books")
    public String books(Model model) {
        model.addAttribute("name", "Bùi Minh Tân");
        return "books";  // -> sẽ tìm templates/books.html
    }
}
