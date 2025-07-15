
package com.example.project_01.controller;

import com.example.project_01.model.User;
import com.example.project_01.service.UserService;
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
        boolean added = userService.addUser(user);
        return added ? "User added successfully!" : "User with this ID already exists.";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        boolean updated = userService.updateUser(id, user);
        return updated ? "User updated successfully!" : "User not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "User deleted successfully!" : "User not found!";
    }
}
