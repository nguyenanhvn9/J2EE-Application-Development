package com.techshop.controller;

import com.techshop.service.OrderService;
import com.techshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String dashboardPage(Model model) {
        return "admin/dashboard";
    }

    @GetMapping("/revenue-7days")
    @ResponseBody
    public List<Map<String, Object>> revenue7Days() {
        return orderService.getRevenueLast7Days();
    }

    @GetMapping("/orders-24h")
    @ResponseBody
    public int orders24h() {
        return orderService.countOrdersLast24h();
    }

    @GetMapping("/top-products")
    @ResponseBody
    public List<Map<String, Object>> topProducts() {
        return productService.getTop5BestSellersThisMonth();
    }
}