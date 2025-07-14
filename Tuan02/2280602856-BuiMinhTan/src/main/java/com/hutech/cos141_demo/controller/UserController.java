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
    private UserService service;

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getById(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        service.addUser(user);
        return "User added.";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        boolean updated = service.updateUser(id, user);
        return updated ? "User updated." : "User not found.";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = service.deleteUser(id);
        return deleted ? "User deleted." : "User not found.";
    }
}
