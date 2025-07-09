package com.example;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;

    public Order() {
        items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (quantity > product.quantityInStock) {
            throw new OutOfStockException("❌ Không đủ hàng trong kho cho sản phẩm: " + product.name);
        }
        product.quantityInStock -= quantity;
        items.add(new OrderItem(product, quantity));
    }

    public void displayOrder() {
        System.out.println("🧾 Chi tiết đơn hàng:");
        double total = 0;
        for (OrderItem item : items) {
            System.out.printf("- %s x %d = $%.2f\n", item.product.name, item.quantity, item.getTotalPrice());
            total += item.getTotalPrice();
        }
        System.out.printf("👉 Tổng cộng: $%.2f\n", total);
    }
}

