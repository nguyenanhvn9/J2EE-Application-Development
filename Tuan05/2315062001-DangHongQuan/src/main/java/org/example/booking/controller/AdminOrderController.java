package org.example.booking.controller;


import org.example.booking.model.Order;
import org.example.booking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            Model model) {

        Page<Order> orderPage;

        if (status != null && !status.isEmpty() && search != null && !search.trim().isEmpty()) {
            orderPage = orderService.searchOrdersByStatus(status, search, page, size);
        } else if (status != null && !status.isEmpty()) {
            orderPage = orderService.getOrdersByStatus(status, page, size);
        } else if (search != null && !search.trim().isEmpty()) {
            orderPage = orderService.searchOrders(search, page, size);
        } else {
            orderPage = orderService.getAllOrders(page, size);
        }

        // Order statistics
        long pendingCount = orderService.getOrderCountByStatus("PENDING");
        long processingCount = orderService.getOrderCountByStatus("PROCESSING");
        long shippedCount = orderService.getOrderCountByStatus("SHIPPED");
        long deliveredCount = orderService.getOrderCountByStatus("DELIVERED");
        long cancelledCount = orderService.getOrderCountByStatus("CANCELLED");

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("currentSearch", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        // Statistics
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("processingCount", processingCount);
        model.addAttribute("shippedCount", shippedCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("cancelledCount", cancelledCount);

        return "admin/orders/list";
    }

    @GetMapping("/view/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        model.addAttribute("order", order);
        return "admin/orders/view";
    }

    @PostMapping("/update-status/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String newStatus,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        try {
            String updatedBy = userDetails.getUsername();
            orderService.updateOrderStatus(id, newStatus, updatedBy);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Order status updated to " + newStatus + " successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating order status: " + e.getMessage());
        }

        return "redirect:/admin/orders/view/" + id;
    }

    @GetMapping("/dashboard")
    public String orderDashboard(Model model) {
        // Recent orders
        List<Order> recentOrders = orderService.getRecentOrders();

        // Order statistics
        long pendingCount = orderService.getOrderCountByStatus("PENDING");
        long processingCount = orderService.getOrderCountByStatus("PROCESSING");
        long shippedCount = orderService.getOrderCountByStatus("SHIPPED");
        long deliveredCount = orderService.getOrderCountByStatus("DELIVERED");
        long cancelledCount = orderService.getOrderCountByStatus("CANCELLED");

        // Revenue
        java.math.BigDecimal totalRevenue = orderService.getTotalRevenue();

        // Today's revenue
        java.time.LocalDateTime startOfDay = java.time.LocalDate.now().atStartOfDay();
        java.time.LocalDateTime endOfDay = startOfDay.plusDays(1);
        java.math.BigDecimal todayRevenue = orderService.getRevenueByDateRange(startOfDay, endOfDay);

        model.addAttribute("recentOrders", recentOrders);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("processingCount", processingCount);
        model.addAttribute("shippedCount", shippedCount);
        model.addAttribute("deliveredCount", deliveredCount);
        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("todayRevenue", todayRevenue);

        return "admin/orders/dashboard";
    }
}