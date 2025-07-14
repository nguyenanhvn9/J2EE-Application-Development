package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserWebController {
    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("message", "User List");
        model.addAttribute("users", userService.getAllUsers());
        return "user/list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("message", "Add New User");
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user, Model model) {
        try {
            userService.addUser(user);
            model.addAttribute("message", "User added successfully!");
            return "redirect:/users";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "user/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable int id, Model model) {
        try {
            User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            model.addAttribute("message", "Edit User");
            model.addAttribute("user", user);
            return "user/add";
        } catch (ResourceNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}