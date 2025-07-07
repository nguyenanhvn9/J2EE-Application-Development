import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Sản phẩm " + product.getName() + " không đủ số lượng trong kho!");
        }
        items.put(product, quantity);
    }

    public void processOrder() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
    }

    public void displayOrder() {
        System.out.println("--- Đơn hàng ---");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("Số lượng mua: " + entry.getValue());
        }
    }
} 