package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository; // Thay ShoppingCartRepository bằng CartRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository; // Sử dụng CartRepository

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public Cart getCartByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUserId(user.getId());
    }

    public Cart getCartByUser(User user) {
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    public void addToCart(Long productId, int quantity) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            if (quantity <= 0 || quantity > product.getStockQuantity()) {
                throw new RuntimeException("Invalid quantity or exceeds stock");
            }
            Cart cart = cartRepository.findByUserId(user.getId());
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cart.setCreatedAt(LocalDateTime.now());
                cart = cartRepository.save(cart);
            }
            CartItem item = cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);
            if (item == null) {
                item = new CartItem();
                item.setCart(cart);
                item.setProduct(product);
                item.setQuantity(quantity);
                item.setPrice(product.getPrice());
                cart.getCartItems().add(item);
            } else {
                item.setQuantity(item.getQuantity() + quantity);
            }
            cartRepository.save(cart);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Error adding to cart: " + e.getMessage());
        }
    }

    public List<CartItem> getSelectedCartItemsForCheckout(User user, List<Long> selectedItemIds) {
        // Implement this method to fetch cart items belonging to the user
        // and matching the provided IDs.
        Cart userCart = getCartByUser(user); // Assuming you have this method
        if (userCart == null) {
            return Collections.emptyList();
        }
        return userCart.getCartItems().stream()
                .filter(item -> selectedItemIds.contains(item.getId()))
                .collect(Collectors.toList());
    }

    public void addToCart(User user, Product product, int quantity) {
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        if (quantity <= 0 || quantity > product.getStockQuantity()) {
            throw new RuntimeException("Invalid quantity or exceeds stock");
        }

        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(LocalDateTime.now());
            cart = cartRepository.save(cart);
        }

        CartItem item = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setPrice(product.getPrice());
            cart.getCartItems().add(item);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }

        cartRepository.save(cart);
    }

    public void updateCartItemQuantity(Long itemId, int quantity) {
        CartItem item = cartItemRepository.findById(itemId).orElse(null);
        if (item != null && quantity > 0) {
            Product product = item.getProduct();
            if (quantity <= product.getStockQuantity()) {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            } else {
                throw new RuntimeException("Quantity exceeds stock");
            }
        }
    }

    public void removeCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}