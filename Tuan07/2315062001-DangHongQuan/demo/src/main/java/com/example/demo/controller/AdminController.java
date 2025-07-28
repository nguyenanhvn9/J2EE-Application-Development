package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.propertyeditor.CategoryPropertyEditor;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid; // Thêm import này nếu chưa có
import org.springframework.validation.BindingResult; // Thêm import này nếu chưa có
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.entity.Category;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.converter.CategoryByIdConverter;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/add-product";
    }
    @Autowired // Tiêm PropertyEditor vào Controller
    private CategoryPropertyEditor categoryPropertyEditor;

    @Autowired // Tiêm converter vào controller
    private CategoryByIdConverter categoryByIdConverter;

    // Trong AdminController.java
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, categoryPropertyEditor); // Đảm bảo đúng Type
    }

    @PostMapping("/add-product")
    public String addProductSubmit(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam double price,
            @RequestParam int stockQuantity,
            @RequestParam MultipartFile imageFile,
            @RequestParam Long categoryId,
            Model model
    ) {
        try {
            productService.addProduct(name, description, price, stockQuantity, imageFile, categoryId);
            return "redirect:/admin/products?addSuccess=true";
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading image: " + e.getMessage());
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/add-product";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/add-product";
        }
    }

    @GetMapping("/products")
    public String listProducts(
            Model model,
            @RequestParam(value = "addSuccess", required = false) String addSuccess,
            @RequestParam(value = "updateSuccess", required = false) String updateSuccess, // THÊM DÒNG NÀY
            @RequestParam(value = "deleteSuccess", required = false) String deleteSuccess, // THÊM DÒNG NÀY
            @RequestParam(value = "errorMessage", required = false) String errorMessage, // THÊM DÒNG NÀY
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId
    ) {
        Page<Product> productsPage = productService.getProducts(keyword, categoryId, page, size, sortBy, sortDir);
        List<Category> categories = productService.getAllCategories(); // Hoặc categoryService.getAllCategories();

        model.addAttribute("products", productsPage); // Truyền Page object
        model.addAttribute("categories", categories); // Truyền danh sách categories cho dropdown lọc
        model.addAttribute("keyword", keyword); // Giữ lại keyword trên form
        model.addAttribute("selectedCategoryId", categoryId); // Giữ lại categoryId trên form
        model.addAttribute("currentPage", productsPage.getNumber());
        model.addAttribute("totalPages", productsPage.getTotalPages());
        model.addAttribute("totalItems", productsPage.getTotalElements());

        // Hiển thị các thông báo flash attribute
        if (addSuccess != null) {
            model.addAttribute("addSuccess", "Sản phẩm đã được thêm thành công!");
        }
        if (updateSuccess != null) { // THÊM KHỐI NÀY
            model.addAttribute("updateSuccess", "Sản phẩm đã được cập nhật thành công!");
        }
        if (deleteSuccess != null) { // THÊM KHỐI NÀY
            model.addAttribute("deleteSuccess", "Sản phẩm đã được xóa thành công!");
        }
        if (errorMessage != null) { // THÊM KHỐI NÀY
            model.addAttribute("errorMessage", errorMessage);
        }
        return "admin/products";
    }
    // Trong AdminController.java
    @GetMapping("/edit-product/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/admin/products?errorMessage=Sản phẩm không tồn tại.";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/edit-product";
    }

    // THÊM MỚI HOẶC CẬP NHẬT PHƯƠNG THỨC NÀY
    // --- POST request để xử lý submit form chỉnh sửa sản phẩm ---
// Trong AdminController.java
    @PostMapping("/edit-product/{id}")
    public String editProductSubmit(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // Log để kiểm tra sau khi binding, trước khi validation
        System.out.println("Received product ID (after model binding): " + product.getId());
        System.out.println("Product name (after model binding): " + product.getName());
        // Lần này, hy vọng Category object (after model binding) sẽ KHÔNG phải là NULL
        System.out.println("Category object (after model binding): " + (product.getCategory() != null ? product.getCategory().getName() : "NULL"));

        if (bindingResult.hasErrors()) {
            product.setId(id);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("errorMessage", "Vui lòng kiểm tra lại các trường dữ liệu.");
            return "admin/edit-product";
        }

        try {
            product.setId(id); // Đảm bảo đúng sản phẩm được cập nhật
            productService.updateProduct(product, imageFile);
            redirectAttributes.addFlashAttribute("updateSuccess", "Sản phẩm đã được cập nhật thành công!");
            return "redirect:/admin/products";
        } catch (IOException e) {
            System.err.println("Error uploading image: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", "Lỗi khi tải ảnh lên: " + e.getMessage());
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/edit-product";
        } catch (RuntimeException e) {
            System.err.println("Error updating product: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/edit-product";
        }
    }
    @GetMapping("/orders")
    public String listOrders(
            Model model,
            @RequestParam(value = "status", required = false) OrderStatus status, // Sử dụng OrderStatus enum
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "successMessage", required = false) String successMessage, // Flash attribute
            @RequestParam(value = "errorMessage", required = false) String errorMessage // Flash attribute
    ) {
        List<Order> orders;
        if (status != null) {
            orders = orderService.getOrdersByStatus(status);
        } else if (keyword != null && !keyword.isEmpty()) {
            orders = orderService.searchOrders(keyword);
        } else {
            orders = orderService.getAllOrders(); // Giả sử getAllOrders trả về sắp xếp theo ID/ngày giảm dần
        }

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status); // Giữ trạng thái đã chọn trên dropdown
        model.addAttribute("keyword", keyword); // Giữ từ khóa tìm kiếm

        // Add flash messages if any
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "admin/orders";
    }

    @GetMapping("/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model,
                              @RequestParam(value = "successMessage", required = false) String successMessage,
                              @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/admin/orders?errorMessage=Đơn hàng không tồn tại.";
        }
        model.addAttribute("order", order);

        // Add flash messages if any
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "admin/order-detail";
    }

    @PostMapping("/orders/{id}/update-status")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam("newStatus") OrderStatus newStatus, // Sử dụng OrderStatus enum
                                    RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedBy = authentication.getName(); // Lấy username của người đang đăng nhập

        try {
            orderService.updateOrderStatus(id, newStatus, updatedBy); // Cần cập nhật service để nhận OrderStatus
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái đơn hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/orders/{id}"; // Chuyển hướng về trang chi tiết đơn hàng
    }
    // --- POST request for Delete Product ---
    @PostMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Product deleted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/products";
    }
}