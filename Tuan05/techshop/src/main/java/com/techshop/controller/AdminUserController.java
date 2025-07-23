package com.techshop.controller;

import com.techshop.model.User;
import com.techshop.service.UserService;
import com.techshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/change-role")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && (newRole.equals("USER") || newRole.equals("ADMIN"))) {
            user.setRole(newRole);
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/toggle-active")
    public String toggleUserActive(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setActive(!user.isActive());
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }
}