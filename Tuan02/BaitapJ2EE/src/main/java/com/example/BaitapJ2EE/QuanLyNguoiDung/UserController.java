package com.example.BaitapJ2EE.QuanLyNguoiDung;

import com.example.BaitapJ2EE.QuanLyNguoiDung.model.User;
import com.example.BaitapJ2EE.QuanLyNguoiDung.service.UserService;
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
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public boolean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public Optional<User> updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
} 