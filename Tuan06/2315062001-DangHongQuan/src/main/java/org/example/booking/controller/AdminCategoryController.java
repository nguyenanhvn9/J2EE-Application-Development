package org.example.booking.controller;


import jakarta.validation.Valid;
import org.example.booking.model.Category;
import org.example.booking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/categories/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Add Category");
        return "admin/categories/form";
    }

    @PostMapping("/add")
    public String addCategory(
            @Valid @ModelAttribute Category category,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Check if category name already exists
        if (categoryService.isCategoryNameExists(category.getName(), null)) {
            bindingResult.rejectValue("name", "error.category", "Category name already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Category");
            return "admin/categories/form";
        }

        try {
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category added successfully!");
            return "redirect:/admin/categories";
        } catch (Exception e) {
            model.addAttribute("pageTitle", "Add Category");
            model.addAttribute("errorMessage", "Error adding category: " + e.getMessage());
            return "admin/categories/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Edit Category");
        return "admin/categories/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute Category category,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        category.setId(id);

        // Check if category name already exists (excluding current category)
        if (categoryService.isCategoryNameExists(category.getName(), id)) {
            bindingResult.rejectValue("name", "error.category", "Category name already exists");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Category");
            return "admin/categories/form";
        }

        try {
            categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("successMessage", "Category updated successfully!");
            return "redirect:/admin/categories";
        } catch (Exception e) {
            model.addAttribute("pageTitle", "Edit Category");
            model.addAttribute("errorMessage", "Error updating category: " + e.getMessage());
            return "admin/categories/form";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.getCategoryById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            // Check if category has products
            if (!category.getProducts().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Cannot delete category that has products. Please move or delete all products first.");
                return "redirect:/admin/categories";
            }

            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @PostMapping("/toggle-status/{id}")
    public String toggleCategoryStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.getCategoryById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            category.setIsActive(!category.getIsActive());
            categoryService.saveCategory(category);

            String status = category.getIsActive() ? "activated" : "deactivated";
            redirectAttributes.addFlashAttribute("successMessage", "Category " + status + " successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating category status: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}