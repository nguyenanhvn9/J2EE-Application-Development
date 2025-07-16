package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/orders-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("order", new Order());
        return "orders/add-order";
    }

    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("order") Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/add-order";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalStateException("Order with ID " + id + " does not exist."));
        model.addAttribute("order", order);
        return "orders/edit-order";
    }

    @PostMapping("/edit/{id}")
    public String updateOrder(@PathVariable Long id, @Valid @ModelAttribute("order") Order order, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/edit-order";
        }
        order.setId(id);
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return "redirect:/orders";
    }
}