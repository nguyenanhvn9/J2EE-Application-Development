package Tuan01;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private String orderId;
    private Map<String, Integer> items;
    private InventoryManager inventoryManager;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new HashMap<>();
        this.inventoryManager = InventoryManager.getInstance();
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItem(String productId, int quantity) throws OutOfStockException {
        Product product = inventoryManager.findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }

        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product " + product.getName() + ". Available: " + product.getQuantityInStock() + ", Requested: " + quantity);
        }

        // If stock is sufficient, add to order and update inventory
        items.put(productId, items.getOrDefault(productId, 0) + quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
        System.out.println(quantity + " units of " + product.getName() + " added to order " + orderId + ". Remaining stock: " + product.getQuantityInStock());
    }

    public double calculateTotal() {
        double total = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            Product product = inventoryManager.findProductById(entry.getKey());
            if (product != null) {
                total += product.getPrice() * entry.getValue();
            }
        }
        return total;
    }

    public void displayOrderDetails() {
        System.out.println("\n--- Order Details (ID: " + orderId + ") ---");
        if (items.isEmpty()) {
            System.out.println("No items in this order.");
            return;
        }
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            Product product = inventoryManager.findProductById(entry.getKey());
            if (product != null) {
                System.out.println("  - " + product.getName() + " (ID: " + product.getId() + ") x " + entry.getValue() + " @ " + product.getPrice() + " VND");
            }
        }
        System.out.println("Total: " + calculateTotal() + " VND");
        System.out.println("-----------------------------------");
    }
}
