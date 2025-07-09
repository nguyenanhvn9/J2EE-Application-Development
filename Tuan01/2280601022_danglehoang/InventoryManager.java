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
        products.put(product.getId(), product);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public void updateProduct(String id, double price, int quantity) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(price);
            p.setQuantityInStock(quantity);
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) result.add(p);
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) result.add(p);
        }
        return result;
    }

    public void displayAll() {
        for (Product p : products.values()) {
            p.display();
        }
    }

    public Product getProductById(String id) {
        return products.get(id);
    }
} 