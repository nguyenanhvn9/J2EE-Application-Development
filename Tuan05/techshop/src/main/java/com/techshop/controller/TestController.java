package com.techshop.controller;

import com.techshop.model.User;
import com.techshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test/users")
    public String testUsers(Model model) {
        try {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            model.addAttribute("count", users.size());
            return "admin/users_simple";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi: " + e.getMessage());
            return "error";
        }
    }
}