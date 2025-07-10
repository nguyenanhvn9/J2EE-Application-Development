package Tuan01;

import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new LinkedHashMap<>();
    }

    public void addItem(Product product, int quantity) {
        items.put(product, items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void process() throws OutOfStockException {
        System.out.println("--- Don hang ---");
        // Kiểm tra tồn kho xem có đủ k thì mới đặt
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            if (p.getQuantityInStock() < qty) {
                throw new OutOfStockException("San pham '" + p.getName() + "' khong du so luong trong kho!");
            }
        }
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setQuantityInStock(p.getQuantityInStock() - qty);
        }
    }

    public void displayOrder() {
        System.out.println("--- Don hang ---");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.print("So luong: " + qty + " - ");
            p.display();
        }
    }
}