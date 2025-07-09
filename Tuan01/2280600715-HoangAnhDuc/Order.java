import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addItem(String id, int quantity) throws OutOfStockException {
        InventoryManager manager = InventoryManager.getInstance();
        Product product = manager.findProductById(id);
        if (product == null) {
            throw new OutOfStockException("Product not found.");
        }
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for " + product.getName());
        }
        items.put(product, items.getOrDefault(product, 0) + quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        items.forEach((product, qty) -> System.out.printf("Product: %s, Quantity: %d, Total: %.2f%n",
                product.getName(), qty, qty * product.getPrice()));
    }
}