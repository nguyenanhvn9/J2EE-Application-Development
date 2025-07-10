package _2280601612_DuongTuanKiet;
import java.util.*;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product p, int quantity) throws OutOfStockException {
        if (p.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for: " + p.getName());
        }
        items.put(p, quantity);
        p.setQuantityInStock(p.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().getName() + " x " + entry.getValue());
        }
    }
}