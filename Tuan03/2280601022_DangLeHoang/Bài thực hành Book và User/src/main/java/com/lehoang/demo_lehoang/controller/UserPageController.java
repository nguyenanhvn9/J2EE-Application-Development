package com.lehoang.demo_lehoang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    @GetMapping("/users")
    public String usersPage() {
        return "users";
    }
} 