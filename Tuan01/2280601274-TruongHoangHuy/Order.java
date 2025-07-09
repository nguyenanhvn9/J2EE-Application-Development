import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new LinkedHashMap<>();
    }

    public void addProduct(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("San pham '" + product.getName() + "' khong du so luong trong kho!");
        }
        items.put(product, quantity);
    }

    public void processOrder() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.setQuantityInStock(product.getQuantityInStock() - quantity);
        }
        System.out.println("Don hang da duoc xu ly thanh cong!");
    }

    public void displayOrder() {
        System.out.println("--- Chi tiet don hang ---");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("So luong mua: " + entry.getValue());
        }
    }
} 