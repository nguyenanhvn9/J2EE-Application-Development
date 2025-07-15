package com.example.QLySach_J2EE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebugController {

    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        return "Debug endpoint is working!";
    }

    @GetMapping("/debug-page")
    public String debugPage(Model model) {
        try {
            model.addAttribute("message", "Debug page is working!");
            model.addAttribute("timestamp", System.currentTimeMillis());
            return "debug";
        } catch (Exception e) {
            System.err.println("Error in debugPage: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/test-books-simple")
    @ResponseBody
    public String testBooksSimple() {
        return "Books endpoint should work!";
    }
} 