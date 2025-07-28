package org.example.booking.service;


import org.example.booking.Response.ShoppingCartRepository;
import org.example.booking.model.CartItem;
import org.example.booking.model.Product;
import org.example.booking.model.ShoppingCart;
import org.example.booking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductService productService;

    public ShoppingCart getOrCreateCart(User user) {
        Optional<ShoppingCart> existingCart = shoppingCartRepository.findByUser(user);
        if (existingCart.isPresent()) {
            return existingCart.get();
        } else {
            ShoppingCart newCart = new ShoppingCart(user);
            return shoppingCartRepository.save(newCart);
        }
    }

    public ShoppingCart getCartByUser(User user) {
        return shoppingCartRepository.findByUser(user).orElse(null);
    }

    public void addItemToCart(User user, Long productId, int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.isAvailable()) {
            throw new RuntimeException("Product is not available");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStockQuantity());
        }

        ShoppingCart cart = getOrCreateCart(user);
        cart.addItem(product, quantity);
        shoppingCartRepository.save(cart);
    }

    public void updateItemQuantity(User user, Long productId, int quantity) {
        ShoppingCart cart = getCartByUser(user);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (quantity > 0 && product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStockQuantity());
        }

        cart.updateItemQuantity(product, quantity);
        shoppingCartRepository.save(cart);
    }

    public void removeItemFromCart(User user, Long productId) {
        ShoppingCart cart = getCartByUser(user);
        if (cart == null) {
            return;
        }

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.removeItem(product);
        shoppingCartRepository.save(cart);
    }

    public void clearCart(User user) {
        ShoppingCart cart = getCartByUser(user);
        if (cart != null) {
            cart.clearCart();
            shoppingCartRepository.save(cart);
        }
    }

    public BigDecimal getCartTotal(User user) {
        ShoppingCart cart = getCartByUser(user);
        return cart != null ? cart.getTotalAmount() : BigDecimal.ZERO;
    }

    public int getCartItemCount(User user) {
        ShoppingCart cart = getCartByUser(user);
        return cart != null ? cart.getTotalItems() : 0;
    }

    public boolean isCartEmpty(User user) {
        ShoppingCart cart = getCartByUser(user);
        return cart == null || cart.isEmpty();
    }

    public void validateCartForCheckout(User user) {
        ShoppingCart cart = getCartByUser(user);
        if (cart == null || cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            if (!product.isAvailable()) {
                throw new RuntimeException("Product " + product.getName() + " is no longer available");
            }
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName() +
                        ". Available: " + product.getStockQuantity() + ", Required: " + item.getQuantity());
            }
        }
    }
}