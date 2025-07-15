package com.example.ToDoList.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToTodos() {
        return "redirect:/todos";
    }
}
