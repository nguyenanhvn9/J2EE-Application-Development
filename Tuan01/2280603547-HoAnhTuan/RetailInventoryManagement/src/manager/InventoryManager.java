package manager;

import models.*;
import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private Map<String, Product> products;

    private InventoryManager() {
        products = new HashMap<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product p) {
        products.put(p.getId(), p);
    }

    public void removeProduct(String id) {
        products.remove(id);
    }

    public Product getProductById(String id) {
        return products.get(id);
    }

    public List<Product> searchByName(String name) {
        List<Product> results = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    public void displayAll() {
        for (Product p : products.values()) {
            p.display();
        }
    }
}
