package com.techshop.controller;

import com.techshop.model.Product;
import com.techshop.model.Category;
import com.techshop.service.ProductService;
import com.techshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product_add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors() || product.getCategory() == null || product.getCategory().getId() == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryError", "Vui lòng chọn danh mục!");
            return "admin/product_add";
        }
        Long categoryId = product.getCategory().getId();
        Category category = categoryService.getCategory(categoryId);
        if (category == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryError", "Danh mục không hợp lệ!");
            return "admin/product_add";
        }
        product.setCategory(category);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null)
            return "redirect:/admin/products";
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product_edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute("product") @Valid Product product,
            BindingResult result, Model model) {
        if (result.hasErrors() || product.getCategory() == null || product.getCategory().getId() == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryError", "Vui lòng chọn danh mục!");
            return "admin/product_edit";
        }
        product.setId(id);
        Long categoryId = product.getCategory().getId();
        Category category = categoryService.getCategory(categoryId);
        if (category == null) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("categoryError", "Danh mục không hợp lệ!");
            return "admin/product_edit";
        }
        product.setCategory(category);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            model.addAttribute("deleteError", "Không thể xóa sản phẩm vì đã có đơn hàng liên quan!");
            model.addAttribute("products", productService.getAllProducts());
            return "admin/products";
        }
        return "redirect:/admin/products";
    }
}