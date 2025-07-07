package order;

import manager.InventoryManager; // Cần truy cập InventoryManager
import models.Product; // Cần sử dụng lớp Product

import java.time.LocalDate; // Để quản lý ngày đặt hàng
import java.util.HashMap;
import java.util.Map;

public class Order {
    private String orderId;
    private LocalDate orderDate;
    // Map để lưu trữ các sản phẩm trong đơn hàng và số lượng của chúng
    private Map<Product, Integer> items;

    public Order(String orderId) {
        this.orderId = orderId;
        this.orderDate = LocalDate.now(); // Tự động lấy ngày hiện tại
        this.items = new HashMap<>();
    }

    public String getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }

    // Thêm sản phẩm vào đơn hàng
    public void addItem(String productId, int quantity) throws OutOfStockException {
        // Lấy InventoryManager instance để kiểm tra kho hàng
        InventoryManager inventory = InventoryManager.getInstance();
        Product p = inventory.getProductById(productId);

        if (p == null) {
            throw new OutOfStockException("Product with ID '" + productId + "' not found.");
        }

        if (p.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product '" + p.getName() + "'. Available: " + p.getQuantityInStock() + ", Requested: " + quantity);
        }
        
        // Thêm hoặc cập nhật số lượng sản phẩm trong đơn hàng
        items.put(p, items.getOrDefault(p, 0) + quantity); 
        
        // Giảm số lượng sản phẩm trong kho
        p.setQuantityInStock(p.getQuantityInStock() - quantity);
        System.out.println("Added " + quantity + " of " + p.getName() + " to order. Remaining stock: " + p.getQuantityInStock());
    }

    // Hiển thị tóm tắt đơn hàng
    public void displayOrder() {
        if (items.isEmpty()) {
            System.out.println("Order is empty.");
            return;
        }
        System.out.printf("%n==== Order Summary (ID: %s, Date: %s) ====%n", orderId, orderDate);
        double totalAmount = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double itemTotal = p.getPrice() * qty;
            System.out.printf(" - %s (ID: %s): %d x %.2f = %.2f%n", p.getName(), p.getId(), qty, p.getPrice(), itemTotal);
            totalAmount += itemTotal;
        }
        System.out.printf("Total Amount: %.2f%n", totalAmount);
        System.out.println("=========================================");
    }
}