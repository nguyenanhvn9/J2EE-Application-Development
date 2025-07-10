import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Khong du hang trong kho cho san pham: " + product.getName());
        }
        items.put(product, quantity);
    }

    public void process() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - qty);
        }
    }

    public void displayOrder() {
        System.out.println("Chi tiet don hang:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("  So luong: " + entry.getValue());
        }
    }
} 