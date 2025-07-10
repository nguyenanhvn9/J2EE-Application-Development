package inventory;

import model.Product;
import java.util.*;

public class InventoryManager {
    private static InventoryManager instance = null;
    private Map<String, Product> products = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product p) {
        products.put(p.getId(), p);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public void updateProduct(String id, double newPrice, int newQty) {
        Product p = products.get(id);
        if (p != null) {
            p.setPrice(newPrice);
            p.updateQuantity(newQty - p.getQuantityInStock());
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
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
