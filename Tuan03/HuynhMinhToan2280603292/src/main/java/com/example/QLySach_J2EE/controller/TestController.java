package com.example.QLySach_J2EE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/test-simple")
    @ResponseBody
    public String testSimple() {
        return "Hello! Spring Boot is working correctly!";
    }

    @GetMapping("/test-page")
    public String testPage(Model model) {
        try {
            model.addAttribute("message", "Test page is working!");
            return "test";
        } catch (Exception e) {
            System.err.println("Error in testPage: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }
} 