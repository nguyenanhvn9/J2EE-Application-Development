package com.techshop.controller;

import com.techshop.model.Category;
import com.techshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/categories";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category_add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category_add";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategory(id);
        if (category == null)
            return "redirect:/admin/categories";
        model.addAttribute("category", category);
        return "admin/category_edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute("category") @Valid Category category,
            BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category_edit";
        }
        category.setId(id);
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            model.addAttribute("deleteError", "Không thể xóa danh mục vì đã có sản phẩm liên quan!");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/categories";
        }
        return "redirect:/admin/categories";
    }
}