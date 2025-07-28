package com.techshop.controller;

import com.techshop.model.Order;
import com.techshop.service.OrderService;
import com.techshop.service.ProductService;
import com.techshop.service.CategoryService;
import com.techshop.util.ControllerUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techshop.model.Category;
import com.techshop.model.Product;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import com.techshop.service.UserService;

@Controller
public class CustomerController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            Map<Category, List<Product>> productsByCategory = new LinkedHashMap<>();
            for (Category cat : categories) {
                List<Product> products = productService.findByCategory(cat.getId());
                if (!products.isEmpty()) {
                    productsByCategory.put(cat, products);
                }
            }
            model.addAttribute("productsByCategory", productsByCategory);
            model.addAttribute("categories", categories);
            ControllerUtils.addUsername(model);
            // Truyền biến userRole từ database
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userRole = null;
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                com.techshop.model.User user = userService.findByUsername(auth.getName());
                if (user != null) {
                    userRole = user.getRole().getValue();
                }
            }
            model.addAttribute("userRole", userRole);
            return "index";
        } catch (Exception e) {
            System.err.println("Lỗi khi render trang index: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Có lỗi xảy ra khi tải trang chủ!");
            return "index";
        }
    }

    @GetMapping("/order-history")
    public String orderHistory(HttpSession session, Model model) {
        ControllerUtils.addUsername(model);
        String phone = (String) session.getAttribute("customerPhone");
        if (phone == null || phone.isEmpty()) {
            model.addAttribute("orders", null);
            model.addAttribute("message", "Bạn cần đặt hàng trước để xem lịch sử!");
            return "order_history";
        }
        List<Order> orders = orderService.findByPhone(phone);
        model.addAttribute("orders", orders);
        return "order_history";
    }
}