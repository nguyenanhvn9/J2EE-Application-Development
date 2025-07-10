import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            System.out.printf("- %s x%d = %.2f\n", p.getName(), qty, p.getPrice() * qty);
        }
    }
}