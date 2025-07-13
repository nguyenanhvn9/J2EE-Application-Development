package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // KHÔNG có endpoint /fetch

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();  // sẽ tự động fetch nếu danh sách rỗng
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User added successfully!";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        boolean updated = userService.updateUser(id, user);
        return updated ? "Updated successfully" : "User not found";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "Deleted successfully" : "User not found";
    }
}
