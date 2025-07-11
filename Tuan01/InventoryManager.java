package Tuan01;

import java.util.*;

public class InventoryManager {
    private static InventoryManager instance;
    private List<Product> products;

    private InventoryManager() {
        products = new ArrayList<>();
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public void addProduct(Product p) { products.add(p); }

    public void removeProductById(String id) {
        products.removeIf(p -> p.getId().equals(id));
    }

    public void updateProduct(String id, double newPrice, int newQty) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setPrice(newPrice);
                p.setQuantityInStock(newQty);
                break;
            }
        }
    }

    public List<Product> searchByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }

    public void displayAllProducts() {
        for (Product p : products) {
            p.display();
        }
    }

    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }
}

