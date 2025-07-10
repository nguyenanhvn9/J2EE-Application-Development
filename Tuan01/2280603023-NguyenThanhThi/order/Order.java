package Tuan01;

import java.util.*;

public class Order {
    private String orderId;
    private Map<Product, Integer> items;

    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        items.forEach((product, quantity) -> {
            product.display();
            System.out.println("  Quantity Ordered: " + quantity);
        });
        double total = items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
        System.out.printf("Total Order Value: %.2f%n", total);
    }
}
