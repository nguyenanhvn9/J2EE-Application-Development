package order;

import manager.InventoryManager;
import model.OutOfStockException;
import model.Product;
import java.util.*;

public class Order {
  private Map<String, Integer> items = new HashMap<>();
  private InventoryManager inventory = InventoryManager.getInstance();

  public void addProductToOrder(String productId, int quantity) throws OutOfStockException {
    Product product = inventory.getProductById(productId);
    if (product == null) {
      throw new OutOfStockException("Sản phẩm không tồn tại!");
    }
    if (product.getQuantityInStock() < quantity) {
      throw new OutOfStockException("Không đủ số lượng trong kho!");
    }
    items.put(productId, quantity);
  }

  public void processOrder() {
    for (Map.Entry<String, Integer> entry : items.entrySet()) {
      Product product = inventory.getProductById(entry.getKey());
      int orderedQty = entry.getValue();
      product.setQuantityInStock(product.getQuantityInStock() - orderedQty);
    }
    System.out.println("✅ Đơn hàng đã được xử lý thành công.");
  }
}
