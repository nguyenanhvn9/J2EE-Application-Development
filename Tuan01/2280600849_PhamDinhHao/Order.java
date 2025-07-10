import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new LinkedHashMap<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
    }

    public void processOrder() throws OutOfStockException {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Not enough stock for product: " + product.getName());
            }
        }
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
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