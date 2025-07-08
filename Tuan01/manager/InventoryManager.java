package Tuan01.manager;

import Tuan01.model.*;

import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private final Map<String, Product> inventory = new HashMap<>();

    private InventoryManager() {}

    public static InventoryManager getInstance() {
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }

    public boolean removeProduct(String id) {
        return inventory.remove(id) != null;
    }

    public boolean updateProduct(String id, double price, int quantity) {
        Product product = inventory.get(id);
        if (product != null) {
            product.setPrice(price);
            product.setQuantityInStock(quantity);
            return true;
        }
        return false;
    }

    public Product searchByName(String name) {
        return inventory.values().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> results = new ArrayList<>();
        for (Product p : inventory.values()) {
            if (p.getPrice() >= min && p.getPrice() <= max)
                results.add(p);
        }
        return results;
    }

    public void displayAllProducts() {
        for (Product p : inventory.values()) {
            p.display();
        }
    }

    public Product getProductById(String id) {
        return inventory.get(id);
    }
}
