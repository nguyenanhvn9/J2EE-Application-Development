package com.example.bookservice.controller;

import com.example.bookservice.dto.UserDTO;
import com.example.bookservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserWebController {

    @Autowired
    private UserService userService;

    // Hiển thị danh sách users
    @GetMapping
    public String listUsers(Model model) {
        try {
            System.out.println("=== UserWebController /users called ===");
            
            List<UserDTO> users = userService.getAllUsers();
            System.out.println("Retrieved " + users.size() + " users from service");
            
            model.addAttribute("users", users);
            model.addAttribute("totalUsers", users.size());
            
            System.out.println("Model attributes set, returning template");
            return "users/list";
        } catch (Exception e) {
            System.err.println("Error in /users endpoint: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("users", List.of());
            model.addAttribute("totalUsers", 0);
            return "users/list";
        }
    }

    // Hiển thị form thêm user mới
    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        try {
            System.out.println("=== UserWebController /users/new called ===");
            UserDTO user = new UserDTO();
            
            model.addAttribute("user", user);
            System.out.println("User DTO created and added to model");
            return "users/form";
        } catch (Exception e) {
            System.err.println("Error in /users/new endpoint: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải form: " + e.getMessage());
            return "redirect:/users";
        }
    }

    // Xử lý thêm user mới
    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") UserDTO user,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/form";
        }

        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating user: " + e.getMessage());
            return "redirect:/users/new";
        }
    }

    // Hiển thị chi tiết user
    @GetMapping("/{id}")
    public String showUserDetail(@PathVariable Long id, Model model) {
        try {
            UserDTO user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "users/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "User not found");
            return "redirect:/users";
        }
    }

    // Hiển thị form chỉnh sửa user
    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        try {
            UserDTO user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "users/form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "User not found");
            return "redirect:/users";
        }
    }

    // Xử lý cập nhật user
    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                            @Valid @ModelAttribute("user") UserDTO user,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "users/form";
        }

        try {
            Optional<UserDTO> updatedUser = userService.updateUser(id, user);
            if (updatedUser.isPresent()) {
                redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            }
            return "redirect:/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
            return "redirect:/users/" + id + "/edit";
        }
    }

    // Xóa user
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/users";
    }
}
