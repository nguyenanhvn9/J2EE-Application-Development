package order;

import model.Product;
import exception.OutOfStockException;
import java.io.Serializable;
import java.util.*;

public class Order implements Serializable {
    private Map<Product, Integer> orderItems;
    public Order() {
        orderItems = new HashMap<>();
    }
    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        orderItems.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }
    public double getTotal() {
        return orderItems.entrySet().stream().mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
    }
    public void displayOrder() {
        System.out.println("Order details:");
        orderItems.forEach((p, q) -> {
            p.display();
            System.out.println("  Quantity: " + q);
        });
        System.out.println("Total: " + getTotal());
    }
    public Map<Product, Integer> getOrderItems() {
        return orderItems;
    }
}
