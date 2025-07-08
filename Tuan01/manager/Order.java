package Tuan01.manager;

import Tuan01.model.Product;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("❌ Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("🛒 Order Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println("- " + entry.getKey().getName() + ": " + entry.getValue() + " pcs");
        }
    }
}
