import java.util.*;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Khong du hang cho san pham: " + product.getName());
        }
        items.put(product, quantity);
    }

    public void processOrder() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setQuantityInStock(p.getQuantityInStock() - qty);
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