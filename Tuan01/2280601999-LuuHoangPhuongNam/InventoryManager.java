import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product product) {
        products.put(product.id, product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public Product getProduct(String id) {
        return products.get(id);
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.name.toLowerCase().contains(name.toLowerCase())) result.add(p);
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.price >= min && p.price <= max) result.add(p);
        }
        return result;
    }

    public void displayAll() {
        for (Product p : products.values()) {
            p.display();
        }
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }
}