package Tuan01.RetailInventorySystem.model;

import Tuan01.RetailInventorySystem.exception.OutOfStockException;
import Tuan01.RetailInventorySystem.manager.InventoryManager;
import java.util.*;

public class Order {
    private Map<Product, Integer> items = new LinkedHashMap<>();

    public void addProduct(String productId, int quantity) throws OutOfStockException {
        InventoryManager im = InventoryManager.getInstance();
        Product p = im.findById(productId);
        if (p == null) {
            System.out.println("Sản phẩm không tồn tại.");
            return;
        }
        if (p.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Không đủ hàng trong kho!");
        }
        p.setQuantityInStock(p.getQuantityInStock() - quantity);
        items.put(p, quantity);
    }

    public void printOrder() {
        System.out.println("---- Đơn hàng ----");
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double lineTotal = qty * p.getPrice();
            total += lineTotal;
            System.out.printf("%s | SL: %d | Thành tiền: %.2f\n", p.getName(), qty, lineTotal);
        }
        System.out.printf("Tổng cộng: %.2f\n", total);
    }
}
