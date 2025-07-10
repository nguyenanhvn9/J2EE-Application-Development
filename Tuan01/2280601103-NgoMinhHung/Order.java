package Tuan01;

import Tuan01.models.Product;
import java.util.*;

public class Order {
    private Map<Product, Integer> orderItems;

    public Order() {
        orderItems = new HashMap<>();
    }

    // Thêm sản phẩm vào đơn hàng
    public void addProduct(Product product, int quantity) {
        orderItems.put(product, orderItems.getOrDefault(product, 0) + quantity);
    }

    // Đặt hàng: kiểm tra tồn kho, ném OutOfStockException nếu không đủ, cập nhật tồn kho
    public void placeOrder() throws Tuan01.OutOfStockException {
        InventoryManager inventory = InventoryManager.getInstance();
        // Kiểm tra tồn kho trước
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            if (product.getQuantityInStock() < quantity) {
                throw new OutOfStockException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho!");
            }
        }
        // Nếu đủ, cập nhật tồn kho
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            inventory.reduceProductQuantity(product.getId(), quantity);
        }
    }

    // Hiển thị đơn hàng
    public void displayOrder() {
        System.out.println("Danh sách sản phẩm trong đơn hàng:");
        for (Map.Entry<Product, Integer> entry : orderItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("- " + product.getName() + " | Số lượng: " + quantity);
        }
    }
}