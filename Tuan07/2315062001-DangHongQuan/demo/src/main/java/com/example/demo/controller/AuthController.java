package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String fullName,
            @RequestParam String phone,
            Model model
    ) {
        try {
            userService.registerUser(username, password, email, fullName, phone);
            return "redirect:/login?signupSuccess=true";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginForm(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String signupSuccess) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (signupSuccess != null) {
            model.addAttribute("signupSuccess", "Registration successful! Please log in.");
        }
        return "login";
    }
}