package com.techshop.controller;

import com.techshop.model.Order;
import com.techshop.model.OrderStatus;
import com.techshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(@RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        List<Order> orders;
        if (status != null) {
            orders = orderService.findByStatus(status);
        } else if (keyword != null && !keyword.isEmpty()) {
            orders = orderService.searchByCustomer(keyword);
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", Arrays.asList(OrderStatus.values()));
        return "admin/orders";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrder(id);
        if (order == null)
            return "redirect:/admin/orders";
        model.addAttribute("order", order);
        model.addAttribute("statuses", Arrays.asList(OrderStatus.values()));
        return "admin/order_detail";
    }

    @PostMapping("/update-status/{id}")
    public String updateStatus(@PathVariable Long id, @RequestParam("status") OrderStatus status) {
        Order order = orderService.getOrder(id);
        if (order != null) {
            order.setStatus(status);
            orderService.saveOrder(order);
        }
        return "redirect:/admin/orders/detail/" + id;
    }
}