package com.example.BaitapJ2EE.QuanLyNguoiDung.controller;

import com.example.BaitapJ2EE.QuanLyNguoiDung.model.User;
import com.example.BaitapJ2EE.QuanLyNguoiDung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "redirect:/user";
        }
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user) {
        User existing = userService.getUserById(id);
        if (existing == null) {
            return "redirect:/user";
        }
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User existing = userService.getUserById(id);
        if (existing == null) {
            return "redirect:/user";
        }
        userService.deleteUser(id);
        return "redirect:/user";
    }
} 