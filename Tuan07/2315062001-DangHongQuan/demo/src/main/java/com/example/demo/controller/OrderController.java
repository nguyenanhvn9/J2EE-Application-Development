package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/confirm")
    public String confirmOrder(Model model) {
        // Lấy Authentication từ SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Ép kiểu thành User entity
        System.out.println("Logged in user: " + user.getUsername() + ", FullName: " + user.getFullName());

        // Thêm user vào model
        model.addAttribute("user", user);

        // Xử lý logic đặt hàng (giả sử)
        // orderService.createOrder(user, ...);

        return "order-confirmation";
    }
}