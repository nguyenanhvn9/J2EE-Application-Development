package order;

import manager.InventoryManager;
import models.Product;
import java.util.*;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(String productId, int quantity) throws OutOfStockException {
        Product p = InventoryManager.getInstance().getProductById(productId);
        if (p == null) throw new OutOfStockException("Product not found.");
        if (p.getQuantityInStock() < quantity) throw new OutOfStockException("Not enough stock.");
        
        items.put(p, quantity);
        p.setQuantityInStock(p.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println(" - " + p.getName() + ": " + qty + " x " + p.getPrice());
        }
    }
}
