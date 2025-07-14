package BaiTH_03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/books")
    public String showBooksPage(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Thymeleaf!");
        return "books"; // trỏ đến books.html trong folder templates
    }
}