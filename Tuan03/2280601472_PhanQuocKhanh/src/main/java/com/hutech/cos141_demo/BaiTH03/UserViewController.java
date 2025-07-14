package com.hutech.cos141_demo.BaiTH03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UserViewController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-user";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam String username, @RequestParam String email, @RequestParam String phone) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        int newId = userService.getAllUsers().stream().mapToInt(User::getId).max().orElse(0) + 1;
        user.setId(newId);
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.getUserById(id).orElse(null);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable int id, @RequestParam String name, @RequestParam String username, @RequestParam String email, @RequestParam String phone) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
} 