package services;

import entity.Product;
import java.util.*;

public class Order {
    private final Map<Product, Integer> orderItems = new HashMap<>();

    public void addProduct(String productId, int quantity) {
        InventoryManager inventory = InventoryManager.getInstance();
        Product product = inventory.getProductById(productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (product.getQuantityInStock() < quantity) {
            System.out.println("Not enough stock for product: " + product.getName());
            return;
        }

        orderItems.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void printOrder() {
        System.out.println("=== Order Summary ===");
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(p.getName() + " x " + qty + " = " + (p.getPrice() * qty));
        }
    }
}
