import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public void updateProduct(String id, double price, int quantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(price);
            p.setQuantityInStock(quantity);
        }
    }

    public List<Product> searchByName(String name) {
        return products.values().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> searchByPrice(double min, double max) {
        return products.values().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public void displayAll() {
        if (products.isEmpty()) {
            System.out.println("Khong co san pham nao trong kho.");
        } else {
            products.values().forEach(Product::display);
        }
    }
} 