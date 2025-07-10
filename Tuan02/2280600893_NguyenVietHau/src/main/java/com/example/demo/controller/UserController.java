package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user)
                : ResponseEntity.status(404).body("User not found.");
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        boolean added = userService.addUser(user);
        return added ? ResponseEntity.ok("User added successfully.")
                : ResponseEntity.badRequest().body("User ID already exists.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        boolean updated = userService.updateUser(id, user);
        return updated ? ResponseEntity.ok("User updated successfully.")
                : ResponseEntity.status(404).body("User not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok("User deleted successfully.")
                : ResponseEntity.status(404).body("User not found.");
    }
}
