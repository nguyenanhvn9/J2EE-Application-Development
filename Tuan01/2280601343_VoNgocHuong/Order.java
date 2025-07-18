package InventorySystem;

import java.util.*;
import InventorySystem.Product;
import InventorySystem.OutOfStockException;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException(product.getName() + " is out of stock!");
        }
        items.put(product, quantity);
    }

    public void processOrder() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setQuantityInStock(p.getQuantityInStock() - qty);
        }
    }

    public void displayOrder() {
        System.out.println("Order details:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("  Quantity: " + entry.getValue());
        }
    }
}
