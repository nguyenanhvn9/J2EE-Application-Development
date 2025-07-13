package com.retail.inventory.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private LocalDateTime orderDate;
    private Map<Product, Integer> orderedProducts;
    private double totalAmount;

    public Order(String orderId) {
        this.orderId = orderId;
        this.orderDate = LocalDateTime.now();
        this.orderedProducts = new HashMap<>();
        this.totalAmount = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        orderedProducts.put(product, quantity);
        calculateTotal();
    }

    private void calculateTotal() {
        totalAmount = orderedProducts.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    public void displayOrder() {
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("🛒 ORDER DETAILS\n");
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("🆔 Order ID: %s\n", orderId);
        System.out.printf("📅 Date: %s\n", orderDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("📦 PRODUCTS:\n");

        orderedProducts.forEach((product, quantity) -> {
            System.out.printf("  • %s (ID: %s) - Quantity: %d - Unit Price: $%.2f - Subtotal: $%.2f\n",
                    product.getName(), product.getId(), quantity, product.getPrice(),
                    product.getPrice() * quantity);
        });

        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        System.out.printf("💰 TOTAL AMOUNT: $%.2f\n", totalAmount);
        System.out.printf("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

    // Getters
    public String getOrderId() { return orderId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Map<Product, Integer> getOrderedProducts() { return orderedProducts; }
    public double getTotalAmount() { return totalAmount; }
}
