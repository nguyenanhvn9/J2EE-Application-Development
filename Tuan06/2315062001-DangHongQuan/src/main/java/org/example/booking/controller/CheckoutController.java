package org.example.booking.controller;

import jakarta.validation.Valid;
import org.example.booking.dto.CheckoutRequest;
import org.example.booking.model.Order;
import org.example.booking.model.ShoppingCart;
import org.example.booking.model.User;
import org.example.booking.service.OrderService;
import org.example.booking.service.ShoppingCartService;
import org.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String checkout(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }


        User user = userService.findByUsername(userDetails.getUsername());
        ShoppingCart cart = shoppingCartService.getCartByUser(user);

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        try {
            shoppingCartService.validateCartForCheckout(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/cart";
        }

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        // Pre-fill with user information
        checkoutRequest.setCustomerName(user.getFullName());
        checkoutRequest.setCustomerEmail(user.getEmail());
        checkoutRequest.setCustomerPhone(user.getPhone());

        model.addAttribute("checkoutRequest", checkoutRequest);
        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", cart.getTotalAmount());

        return "customer/checkout";
    }

    @PostMapping("/process")
    public String processCheckout(
            @Valid @ModelAttribute CheckoutRequest checkoutRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(userDetails.getUsername());
        ShoppingCart cart = shoppingCartService.getCartByUser(user);

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("subtotal", cart.getTotalAmount());
            return "customer/checkout";
        }

        try {
            // Calculate shipping fee based on shipping method
            BigDecimal shippingFee = calculateShippingFee(checkoutRequest.getShippingMethod());

            Order order = orderService.createOrder(
                    user,
                    checkoutRequest.getCustomerName(),
                    checkoutRequest.getShippingAddress(),
                    checkoutRequest.getCustomerPhone(),
                    shippingFee
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Order placed successfully! Order ID: " + order.getId());
            return "redirect:/checkout/success/" + order.getId();

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("cart", cart);
            model.addAttribute("subtotal", cart.getTotalAmount());
            return "customer/checkout";
        }
    }

    @GetMapping("/success/{orderId}")
    public String checkoutSuccess(@PathVariable Long orderId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(userDetails.getUsername());
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Verify order belongs to current user
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to order");
        }

        model.addAttribute("order", order);
        return "customer/checkout-success";
    }

    private BigDecimal calculateShippingFee(String shippingMethod) {
        return switch (shippingMethod) {
            case "standard" -> new BigDecimal("30000");
            case "express" -> new BigDecimal("50000");
            default -> BigDecimal.ZERO;
        };
    }
}