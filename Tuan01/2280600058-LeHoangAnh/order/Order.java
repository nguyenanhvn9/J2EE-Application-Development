package order;

import model.Product;
import exception.OutOfStockException;
import java.util.*;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
        product.updateQuantity(-quantity);
    }

    public void displayOrder() {
        System.out.println("\nOrder Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("%s | Quantity: %d | Subtotal: %.2f%n", p.getName(), qty, p.getPrice() * qty);
        }
    }
}
