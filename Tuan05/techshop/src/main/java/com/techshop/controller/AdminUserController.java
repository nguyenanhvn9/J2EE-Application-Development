package com.techshop.controller;

import com.techshop.model.User;
import com.techshop.model.Role;
import com.techshop.service.UserService;
import com.techshop.repository.UserRepository;
import com.techshop.util.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    // Chỉ ADMIN mới có thể truy cập quản lý users
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ThymeleafUtils thymeleafUtils;
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        try {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            
            // Thêm thông tin authorization
            model.addAttribute("isAdmin", thymeleafUtils.hasRole("ADMIN"));
            model.addAttribute("isManager", thymeleafUtils.hasRole("MANAGER"));
            model.addAttribute("isLeader", thymeleafUtils.hasRole("LEADER"));
            model.addAttribute("hasAnyAdminRole", thymeleafUtils.hasAnyRole("ADMIN", "MANAGER", "LEADER"));
            model.addAttribute("hasAnyManagerRole", thymeleafUtils.hasAnyRole("ADMIN", "MANAGER"));
            model.addAttribute("currentUserRole", thymeleafUtils.getCurrentUserRole());
            
            return "admin/users";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/change-role")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Role role = Role.fromString(newRole);
            user.setRole(role);
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