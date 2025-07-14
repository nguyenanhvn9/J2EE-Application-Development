package order;

import model.Product;
import manager.InventoryManager;
import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addItem(String productId, int quantity) throws OutOfStockException {
        InventoryManager inventory = InventoryManager.getInstance();
        Product product = inventory.getProductById(productId);
        if (product == null) {
            throw new OutOfStockException("Sản phẩm không tồn tại: " + productId);
        }
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Không đủ hàng cho sản phẩm: " + product.getName());
        }
        items.put(product, quantity);
    }

    public void processOrder() throws OutOfStockException {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Không đủ hàng cho sản phẩm: " + product.getName());
            }
        }
        // Trừ hàng
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
    }

    public void displayOrder() {
        System.out.println("Đơn hàng:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("Số lượng mua: " + entry.getValue());
        }
    }
} 