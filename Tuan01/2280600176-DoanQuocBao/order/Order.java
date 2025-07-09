package order;

import inventory.InventoryManager;
import inventory.OutOfStockException;
import model.Product;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(String productId, int quantity) throws OutOfStockException {
        InventoryManager manager = InventoryManager.getInstance();
        Product product = manager.findProductById(productId);
        if (product == null) throw new IllegalArgumentException("Sản phẩm không tồn tại.");
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Không đủ hàng trong kho.");
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void showOrder() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.println("-> " + p.getName() + " x " + qty + " = " + (p.getPrice() * qty));
            total += p.getPrice() * qty;
        }
        System.out.println("Tổng tiền: " + total);
    }
}
