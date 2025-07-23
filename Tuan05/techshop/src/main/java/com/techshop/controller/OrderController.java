package com.techshop.controller;

import com.techshop.model.Order;
import com.techshop.model.OrderItem;
import com.techshop.service.OrderService;
import com.techshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model, Authentication authentication) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return "redirect:/profile?tab=orders";
        }
        // Kiểm tra quyền: chỉ cho phép user xem đơn của chính mình
        String username = authentication.getName();
        Long userId = userService.findByUsername(username).getId();
        if (!order.getUserId().equals(userId)) {
            return "redirect:/profile?tab=orders";
        }
        model.addAttribute("order", order);
        List<OrderItem> items = order.getItems();
        model.addAttribute("items", items);
        return "order_detail";
    }
}