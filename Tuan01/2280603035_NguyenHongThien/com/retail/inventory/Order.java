package com.retail.inventory;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Represents an order.
 */
public class Order implements Serializable {
    private String orderId;
    private Map<Product, Integer> products;
    private LocalDate orderDate;

    public Order(String orderId) {
        this.orderId = orderId;
        this.products = new LinkedHashMap<>();
        this.orderDate = LocalDate.now();
    }

    public String getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public Map<Product, Integer> getProducts() { return products; }

    /**
     * Add product to order.
     * @throws OutOfStockException if stock is insufficient
     */
    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive.");
        if (product.getQuantityInStock() < quantity)
            throw new OutOfStockException("Insufficient stock for product: " + product.getName());
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    /**
     * Process order and update inventory.
     * @throws OutOfStockException if any product lacks stock
     */
    public void processOrder(InventoryManager inventory) throws OutOfStockException {
        // Check all stock first
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = inventory.getProductById(entry.getKey().getId());
            if (p == null)
                throw new OutOfStockException("Product not found: " + entry.getKey().getName());
            if (p.getQuantityInStock() < entry.getValue())
                throw new OutOfStockException("Insufficient stock for product: " + p.getName());
        }
        // Deduct stock
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = inventory.getProductById(entry.getKey().getId());
            p.setQuantityInStock(p.getQuantityInStock() - entry.getValue());
        }
    }

    /**
     * Display order details.
     */
    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        double total = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double cost = p.getPrice() * qty;
            System.out.printf("  %s x%d @ %.2f each = %.2f%n", p.getName(), qty, p.getPrice(), cost);
            total += cost;
        }
        System.out.printf("Total Cost: %.2f%n", total);
    }
}
