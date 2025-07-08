import java.util.*;

public class Order {
    private Map<Product, Integer> items;

    public Order() {
        items = new HashMap<>();
    }

    public void addProduct(String id, int quantity) throws OutOfStockException {
        Product product = InventoryManager.getInstance().getProductById(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }
        items.put(product, quantity);
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
    }

    public void displayOrder() {
        System.out.println("Order Summary:");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.print("x" + entry.getValue() + " - ");
            entry.getKey().display();
        }
    }
}
