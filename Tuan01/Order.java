import java.util.*;

public class Order {
    private Map<Product, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.quantityInStock < quantity) {
            throw new OutOfStockException("Khong du hang trong kho!");
        }
        items.put(product, quantity);
        product.quantityInStock -= quantity;
    }

    public void displayOrder() {
        System.out.println("Don hang:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().display();
            System.out.println("So luong mua: " + entry.getValue());
        }
    }
}