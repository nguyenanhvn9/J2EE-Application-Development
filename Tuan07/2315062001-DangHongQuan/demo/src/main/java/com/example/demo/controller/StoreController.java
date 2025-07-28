package com.example.demo.controller;

// package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public String listStores(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("newStore", new Store()); // For add form
        return "admin/store-list"; // Create this Thymeleaf template
    }

    @PostMapping
    public String addStore(@ModelAttribute Store store, RedirectAttributes redirectAttributes) {
        try {
            storeService.saveStore(store);
            redirectAttributes.addFlashAttribute("successMessage", "Store added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding store: " + e.getMessage());
        }
        return "redirect:/admin/stores";
    }

    @GetMapping("/edit/{id}")
    public String editStoreForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return storeService.getStoreById(id)
                .map(store -> {
                    model.addAttribute("storeToEdit", store);
                    return "admin/store-edit"; // Create this Thymeleaf template
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Store not found.");
                    return "redirect:/admin/stores";
                });
    }

    @PostMapping("/update")
    public String updateStore(@ModelAttribute Store store, RedirectAttributes redirectAttributes) {
        try {
            storeService.saveStore(store); // save method handles both insert and update
            redirectAttributes.addFlashAttribute("successMessage", "Store updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating store: " + e.getMessage());
        }
        return "redirect:/admin/stores";
    }

    @PostMapping("/delete/{id}")
    public String deleteStore(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            storeService.deleteStore(id);
            redirectAttributes.addFlashAttribute("successMessage", "Store deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting store: " + e.getMessage());
        }
        return "redirect:/admin/stores";
    }
}