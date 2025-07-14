package com.example.usermanagement.controller;

import com.example.usermanagement.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "user-add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        user.setId(nextId++);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(updatedUser.getId())) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setRole(updatedUser.getRole());
                break;
            }
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.removeIf(user -> user.getId().equals(id));
        return "redirect:/users";
    }
}
