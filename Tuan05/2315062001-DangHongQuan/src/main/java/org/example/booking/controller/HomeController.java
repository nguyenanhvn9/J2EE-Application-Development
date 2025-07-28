package org.example.booking.controller;


import org.example.booking.model.Category;
import org.example.booking.model.Product;
import org.example.booking.service.CategoryService;
import org.example.booking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        // Get featured categories (active categories with products)
        List<Category> featuredCategories = categoryService.getActiveCategoriesWithProducts();

        // Get latest products (8 most recent)
        List<Product> latestProducts = productService.getLatestProducts();

        // Get best selling products (8 top sellers)
        List<Product> bestSellingProducts = productService.getBestSellingProducts(8);

        model.addAttribute("featuredCategories", featuredCategories);
        model.addAttribute("latestProducts", latestProducts);
        model.addAttribute("bestSellingProducts", bestSellingProducts);

        return "customer/home";
    }

    @GetMapping("/products")
    public String products(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            Model model) {

        Page<Product> productPage;
        Category selectedCategory = null;

        if (categoryId != null) {
            selectedCategory = categoryService.getCategoryById(categoryId).orElse(null);
            if (selectedCategory != null) {
                if (search != null && !search.trim().isEmpty()) {
                    productPage = productService.searchProductsByCategory(selectedCategory, search, page, size);
                } else {
                    productPage = productService.getProductsByCategory(selectedCategory, page, size);
                }
            } else {
                productPage = productService.getAllActiveProducts(page, size);
            }
        } else if (search != null && !search.trim().isEmpty()) {
            productPage = productService.searchProducts(search, page, size);
        } else {
            productPage = productService.getAllActiveProducts(page, size);
        }

        // Get all categories for filter
        List<Category> categories = categoryService.getActiveCategoriesWithProducts();

        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", selectedCategory);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "customer/products";
    }

    @GetMapping("/product-detail")
    public String productDetail(@RequestParam Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getIsActive()) {
            throw new RuntimeException("Product is not available");
        }

        // Get related products from same category
        List<Product> relatedProducts = productService.getProductsByCategory(product.getCategory(), 0, 4)
                .getContent()
                .stream()
                .filter(p -> !p.getId().equals(product.getId()))
                .toList();

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        return "customer/product-detail";
    }
}