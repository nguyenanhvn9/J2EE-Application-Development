import java.util.*;

public class Order {
    private Map<Product, Integer> items = new LinkedHashMap<>();

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.quantityInStock < quantity) {
            throw new OutOfStockException("Khong du hang cho san pham: " + product.name);
        }
        product.quantityInStock -= quantity;
        items.put(product, quantity);
    }

    public void displayOrder() {
        System.out.println("=== Don hang ===");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println("- " + entry.getKey().name + ": " + entry.getValue() + " cai");
        }
    }
}