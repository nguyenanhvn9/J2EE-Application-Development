    package org.example.booking.controller;

    import jakarta.validation.Valid;
    import org.example.booking.model.Category;
    import org.example.booking.model.Product;
    import org.example.booking.service.CategoryService;
    import org.example.booking.service.ProductService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import java.util.List;

    @Controller
    @RequestMapping("/admin/products")
    public class AdminProductController {

        @Autowired
        private ProductService productService;

        @Autowired
        private CategoryService categoryService;

        @GetMapping
        public String listProducts(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(required = false) String search,
                @RequestParam(required = false) Long categoryId,
                Model model) {

            Page<Product> productPage;
            Category selectedCategory = null;

            if (categoryId != null) {
                selectedCategory = categoryService.getCategoryById(categoryId).orElse(null);
                if (selectedCategory != null) {
                    productPage = productService.getProductsByCategoryForAdmin(selectedCategory, page, size);
                } else {
                    productPage = productService.getAllProductsForAdmin(page, size);
                }
            } else if (search != null && !search.trim().isEmpty()) {
                productPage = productService.searchProductsForAdmin(search, page, size);
            } else {
                productPage = productService.getAllProductsForAdmin(page, size);
            }

            List<Category> categories = categoryService.getAllCategories();

            model.addAttribute("productPage", productPage);
            model.addAttribute("categories", categories);
            model.addAttribute("selectedCategory", selectedCategory);
            model.addAttribute("currentSearch", search);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());

            return "admin/products/list";
        }

        @GetMapping("/add")
        public String showAddForm(Model model) {
            Product product = new Product();
            List<Category> categories = categoryService.getAllActiveCategories();

            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            model.addAttribute("pageTitle", "Add Product");

            return "admin/products/form";
        }

        @PostMapping("/add")
        public String addProduct(
                @Valid @ModelAttribute Product product,
                BindingResult bindingResult,
                Model model,
                RedirectAttributes redirectAttributes) {

            // Custom validation
            validateProduct(product, bindingResult);

            if (bindingResult.hasErrors()) {
                List<Category> categories = categoryService.getAllActiveCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("pageTitle", "Add Product");
                return "admin/products/form";
            }

            try {
                productService.saveProduct(product);
                redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
                return "redirect:/admin/products";
            } catch (Exception e) {
                List<Category> categories = categoryService.getAllActiveCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("pageTitle", "Add Product");
                model.addAttribute("errorMessage", "Error adding product: " + e.getMessage());
                return "admin/products/form";
            }
        }

        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable Long id, Model model) {
            Product product = productService.getProductById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            List<Category> categories = categoryService.getAllActiveCategories();

            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
            model.addAttribute("pageTitle", "Edit Product");

            return "admin/products/form";
        }

        @PostMapping("/edit/{id}")
        public String updateProduct(
                @PathVariable Long id,
                @Valid @ModelAttribute Product product,
                BindingResult bindingResult,
                Model model,
                RedirectAttributes redirectAttributes) {

            product.setId(id);

            // Custom validation
            validateProduct(product, bindingResult);

            if (bindingResult.hasErrors()) {
                List<Category> categories = categoryService.getAllActiveCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("pageTitle", "Edit Product");
                return "admin/products/form";
            }

            try {
                productService.saveProduct(product);
                redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
                return "redirect:/admin/products";
            } catch (Exception e) {
                List<Category> categories = categoryService.getAllActiveCategories();
                model.addAttribute("categories", categories);
                model.addAttribute("pageTitle", "Edit Product");
                model.addAttribute("errorMessage", "Error updating product: " + e.getMessage());
                return "admin/products/form";
            }
        }

        @GetMapping("/view/{id}")
        public String viewProduct(@PathVariable Long id, Model model) {
            Product product = productService.getProductById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            model.addAttribute("product", product);
            return "admin/products/view";
        }

        @PostMapping("/delete/{id}")
        public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
            try {
                productService.deleteProduct(id);
                redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error deleting product: " + e.getMessage());
            }
            return "redirect:/admin/products";
        }

        @PostMapping("/toggle-status/{id}")
        public String toggleProductStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
            try {
                Product product = productService.getProductById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                product.setIsActive(!product.getIsActive());
                productService.saveProduct(product);

                String status = product.getIsActive() ? "activated" : "deactivated";
                redirectAttributes.addFlashAttribute("successMessage", "Product " + status + " successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error updating product status: " + e.getMessage());
            }
            return "redirect:/admin/products";
        }

        private void validateProduct(Product product, BindingResult bindingResult) {
            // Check if product name already exists
            if (productService.isProductNameExists(product.getName(), product.getId())) {
                bindingResult.rejectValue("name", "error.product", "Product name already exists");
            }

            // Validate name length (additional check)
            if (product.getName() != null && product.getName().trim().length() < 10) {
                bindingResult.rejectValue("name", "error.product", "Product name must be at least 10 characters");
            }

            // Validate price
            if (product.getPrice() != null && product.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                bindingResult.rejectValue("price", "error.product", "Price must be greater than 0");
            }

            // Validate stock quantity
            if (product.getStockQuantity() != null && product.getStockQuantity() < 0) {
                bindingResult.rejectValue("stockQuantity", "error.product", "Stock quantity cannot be negative");
            }

            if (product.getStockQuantity() != null && product.getStockQuantity() > 9999) {
                bindingResult.rejectValue("stockQuantity", "error.product", "Stock quantity cannot exceed 9999");
            }
        }
    }