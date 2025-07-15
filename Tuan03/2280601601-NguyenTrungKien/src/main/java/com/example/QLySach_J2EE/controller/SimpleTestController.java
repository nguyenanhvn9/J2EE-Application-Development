package com.example.QLySach_J2EE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleTestController {

    @GetMapping("/test-home")
    @ResponseBody
    public String testHome() {
        return "Home endpoint is working!";
    }

    @GetMapping("/test-books")
    @ResponseBody
    public String testBooks() {
        return "Books endpoint is working!";
    }

    @GetMapping("/test-about")
    @ResponseBody
    public String testAbout() {
        return "About endpoint is working!";
    }

    @GetMapping("/simple-home")
    public String simpleHome(Model model) {
        model.addAttribute("message", "Simple Home Page");
        return "simple";
    }
} 