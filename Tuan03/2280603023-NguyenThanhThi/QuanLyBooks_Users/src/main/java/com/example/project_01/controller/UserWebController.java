
package com.example.project_01.controller;

import com.example.project_01.model.User;
import com.example.project_01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserWebController {
    @Autowired
    private UserService userService;

    // Hiển thị danh sách user
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list-user";
    }

    // Hiển thị form thêm user
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "form-user";
    }

    // Xử lý thêm user
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        // Nếu là thêm mới (id = 0 hoặc null), tự động sinh id mới
        if (user.getId() == 0) {
            int maxId = userService.getAllUsers().stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
            user.setId(maxId + 1);
        }
        userService.addUser(user);
        return "redirect:/users";
    }

    // Hiển thị form sửa user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        User user = userService.getUserById(id).orElse(new User());
        model.addAttribute("user", user);
        return "form-user";
    }

    // Xử lý sửa user
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable int id, @ModelAttribute User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Xóa user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
