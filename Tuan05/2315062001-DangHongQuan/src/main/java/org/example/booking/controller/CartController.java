package org.example.booking.controller;


import org.example.booking.model.ShoppingCart;
import org.example.booking.model.User;
import org.example.booking.service.ShoppingCartService;
import org.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.findByUsername(userDetails.getUsername());
        ShoppingCart cart = shoppingCartService.getCartByUser(user);

        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", cart != null ? cart.getTotalAmount() : 0);
        model.addAttribute("cartItemCount", cart != null ? cart.getTotalItems() : 0);

        return "customer/cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "error:Please login to add items to cart";
        }

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            shoppingCartService.addItemToCart(user, productId, quantity);
            return "success:Product added to cart successfully";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCartItem(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "error:Please login";
        }

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            shoppingCartService.updateItemQuantity(user, productId, quantity);
            return "success:Cart updated successfully";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public String removeFromCart(
            @RequestParam Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return "error:Please login";
        }

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            shoppingCartService.removeItemFromCart(user, productId);
            return "success:Item removed from cart";
        } catch (Exception e) {
            return "error:" + e.getMessage();
        }
    }

    @PostMapping("/clear")
    public String clearCart(
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            User user = userService.findByUsername(userDetails.getUsername());
            shoppingCartService.clearCart(user);
            redirectAttributes.addFlashAttribute("successMessage", "Cart cleared successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/cart";
    }

    @GetMapping("/count")
    @ResponseBody
    public int getCartItemCount(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return 0;
        }

        User user = userService.findByUsername(userDetails.getUsername());
        return shoppingCartService.getCartItemCount(user);
    }
}