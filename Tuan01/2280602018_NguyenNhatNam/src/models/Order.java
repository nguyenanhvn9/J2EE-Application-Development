package models;

import exceptions.OutOfStockException;
import java.util.*;
import managers.InventoryManager;

public class Order {
    private List<Product> items = new ArrayList<>();

    public void addToOrder(String productId, int quantity) throws OutOfStockException {
        InventoryManager manager = InventoryManager.getInstance();
        Product p = manager.getProductById(productId);
        if (p == null) {
            System.out.println("Sản phẩm không tồn tại.");
            return;
        }
        if (p.quantityInStock < quantity) {
            throw new OutOfStockException("Không đủ hàng trong kho: " + p.name);
        }
        p.quantityInStock -= quantity;
        items.add(p);
        System.out.println("Đã thêm vào đơn hàng: " + p.name);
    }

    public void displayOrder() {
        System.out.println("Tóm tắt đơn hàng:");
        items.forEach(Product::display);
    }
}