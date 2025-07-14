package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Hiển thị danh sách người dùng
    @GetMapping("")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/index"; // templates/users/index.html
    }

    // Hiển thị thông tin 1 người dùng theo ID
    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "users/detail"; // templates/users/detail.html
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // templates/error.html
        }
    }

    // Hiển thị form thêm người dùng
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "users/add"; // templates/users/add.html
    }

    // Xử lý thêm người dùng
    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        boolean added = userService.addUser(user);
        if (added) {
            return "redirect:/users";
        } else {
            model.addAttribute("error", "User ID already exists.");
            return "users/add";
        }
    }

    // Hiển thị form cập nhật người dùng
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "users/edit"; // templates/users/edit.html
        } else {
            model.addAttribute("error", "User not found.");
            return "error";
        }
    }

    // Xử lý cập nhật người dùng
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable int id, @ModelAttribute("user") User user, Model model) {
        boolean updated = userService.updateUser(id, user);
        if (updated) {
            return "redirect:/users";
        } else {
            model.addAttribute("error", "User not found.");
            return "users/edit";
        }
    }

    // Xử lý xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return "redirect:/users";
        } else {
            model.addAttribute("error", "User not found.");
            return "error";
        }
    }
}
