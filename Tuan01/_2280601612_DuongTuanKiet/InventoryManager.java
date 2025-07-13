package _2280601612_DuongTuanKiet;
import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> productMap = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product p) {
        productMap.put(p.getId(), p);
    }

    public void removeProduct(String id) {
        productMap.remove(id);
    }

    public void updateProduct(String id, double price, int quantity) {
        Product p = productMap.get(id);
        if (p != null) {
            p.setPrice(price);
            p.setQuantityInStock(quantity);
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : productMap.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : productMap.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayAll() {
        for (Product p : productMap.values()) {
            p.display();
        }
    }

    public Product getProductById(String id) {
        return productMap.get(id);
    }
}