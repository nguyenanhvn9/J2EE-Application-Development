import java.util.*;

public class Order {
    private List<Product> items;
    private InventoryManager inventory;

    public Order() {
        items = new ArrayList<>();
        inventory = InventoryManager.getInstance();
    }

    public void addItem(String productId, int quantity) throws OutOfStockException {
        Product product = inventory.getProductById(productId); // Sử dụng phương thức getProductById
        if (product == null) throw new IllegalArgumentException("San Pham khong ton tai: " + productId);
        if (product.getQuantityInStock() < quantity) {
            throw new OutOfStockException("Khong du hang cho san pham: " + product.getName());
        }
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
        items.add(product);
    }

    public void displayOrder() {
        for (Product item : items) {
            item.display();
        }
    }
}