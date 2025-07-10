package com.hutech.cos141_demo.controller;

import com.hutech.cos141_demo.model.User;
import com.hutech.cos141_demo.service.UserService;
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
        return userService.getUserById(id);
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
            return "User with ID " + id + " not found.";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return "User deleted successfully!";
        } else {
            return "User with ID " + id + " not found.";
        }
    }
} 