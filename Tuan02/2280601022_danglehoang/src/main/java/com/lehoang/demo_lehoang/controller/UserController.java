package com.lehoang.demo_lehoang.controller;

import com.lehoang.demo_lehoang.model.User;
import com.lehoang.demo_lehoang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        Optional<User> user = userService.getUserById(id);
        return user.orElse(null);
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            return "User added successfully!";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> result = userService.updateUser(id, updatedUser);
        if (result.isPresent()) {
            return "User updated successfully!";
        } else {
            return "User not found!";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return "User deleted successfully!";
        } else {
            return "User not found!";
        }
    }
} 