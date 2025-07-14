package hutech.example.CMP141.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với Thymeleaf!");
        model.addAttribute("name", "Trương Minh Mẫn đẹp trai nhất Hutech");
        return "home"; // Tên file view (home.html)
    }
}